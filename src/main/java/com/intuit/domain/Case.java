package com.intuit.domain;

import com.intuit.controller.requestobjects.CaseSearchFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public CaseId getCaseId() {
        return caseId;
    }

    public void setCaseId(CaseId caseId) {
        this.caseId = caseId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Long getCreatedErrorCode() {
        return createdErrorCode;
    }

    public void setCreatedErrorCode(Long createdErrorCode) {
        this.createdErrorCode = createdErrorCode;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public LocalDateTime getTicketCreationDate() {
        return ticketCreationDate;
    }

    public void setTicketCreationDate(LocalDateTime ticketCreationDate) {
        this.ticketCreationDate = ticketCreationDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
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

    @Override
    public String toString() {
        return "Case{" +
                "caseId=" + caseId +
                ", customerId=" + customerId +
                ", product=" + product +
                ", providerId=" + providerId +
                ", createdErrorCode=" + createdErrorCode +
                ", isOpen=" + isOpen +
                ", ticketCreationDate=" + ticketCreationDate +
                ", lastModifiedDate=" + lastModifiedDate +
                '}';
    }

    public static Specification<Case> findUsingCaseSearchFilter(final CaseSearchFilter caseSearchFilter) {
        return new Specification<Case>() {
            @Override
            public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList();
                if(caseSearchFilter.getProduct() != null) {
                    predicateList.add(cb.equal(root.get("product"),caseSearchFilter.getProduct()));
                }
                if(caseSearchFilter.getProvider() != null) {
                    predicateList.add(cb.equal(root.get("providerId"),caseSearchFilter.getProvider()));
                }
                if(caseSearchFilter.getErrorCode() != null) {
                    predicateList.add(cb.equal(root.get("createdErrorCode"),caseSearchFilter.getErrorCode()));
                }
                if(caseSearchFilter.getCustomerId() != null) {
                    predicateList.add(cb.equal(root.get("customerId"),caseSearchFilter.getCustomerId()));
                }
                if(caseSearchFilter.getOpen() != null) {
                    predicateList.add(cb.equal(root.get("isOpen"),caseSearchFilter.getOpen()));
                }
                if(caseSearchFilter.getDateRange() != null) {
                    if(caseSearchFilter.getDateRange().getStartDate() != null) {
                        predicateList.add(cb.greaterThanOrEqualTo(root.get("ticketCreationDate"),caseSearchFilter.getDateRange().getStartDate().atStartOfDay()));
                    }
                    if(caseSearchFilter.getDateRange().getEndDate() != null) {
                        predicateList.add(cb.lessThanOrEqualTo(root.get("ticketCreationDate"),caseSearchFilter.getDateRange().getEndDate().plusDays(1).atStartOfDay()));
                    }
                }

                if(!predicateList.isEmpty()) {
                    return cb.and(predicateList.toArray(new Predicate[0]));
                } else {
                    return null;
                }
            }
        };
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

        @Override
        public String toString() {
            return "CaseId{" +
                    "caseId=" + caseId +
                    ", crm='" + crm + '\'' +
                    '}';
        }
    }
}