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
@Table(name="t_token")
public class TToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "email")
	private String email;
	@Column(name = "token")
	private String token;
	@Column(name = "expired_on")
	private Date expiredOn;
	@Column(name = "is_expired")
	private Boolean isExpired;
	@Column(name = "used_for")
	private String usedFor;
	@Column(name = "created_by", nullable=false)
	private Long createdBy;
	@Column(name = "created_on", nullable=false)
	private Date createdOn;
	@Column(name = "modified_by")
	private Long modifiedBy;
	@Column(name = "modified_on")
	private Date modifiedOn;
	@Column(name = "deleted_by")
	private Long deletedBy;
	@Column(name = "deleted_on")
	private Date deletedOn;
	@Column(name = "is_delete", nullable=false)
	private Boolean isDelete = false;
	
	@ManyToOne
	@JoinColumn(name = "user_id", insertable=false, updatable=false)
	private MUser mUser;
	
	@ManyToOne
	@JoinColumn(name = "created_by", insertable=false, updatable=false)
	private MUser mUserCreate;

	@ManyToOne
	@JoinColumn(name = "modified_by", insertable=false, updatable=false)
	private MUser mUserModify;
	
	@ManyToOne
	@JoinColumn(name = "deleted_by", insertable=false, updatable=false)
	private MUser mUserDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiredOn() {
		return expiredOn;
	}

	public void setExpiredOn(Date expiredOn) {
		this.expiredOn = expiredOn;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getUsedFor() {
		return usedFor;
	}

	public void setUsedFor(String usedFor) {
		this.usedFor = usedFor;
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

	public MUser getmUser() {
		return mUser;
	}

	public void setmUser(MUser mUser) {
		this.mUser = mUser;
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
