package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MMedicalFacility;

public interface MMedicalFacilityRepository extends JpaRepository<MMedicalFacility, Long>{

	@Query(value = "select * from m_medical_facility where is_delete = false", nativeQuery = true)
	List<MMedicalFacility> findByIsDelete();
}
