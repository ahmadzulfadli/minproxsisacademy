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
@Table(name = "t_customer_chat")
public class TCustomerChat {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	@Column(name = "customer_id")
	private Long customerId;
	@Column(name = "doctor_id")
	private Long doctorId;
	@Column(name="created_by", nullable = false)
	private Long createdBy;
	@Column(name="created_on", nullable = false)
	private Date createdOn;
	@Column(name="modified_by")
	private Long modifiedBy;
	@Column(name="modified_on")
	private Date modifiedOn;
	@Column(name="deleted_by")
	private Long deletedBy;
	@Column(name="deleted_on")
	private Date deletedOn;
	@Column(name="is_delete", nullable = false)
	private Boolean isDelete;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", insertable = false, updatable = false)
	private MDoctor mDoctor;
	
	@ManyToOne
	@JoinColumn(name = "customer_id", insertable = false, updatable = false)
	private MCustomer mCustomer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
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

	public MDoctor getmDoctor() {
		return mDoctor;
	}

	public void setmDoctor(MDoctor mDoctor) {
		this.mDoctor = mDoctor;
	}

	public MCustomer getmCustomer() {
		return mCustomer;
	}

	public void setmCustomer(MCustomer mCustomer) {
		this.mCustomer = mCustomer;
	}
	
	
	
}
