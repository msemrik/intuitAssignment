package com.intuit.controller.requestobjects;

import com.intuit.domain.Product;

public class CaseSearchFilter {
        private Product product;
        private String provider;
        private Integer errorCode;
        private Integer customerId;
        private Boolean open;
        private DateRange dateRange;

        public Product getProduct() {
                return product;
        }

        public void setProduct(Product product) {
                this.product = product;
        }

        public String getProvider() {
                return provider;
        }

        public void setProvider(String provider) {
                this.provider = provider;
        }

        public Integer getErrorCode() {
                return errorCode;
        }

        public void setErrorCode(Integer errorCode) {
                this.errorCode = errorCode;
        }

        public Integer getCustomerId() {
                return customerId;
        }

        public void setCustomerId(Integer customerId) {
                this.customerId = customerId;
        }

        public Boolean getOpen() {
                return open;
        }

        public void setOpen(Boolean open) {
                this.open = open;
        }

        public DateRange getDateRange() {
                return dateRange;
        }

        public void setDateRange(DateRange dateRange) {
                this.dateRange = dateRange;
        }
}
