package com.intuit.controller.requestobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intuit.controller.requestobjects.filters.Product;

import javax.validation.constraints.Min;

public class CaseSearchFilter {
        @JsonProperty
        private Product product;
        private String provider;
//        private Integer errorCode;
//        @Min(0)
//        private Integer customerId;
//        private Boolean open;
        private DateRange dateRange;

        public CaseSearchFilter() {
        }

        public CaseSearchFilter(Product product) {
                this.product = product;
        }

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

        public DateRange getDateRange() {
                return dateRange;
        }

        public void setDateRange(DateRange dateRange) {
                this.dateRange = dateRange;
        }

        //        public CaseSearchFilter(String product) {
//                this.product = product;
//        }
//
//        public String getProduct() {
//                return product;
//        }
//
//        public void setProduct(String product) {
//                this.product = product;
//        }
        //        public CaseSearchFilter() {
//
//        }
//
//        public CaseSearchFilter(String product, String provider, Integer errorCode, @Min(0) Integer customerId, Boolean open, DateRange dateRange) {
//                this.product = product;
//                this.provider = provider;
//                this.errorCode = errorCode;
//                this.customerId = customerId;
//                this.open = open;
//                this.dateRange = dateRange;
//        }
//
//        //        public CaseSearchFilter(String product, String provider, Integer errorCode, @Min(0) Integer customerId, Boolean open, DateRange dateRange) {
////                this.product = product;
////                this.provider = provider;
////                this.errorCode = errorCode;
////                this.customerId = customerId;
////                this.open = open;
////                this.dateRange = dateRange;
////        }
//
//        public String getProduct() {
//                return product;
//        }
//
//        @JsonProperty
//        public void setProduct(String product) {
//                this.product = product;
//        }
//
//        public String getProvider() {
//                return provider;
//        }
//
//        @JsonProperty
//        public void setProvider(String provider) {
//                this.provider = provider;
//        }
//
//        public Integer getErrorCode() {
//                return errorCode;
//        }
//
//        public void setErrorCode(Integer errorCode) {
//                this.errorCode = errorCode;
//        }
//
//        public Integer getCustomerId() {
//                return customerId;
//        }
//
//        public void setCustomerId(Integer customerId) {
//                this.customerId = customerId;
//        }
//
//        public Boolean getOpen() {
//                return open;
//        }
//
//        public void setOpen(Boolean open) {
//                this.open = open;
//        }
//
//        public DateRange getDateRange() {
//                return dateRange;
//        }
//
//        public void setDateRange(DateRange dateRange) {
//                this.dateRange = dateRange;
//        }
//
//        @Override
//        public String toString() {
//                return "CaseSearchFilter{" +
//                        "product='" + product + '\'' +
//                        ", provider='" + provider + '\'' +
//                        ", errorCode=" + errorCode +
//                        ", customerId=" + customerId +
//                        ", open=" + open +
//                        ", dateRange=" + dateRange +
//                        '}';
//        }
}
