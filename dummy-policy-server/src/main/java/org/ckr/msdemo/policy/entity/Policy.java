package org.ckr.msdemo.policy.entity;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Created by yukai.a.lin on 7/31/2017.
 */
@Entity
@Table(name = "POLICY", indexes = {
    @Index(name = "policy_index_1", columnList = "PLCNUM", unique = true)
})
public class Policy implements Serializable {
    private String policyNo;
    private int trancheNo;
    private String currency;
    private String statusCode;
    private int ownerId;
    private String ownerNum;
    private String ownerName;
    private Date inceptionDate;

    private static final long serialVersionUID = 7028458717583173058L;

    @Override
    public String toString() {
        return "Policy{" +
            "policyNo='" + policyNo + '\'' +
            ", trancheNo=" + trancheNo +
            ", currency='" + currency + '\'' +
            ", statusCode='" + statusCode + '\'' +
            ", ownerId=" + ownerId +
            ", ownerNum='" + ownerNum + '\'' +
            ", ownerName='" + ownerName + '\'' +
            ", inceptionDate=" + inceptionDate +
            '}';
    }

    @Id
    @Column(name = "PLCNUM", unique = true, nullable = false, length = 20)
    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    @Column(name = "TRANNO", length = 5)
    public int getTrancheNo() {
        return trancheNo;
    }

    public void setTrancheNo(int trancheNo) {
        this.trancheNo = trancheNo;
    }

    @Column(name = "CURRENCY", length = 3)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "STATCODE", length = 2)
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Column(name = "OWNERID", length = 19)
    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    @Column(name = "OWNERNUM", length = 100)
    public String getOwnerNum() {
        return ownerNum;
    }

    public void setOwnerNum(String ownerNum) {
        this.ownerNum = ownerNum;
    }

    @Column(name = "OWNERNAME", length = 100)
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    @Column(name = "INCPDATE")
    public Date getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(Date inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

}
