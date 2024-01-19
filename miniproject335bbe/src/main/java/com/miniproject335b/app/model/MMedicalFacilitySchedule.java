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
@Table(name = "m_medical_facility_schedule")
public class MMedicalFacilitySchedule {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

    @Column(name = "medical_facility_id")
    private Long medicalFacilityId;
    @Column(name = "day")
    private String day;
    @Column(name = "time_schedule_start")
    private String timeScheduleStart;
    @Column(name = "time_schedule_end")
    private String timeScheduleEnd;

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
    @JoinColumn(name = "medical_facility_id", insertable = false, updatable = false)
    private MMedicalFacility mMedicalFacility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedicalFacilityId() {
        return medicalFacilityId;
    }

    public void setMedicalFacilityId(Long medicalFacilityId) {
        this.medicalFacilityId = medicalFacilityId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeScheduleStart() {
        return timeScheduleStart;
    }

    public void setTimeScheduleStart(String timeScheduleStart) {
        this.timeScheduleStart = timeScheduleStart;
    }

    public String getTimeScheduleEnd() {
        return timeScheduleEnd;
    }

    public void setTimeScheduleEnd(String timeScheduleEnd) {
        this.timeScheduleEnd = timeScheduleEnd;
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

    public MMedicalFacility getmMedicalFacility() {
        return mMedicalFacility;
    }

    public void setmMedicalFacility(MMedicalFacility mMedicalFacility) {
        this.mMedicalFacility = mMedicalFacility;
    }

    
}
