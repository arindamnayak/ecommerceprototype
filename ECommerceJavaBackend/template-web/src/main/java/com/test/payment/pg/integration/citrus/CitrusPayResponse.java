package com.test.payment.pg.integration.citrus;

import javax.annotation.Generated;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
   
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @Generated("org.jsonschema2pojo")
    @JsonPropertyOrder({
            "id",
            "cutsomer",
            "merchant",
            "type",
            "date",
            "amount",
            "status",
            "reason",
            "balance",
            "ref"
    })
    public class CitrusPayResponse {

        @JsonProperty("id")
        private String id;
        @JsonProperty("cutsomer")
        private String cutsomer;
        @JsonProperty("merchant")
        private String merchant;
        @JsonProperty("type")
        private String type;
        @JsonProperty("date")
        private Long date;
        @JsonProperty("amount")
        private Amount amount;
        @JsonProperty("status")
        private String status;
        @JsonProperty("reason")
        private String reason;
        @JsonProperty("balance")
        private Balance balance;
        @JsonProperty("ref")
        private String ref; // bookingNo 

        /**
         *
         * @return
         * The id
         */
        @JsonProperty("id")
        public String getId() {
            return id;
        }

        /**
         *
         * @param id
         * The id
         */
        @JsonProperty("id")
        public void setId(String id) {
            this.id = id;
        }

        /**
         *
         * @return
         * The cutsomer
         */
        @JsonProperty("cutsomer")
        public String getCutsomer() {
            return cutsomer;
        }

        /**
         *
         * @param cutsomer
         * The cutsomer
         */
        @JsonProperty("cutsomer")
        public void setCutsomer(String cutsomer) {
            this.cutsomer = cutsomer;
        }

        /**
         *
         * @return
         * The merchant
         */
        @JsonProperty("merchant")
        public String getMerchant() {
            return merchant;
        }

        /**
         *
         * @param merchant
         * The merchant
         */
        @JsonProperty("merchant")
        public void setMerchant(String merchant) {
            this.merchant = merchant;
        }

        /**
         *
         * @return
         * The type
         */
        @JsonProperty("type")
        public String getType() {
            return type;
        }

        /**
         *
         * @param type
         * The type
         */
        @JsonProperty("type")
        public void setType(String type) {
            this.type = type;
        }

        /**
         *
         * @return
         * The date
         */
        @JsonProperty("date")
        public Long getDate() {
            return date;
        }

        /**
         *
         * @param date
         * The date
         */
        @JsonProperty("date")
        public void setDate(Long date) {
            this.date = date;
        }

        /**
         *
         * @return
         * The amount
         */
        @JsonProperty("amount")
        public Amount getAmount() {
            return amount;
        }

        /**
         *
         * @param amount
         * The amount
         */
        @JsonProperty("amount")
        public void setAmount(Amount amount) {
            this.amount = amount;
        }

        /**
         *
         * @return
         * The status
         */
        @JsonProperty("status")
        public String getStatus() {
            return status;
        }

        /**
         *
         * @param status
         * The status
         */
        @JsonProperty("status")
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         *
         * @return
         * The reason
         */
        @JsonProperty("reason")
        public String getReason() {
            return reason;
        }

        /**
         *
         * @param reason
         * The reason
         */
        @JsonProperty("reason")
        public void setReason(String reason) {
            this.reason = reason;
        }

        /**
         *
         * @return
         * The balance
         */
        @JsonProperty("balance")
        public Balance getBalance() {
            return balance;
        }

        /**
         *
         * @param balance
         * The balance
         */
        @JsonProperty("balance")
        public void setBalance(Balance balance) {
            this.balance = balance;
        }

        /**
         *
         * @return
         * The ref
         */
        @JsonProperty("ref")
        public String getRef() {
            return ref;
        }

        /**
         *
         * @param ref
         * The ref
         */
        @JsonProperty("ref")
        public void setRef(String ref) {
            this.ref = ref;
        }

    }
