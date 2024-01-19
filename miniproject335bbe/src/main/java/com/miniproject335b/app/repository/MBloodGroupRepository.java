package com.miniproject335b.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MBloodGroup;

public interface MBloodGroupRepository extends JpaRepository<MBloodGroup, Long> {

	List<MBloodGroup> findByIsDelete(Boolean isDelete);

	@Query(value = "select * from m_blood_group mbg where id = ?1 and is_delete = false", nativeQuery = true)
	Optional<MBloodGroup> findById(Long id);
	
	@Query(value = "select * from m_blood_group mbg where is_delete = false and code ilike concat('%',?1,'%')", nativeQuery = true)
	List<MBloodGroup> findBySearch(String search);
	
	@Query(value = "select * from m_blood_group mbg where code = ?1 and is_delete = false", nativeQuery = true)
	Optional<MBloodGroup> findByCode(String code);
}
