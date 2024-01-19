package com.miniproject335b.app.model;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "m_customer")
public class MCustomer {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	@Column(name="biodata_id")
	private Long biodataId;
	@Column(name="dob")
	private Date dob;
	@Column(name="gender", length = 1)
	private String gender;
	@Column(name="blood_group_id")
	private Long bloodGroupId;
	@Column(name="rhesus_type", length = 5)
	private String rhesusType;
	@Column(name="height")
	private Double height;
	@Column(name="weight")
	private Double weight;
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
	
	@OneToOne
	@JoinColumn(name = "biodata_id", insertable=false, updatable=false)
	private MBiodata mbiodata;
	@ManyToOne
	@JoinColumn(name = "blood_group_id", insertable=false, updatable=false)
	private MBloodGroup mbloodgroup;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBiodataId() {
		return biodataId;
	}
	public void setBiodataId(Long biodataId) {
		this.biodataId = biodataId;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Long getBloodGroupId() {
		return bloodGroupId;
	}
	public void setBloodGroupId(Long bloodGroupId) {
		this.bloodGroupId = bloodGroupId;
	}
	public String getRhesusType() {
		return rhesusType;
	}
	public void setRhesusType(String rhesusType) {
		this.rhesusType = rhesusType;
	}
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
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
	public MBiodata getMbiodata() {
		return mbiodata;
	}
	public void setMbiodata(MBiodata mbiodata) {
		this.mbiodata = mbiodata;
	}
	public MBloodGroup getMbloodgroup() {
		return mbloodgroup;
	}
	public void setMbloodgroup(MBloodGroup mbloodgroup) {
		this.mbloodgroup = mbloodgroup;
	}
	
}
