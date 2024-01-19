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
@Table(name = "m_medical_facility")
public class MMedicalFacility {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "medical_facility_category_id")
	private Long medicalFacilityCategoryId;
	@Column(name = "location_id")
	private Long locationId;
	@Column(name = "full_address")
	private String fullAddress;
	@Column(name = "email")
	private String email;
	@Column(name = "phone_code")
	private String phoneCode;
	@Column(name = "phone")
	private String phone;
	@Column(name = "fax")
	private String fax;
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
	
	@OneToOne
	@JoinColumn(name = "medical_facility_category_id", insertable = false, updatable = false)
	private MMedicalFacilityCategory mMedicalFacilityCategory;
	
	@ManyToOne
	@JoinColumn(name = "location_id", insertable = false, updatable = false)
	private MLocation mLocation;
	
	
	
	public MMedicalFacilityCategory getmMedicalFacilityCategory() {
		return mMedicalFacilityCategory;
	}
	public void setmMedicalFacilityCategory(MMedicalFacilityCategory mMedicalFacilityCategory) {
		this.mMedicalFacilityCategory = mMedicalFacilityCategory;
	}
	public MLocation getmLocation() {
		return mLocation;
	}
	public void setmLocation(MLocation mLocation) {
		this.mLocation = mLocation;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getMedicalFacilityCategoryId() {
		return medicalFacilityCategoryId;
	}
	public void setMedicalFacilityCategoryId(Long medicalFacilityCategoryId) {
		this.medicalFacilityCategoryId = medicalFacilityCategoryId;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
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
	
	
}
