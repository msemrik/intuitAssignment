package com.intuit.domain;

import com.intuit.controller.requestobjects.filters.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cases")
public class Case implements Serializable {

    @EmbeddedId
    private CaseId caseId;
    @Column(nullable = false)
    private Long customerId;
    @Column(nullable = false)
    private Product product;
    @Column(nullable = false)
    private Long providerId;
    @Column(nullable = false)
    private Long createdErrorCode;
    @Column(nullable = false)
    private boolean isOpen;
    @Column(nullable = false)
    private LocalDateTime ticketCreationDate;
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;

    public Case() {
    }


    public Case(CaseId caseId, Long customerId, Product product, Long providerId, Long createdErrorCode, boolean isOpen,
                LocalDateTime ticketCreationDate, LocalDateTime lastModifiedDate) {
        this.caseId = caseId;
        this.customerId = customerId;
        this.product = product;
        this.providerId = providerId;
        this.createdErrorCode = createdErrorCode;
        this.isOpen = isOpen;
        this.ticketCreationDate = ticketCreationDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Case)) return false;
        Case aCase = (Case) o;
        return isOpen == aCase.isOpen &&
                Objects.equals(caseId, aCase.caseId) &&
                Objects.equals(customerId, aCase.customerId) &&
                product == aCase.product &&
                Objects.equals(providerId, aCase.providerId) &&
                Objects.equals(createdErrorCode, aCase.createdErrorCode) &&
                Objects.equals(ticketCreationDate, aCase.ticketCreationDate) &&
                Objects.equals(lastModifiedDate, aCase.lastModifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caseId, customerId, product, providerId, createdErrorCode, isOpen, ticketCreationDate, lastModifiedDate);
    }

    @Embeddable
    public static class CaseId implements Serializable {
        private Long caseId;
        private String crm;

        public CaseId() {
        }

        public CaseId(Long caseId, String crm) {
            this.caseId = caseId;
            this.crm = crm;
        }

        public Long getCaseId() {
            return caseId;
        }

        public void setCaseId(Long caseId) {
            this.caseId = caseId;
        }

        public String getCrm() {
            return crm;
        }

        public void setCrm(String crm) {
            this.crm = crm;
        }
    }
}


