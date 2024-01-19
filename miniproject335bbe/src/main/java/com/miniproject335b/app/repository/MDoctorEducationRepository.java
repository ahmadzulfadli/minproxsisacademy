package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MBiodata;
import com.miniproject335b.app.model.MDoctorEducation;

public interface MDoctorEducationRepository extends JpaRepository<MDoctorEducation, Long>{

	@Query(value = "select * from m_doctor_education where is_delete = false and doctor_id = ?1 order by end_year", nativeQuery = true)
	List<MDoctorEducation> findByDoctorId(Long doctorId);
}
