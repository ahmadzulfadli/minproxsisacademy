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
@Table(name = "t_current_doctor_specialization")
public class TCurrentDoctorSpecialization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false)
	private Long id;
	@Column(name = "doctor_id")
	private Long doctorId;
	@Column(name = "specialization_id")
	private Long specializationId;
	@Column(name="create_by", nullable = false)
	private Long createBy = 1L;
	@Column(name="create_on", nullable = false)
	private Date createOn = new Date();
	@Column(name="modified_by")
	private Long modifyBy;
	@Column(name="modified_on")
	private Date modifyOn;
	@Column(name="deleted_by")
	private Long deletedBy;
	@Column(name="deleted_on")
	private Date deletedOn;
	@Column(name="is_delete",nullable = false)
	private Boolean isDelete = false;
	
	@OneToOne
	@JoinColumn(name = "doctor_id", insertable = false, updatable = false)
	private MDoctor mDoctor;
	
	@ManyToOne
	@JoinColumn(name = "specialization_id", insertable = false, updatable = false)
	private Mspecialization mspecialization;

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

	public Long getSpecializationId() {
		return specializationId;
	}

	public void setSpecializationId(Long specializationId) {
		this.specializationId = specializationId;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public Long getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(Long modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
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

	public Mspecialization getMspecialization() {
		return mspecialization;
	}

	public void setMspecialization(Mspecialization mspecialization) {
		this.mspecialization = mspecialization;
	}
	
	
}
