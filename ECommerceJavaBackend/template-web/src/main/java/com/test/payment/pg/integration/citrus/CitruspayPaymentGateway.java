package com.test.payment.pg.integration.citrus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Service;

import com.test.Enums.Status;
import com.test.payment.pg.PaymentGatewayOps;
import com.test.payment.pg.PgResponseEntity;
import com.test.persistence.entities.PgProperties;
import com.test.persistence.entities.PgRequestEntity;
import com.test.service.PgPropertiesService;

@Service
public class CitruspayPaymentGateway implements PaymentGatewayOps {


    private static String requesturl;
    private static String pathMakePaymentService;
    private static String pathGetBalanceService;
    private static String pathGetAuthTokenService; // out of scope 
    private static String pathRefundPaymentService; // out of scope 
    private static String pathPaymentInquiryService; // out of scope 

    
    final static Logger logger = Logger.getLogger(CitruspayPaymentGateway.class);
     
    @Override
    public void buildProp(PgPropertiesService pgProService,String pgCode) {
    	System.out.println("Now inside BuildProp");
     	List<PgProperties> PgpropertiesList= pgProService    
                .findByPgCode("CITRUSPAY");
        for (PgProperties propt : PgpropertiesList) {
            if (!propt.getPropertyKey().isEmpty()
                    && !propt.getPropertyValue().isEmpty()) {
                switch (propt.getPropertyKey()) {
                case "url":
                    setRequesturl(propt.getPropertyValue());
                    break;
                case "path_make_payment_service":
                    setPathMakePaymentService(propt.getPropertyValue());
                    break;
                case "path_get_auth_token_service":
                    setPathGetAuthTokenService(propt.getPropertyValue());
                    break;
                case "path_get_balance_service":
                    setPathGetBalanceService(propt.getPropertyValue());
                    break;
                case "path_payment_inquiry_service":
                    setPathPaymentInquiryService(propt.getPropertyValue());
                    break;
                case "path_refund_payment_service":
                    setPathRefundPaymentService(propt.getPropertyValue());

                default:
                    break;
                }
            }

        }
    }  

    @Override
    public PgResponseEntity doPayment(PgRequestEntity pgRequestEntity) {
    	System.out.println("Now inside doPayment");
        PgResponseEntity pgResponse = new PgResponseEntity();
         try {
            pgResponse = sendPostRequest(pgRequestEntity);
            if (pgResponse.getPgTransactionId()==null || pgResponse.getPgTransactionId().isEmpty()) {
                logger.info("transaction failed on payment {}"
                        + pgResponse.getReasonStatus());
            }
        } catch (Exception e) {        	
            logger.error("transaction failed " + e.getMessage());
           // e.printStackTrace();
        }
        return pgResponse;
    }

