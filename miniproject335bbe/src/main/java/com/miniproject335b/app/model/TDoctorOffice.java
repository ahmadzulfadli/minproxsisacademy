package com.miniproject335b.app.model;

import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_doctor_office")
public class TDoctorOffice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "doctor_id")
	private Long doctorId;
	@Column(name = "medical_facility_id")
	private Long medicalFacilityId;
	@Column(name = "specialization")
	@Nonnull
	private String specialization;
	@Column(name = "start_date")
	@Nonnull
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
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
	@JoinColumn(name = "doctor_id", insertable = false, updatable = false)
	private MDoctor mDoctor;
	
	@ManyToOne
	@JoinColumn(name = "medical_facility_id", insertable = false, updatable = false)
	private MMedicalFacility mMedicalFacility;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getMedicalFacilityId() {
		return medicalFacilityId;
	}

	public void setMedicalFacilityId(Long medicalFacilityId) {
		this.medicalFacilityId = medicalFacilityId;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public MMedicalFacility getmMedicalFacility() {
		return mMedicalFacility;
	}

	public void setmMedicalFacility(MMedicalFacility mMedicalFacility) {
		this.mMedicalFacility = mMedicalFacility;
	}
	
	
	
	
	
}
