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
@Table(name = "t_doctor_office_treatment_price")
public class TDoctorOfficeTreatmentPrice {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

    @Column(name = "doctor_office_treatment_id")
    private Long doctorOfficeTreatmentId;
    @Column(name = "price")
    private Double price;
    @Column(name = "price_start_from")
    private Double priceStartFrom;
    @Column(name = "price_until_from")
    private Double priceUntilFrom;

    @Column(name = "created_by")
	private Long createdBy;
	@Column(name = "created_on")
	private Date createdOn;
	@Column(name = "modified_by")
	private Long modifiedBy;
	@Column(name = "modified_on")
	private Date modifiedOn;
	@Column(name = "deleted_by")
	private Long deletedBy;
	@Column(name = "deleted_on")
	private Date deletedOn;
	@Column(name = "is_delete")
	private Boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "doctor_office_treatment_id", insertable = false, updatable = false)
    private TDoctorOfficeTreatment tDoctorOfficeTreatment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorOfficeTreatmentId() {
        return doctorOfficeTreatmentId;
    }

    public void setDoctorOfficeTreatmentId(Long doctorOfficeTreatmentId) {
        this.doctorOfficeTreatmentId = doctorOfficeTreatmentId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceStartFrom() {
        return priceStartFrom;
    }

    public void setPriceStartFrom(Double priceStartFrom) {
        this.priceStartFrom = priceStartFrom;
    }

    public Double getPriceUntilFrom() {
        return priceUntilFrom;
    }

    public void setPriceUntilFrom(Double priceUntilFrom) {
        this.priceUntilFrom = priceUntilFrom;
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

    public TDoctorOfficeTreatment gettDoctorOfficeTreatment() {
        return tDoctorOfficeTreatment;
    }

    public void settDoctorOfficeTreatment(TDoctorOfficeTreatment tDoctorOfficeTreatment) {
        this.tDoctorOfficeTreatment = tDoctorOfficeTreatment;
    }

    
    
}