    @Override
    public PgResponseEntity sendPostRequest(PgRequestEntity pgRequestEntity) {

        String targetURL = requesturl + pathMakePaymentService; // construct
                                                                // makePayment
                                                                // url
        String amount = pgRequestEntity.getAmount().toString();
        String urlParameters = null;
        PgResponseEntity pgResp = new PgResponseEntity();

        /*
         * // getBalance partial deduction Balance balance = new Balance();
         * balance = getBalance(pgRequestEntity);
         * if(!balance.getCurrency().isEmpty() && balance.getValue().isNaN()){
         * if(balance.getValue() < pgRequestEntity.getAmount()){ payableAmount =
         * balance.getValue(); amount = payableAmount.toString(); }
         * 
         * }
         */

        try {
            urlParameters = "merchantTransactionId="
                    + URLEncoder
                            .encode(pgRequestEntity.getMerchantTransactionId(),
                                    "UTF-8") + "&amount="
                    + URLEncoder.encode(amount, "UTF-8") + "&currency="
                    + URLEncoder.encode(pgRequestEntity.getCurrency(), "UTF-8")
                    + "&comment="
                    + URLEncoder.encode(pgRequestEntity.getComment(), "UTF-8");
        } catch (UnsupportedEncodingException e1) {

            logger.error("Failed to create urlParams {}" + e1);
        }

        URL url;
        HttpURLConnection connection = null;
        try {
            // Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                    "" + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setRequestProperty("Authorization", "Bearer "
                    + pgRequestEntity.getAccessToken());

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            // TODO set time out , connection time,response time out  
            // Send request
            DataOutputStream wr = new DataOutputStream(
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            // Get Response
            int respCode = connection.getResponseCode();
            if (respCode > 200 && respCode < 300) { 
                // error code for failed response 
                // connection time out 
            	pgResp.setErrorType("FAILED:error code "+ connection.getResponseCode());
            	pgResp.setErrorMessage(connection.getResponseMessage());
                logger.debug("Error not able to connet with {}" + respCode);
                logger.error("Error not able to connet with {}" + respCode);
            }

            String response = getStreamToString(connection.getInputStream());
           
            ObjectMapper om = new ObjectMapper();
            CitrusPayResponse citruspayresponse = om.readValue(response,
                    CitrusPayResponse.class);

            // check for balance
            String[] tmp = citruspayresponse.getRef().split(":");

            pgResp.setBookingNo(tmp[0]);
            pgResp.setPgTransactionId(citruspayresponse.getId());
            pgResp.setStatus(citruspayresponse.getStatus());
            if (Status.FAILED.toString().equals(pgResp.getStatus()))
            {
            	pgResp.setAmountPaid(0D);
            	pgResp.setAmountUnpaid(citruspayresponse.getAmount().getValue());
            }
            else
            {
            	pgResp.setAmountPaid(citruspayresponse.getAmount().getValue());	
            	pgResp.setAmountUnpaid(0D);
            }            
            pgResp.setBalance(citruspayresponse.getBalance().getValue());
            pgResp.setReasonStatus(citruspayresponse.getReason());
            pgResp.setCurrency(citruspayresponse.getAmount().getCurrency());
            pgResp.setDate(citruspayresponse.getDate().toString());
            // pgResp.setAmountUnpaid(pgRequestEntity.getAmount() -
            // payableAmount);
            pgResp.setCustomerNo(pgRequestEntity.getCustomerId());
            logger.debug(citruspayresponse.getId());

        } catch (Exception e) {
            logger.error("Failed to create connection {}" + e);
        } finally {

            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                    logger.error("Failed to close http connection {}" + e);
                }

            }
        }
        return pgResp;
    }

    @Override
    public PgResponseEntity getBalance(PgRequestEntity pgRequestEntity) {

    	PgResponseEntity pgResponseEntity = new  PgResponseEntity(); 
        String targetURL = requesturl + pathGetBalanceService;
        Balance balance = new Balance();
        URL url;
        HttpURLConnection connection = null;
        try {
            // Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer "
                    + pgRequestEntity.getAccessToken());

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            // Get Response
            int respCode = connection.getResponseCode();
            if (respCode > 200 && respCode < 300) { // if error code is
                pgResponseEntity.setErrorType("FAILED:error code "+ connection.getResponseCode());
                pgResponseEntity.setErrorMessage(connection.getResponseMessage());
            } else {
                String response = getStreamToString(connection.getInputStream());
                // store response stream into database

                ObjectMapper om = new ObjectMapper();
                balance = om.readValue(response, Balance.class);
                if (!balance.getCurrency().isEmpty()) {
                    pgResponseEntity.setBalance(balance.getValue());
                    pgResponseEntity.setCurrency(balance.getCurrency());
                    return pgResponseEntity;

                } else {
                    pgResponseEntity.setErrorType("FAILED: invalid getBalance reponse");
                    pgResponseEntity.setErrorMessage(balance.toString());
                }
            }

        } catch (Exception e) {
            pgResponseEntity.setErrorType("FAILED: connection problem getBalance");
            pgResponseEntity.setErrorMessage(e.toString());
            logger.debug("Get request issue" + e.toString());
        }
        return pgResponseEntity;
    }

    private static String getStreamToString(InputStream stream)
            throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(stream));
        StringBuffer buf = new StringBuffer();
        String line;
        while ((line = rd.readLine()) != null) {
            buf.append(line);
        }
        return buf.toString();
    }

    @Override
    public String refundPayment(PgRequestEntity PgRequestEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String inquirePayment(PgRequestEntity PgRequestEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String sendGetRequest(PgRequestEntity PgRequestEntity) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param requesturl
     *            the requesturl to set
     */
    private static void setRequesturl(String requesturl) {
        CitruspayPaymentGateway.requesturl = requesturl;
    }

    /**
     * @param pathMakePaymentService
     *            the pathMakePaymentService to set
     */
    private static void setPathMakePaymentService(String pathMakePaymentService) {
        CitruspayPaymentGateway.pathMakePaymentService = pathMakePaymentService;
    }

    /**
     * @param pathGetAuthTokenService
     *            the pathGetAuthTokenService to set
     */
    private static void setPathGetAuthTokenService(
            String pathGetAuthTokenService) {
        CitruspayPaymentGateway.pathGetAuthTokenService = pathGetAuthTokenService;
    }

    /**
     * @param pathRefundPaymentService
     *            the pathRefundPaymentService to set
     */
    private static void setPathRefundPaymentService(
            String pathRefundPaymentService) {
        CitruspayPaymentGateway.pathRefundPaymentService = pathRefundPaymentService;
    }

    /**
     * @param pathPaymentInquiryService
     *            the pathPaymentInquiryService to set
     */
    private static void setPathPaymentInquiryService(
            String pathPaymentInquiryService) {
        CitruspayPaymentGateway.pathPaymentInquiryService = pathPaymentInquiryService;
    }

    /**
     * @param pathGetBalanceService
     *            the pathGetBalanceService to set
     */
    private static void setPathGetBalanceService(String pathGetBalanceService) {
        CitruspayPaymentGateway.pathGetBalanceService = pathGetBalanceService;
    }

}
