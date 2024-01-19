package com.miniproject335b.app.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="m_blood_group")
public class MBloodGroup {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	@Column(name="code", length=5)
	private String code;
	@Column(name="description", length=255)
	private String description;
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
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
