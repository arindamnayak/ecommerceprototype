package com.test.BackendService.Utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.Enums.CryptoCode;
import com.test.service.CryptoService;

/**
 * Description: This class is used to encrypt/decrypt a string.
 * 
 * @author AsishS
 *
 */
@Component
public class CryptoUtil {

	@Autowired
	CryptoService cryptoService;

	final static Logger logger = Logger.getLogger(CryptoUtil.class.getName());

	public String encryptString(String inputData) {
		logger.debug("Inside encryptString Method");
		String encryptedValue = null;
		String key = cryptoService.findByCode(CryptoCode.ECommerce.toString())
				.getSecretKey();
		// byte[] decodedKey = Base64.getDecoder().decode(key);
		byte[] decodedKey = DatatypeConverter.parseBase64Binary(key);
		Key originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length,
				"AES");
		try {
			Cipher cipher;

			// Create the cipher
			cipher = Cipher.getInstance("AES");

			// Initialize the cipher for encryption
			cipher.init(Cipher.ENCRYPT_MODE, originalKey);

			// sensitive information
			byte[] encodedValue = cipher.doFinal(inputData.getBytes());

			// sencryptedValue =new BASE64Encoder().encode(encodedValue);

			encryptedValue = DatatypeConverter.printBase64Binary(encodedValue);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (BadPaddingException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return encryptedValue;
	}

	public String decryptString(String encryptedData) {

		logger.debug("Inside decryptString Method");
		String decryptedValue = null;

		try {
			Cipher desCipher;

			String key = cryptoService.findByCode(
					CryptoCode.ECommerce.toString()).getSecretKey();
			// byte[] decodedKey = Base64.getDecoder().decode(key);
			byte[] decodedKey = DatatypeConverter.parseBase64Binary(key);
			Key originalKey = new SecretKeySpec(decodedKey, 0,
					decodedKey.length, "AES");

			// Create the cipher
			desCipher = Cipher.getInstance("AES");

			// Initialize the cipher for encryption
			desCipher.init(Cipher.DECRYPT_MODE, originalKey);

			// sensitive information
			// byte[] decordedValue = new
			// BASE64Decoder().decodeBuffer(encryptedData);
			byte[] decordedValue = DatatypeConverter
					.parseBase64Binary(encryptedData);
			byte[] decValue = desCipher.doFinal(decordedValue);

			decryptedValue = new String(decValue);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.toString());
		} catch (NoSuchPaddingException e) {
			logger.error(e.toString());
		} catch (InvalidKeyException e) {
			logger.error(e.toString());
		} catch (IllegalBlockSizeException e) {
			logger.error(e.toString());
		} catch (BadPaddingException e) {
			logger.error(e.toString());			
		}
		return decryptedValue;
	}
}
