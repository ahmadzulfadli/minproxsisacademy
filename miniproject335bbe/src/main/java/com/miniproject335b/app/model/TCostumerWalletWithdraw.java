package com.miniproject335b.app.model;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_costumer_wallet_withdraw")
public class TCostumerWalletWithdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "costumer_id")
    private Long costumerId;
    @Column(name = "wallet_default_nominal_id")
    private Long walletDefaultNominalId;
    @Column(name = "amount")
    private Integer amount;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_name")
    private String accountName;
    @Column(name = "otp")
    private Integer otp;

    @Column(name="created_by")
    private Long createdBy;
    @Column(name="created_on")
    private Date createdOn;
    @Column(name="modified_by")
    private Long modifiedBy;
    @Column(name="modified_on")
    private Date modifiedOn;
    @Column(name="deleted_by")
    private Long deletedBy;
    @Column(name="deleted_on")
    private Date deletedOn;
    @Column(name="is_delete")
    private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "costumer_id", insertable = false, updatable = false)
    private MCustomer mCustomer;
    
    @ManyToOne
    @JoinColumn(name = "wallet_default_nominal_id", insertable = false, updatable = false)
    private MWalletDefaultNominal mWalletDefaultNominal;

    @ManyToOne
    @JoinColumn(name = "created_by", insertable = false, updatable = false)
    private MUser mUserCreate;

    @ManyToOne
    @JoinColumn(name = "modified_by", insertable = false, updatable = false)
    private MUser mUserModify;

    @ManyToOne
    @JoinColumn(name = "deleted_by", insertable = false, updatable = false)
    private MUser mUserDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostumerId() {
        return costumerId;
    }

    public void setCostumerId(Long costumerId) {
        this.costumerId = costumerId;
    }

    public Long getWalletDefaultNominalId() {
        return walletDefaultNominalId;
    }

    public void setWalletDefaultNominalId(Long walletDefaultNominalId) {
        this.walletDefaultNominalId = walletDefaultNominalId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Long getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Long deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Date deletedOn) {
        this.deletedOn = deletedOn;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public MCustomer getmCustomer() {
        return mCustomer;
    }

    public void setmCustomer(MCustomer mCustomer) {
        this.mCustomer = mCustomer;
    }

    public MWalletDefaultNominal getmWalletDefaultNominal() {
        return mWalletDefaultNominal;
    }

    public void setmWalletDefaultNominal(MWalletDefaultNominal mWalletDefaultNominal) {
        this.mWalletDefaultNominal = mWalletDefaultNominal;
    }

    public MUser getmUserCreate() {
        return mUserCreate;
    }

    public void setmUserCreate(MUser mUserCreate) {
        this.mUserCreate = mUserCreate;
    }

    public MUser getmUserModify() {
        return mUserModify;
    }

    public void setmUserModify(MUser mUserModify) {
        this.mUserModify = mUserModify;
    }

    public MUser getmUserDelete() {
        return mUserDelete;
    }

    public void setmUserDelete(MUser mUserDelete) {
        this.mUserDelete = mUserDelete;
    }

    
}
