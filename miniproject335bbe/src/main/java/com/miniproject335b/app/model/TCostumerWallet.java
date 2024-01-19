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
@Table(name = "t_costumer_wallet")
public class TCostumerWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="costumer_id")
    private Long costumerId;
    @Column(name = "pin")
    private String pin;
    @Column(name = "balance")
    private Double balance;
    @Column(name = "barcode")
    private String barcode;
    @Column(name = "points")
    private Double points;
    
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
    private MCustomer mCostumer;

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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
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

    public MCustomer getmCostumer() {
        return mCostumer;
    }

    public void setmCostumer(MCustomer mCostumer) {
        this.mCostumer = mCostumer;
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
