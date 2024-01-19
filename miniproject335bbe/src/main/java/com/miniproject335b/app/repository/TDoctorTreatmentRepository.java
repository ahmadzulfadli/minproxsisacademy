package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TDoctorTreatment;

public interface TDoctorTreatmentRepository extends JpaRepository<TDoctorTreatment, Long>{

	@Query(value = "select * from t_doctor_treatment where is_delete = false and doctor_id = ?1", nativeQuery = true)
	List<TDoctorTreatment> findByDoctorId(Long doctorId);
	
	@Query(value="select * from t_doctor_treatment where name ilike concat('%',?1,'%') and is_delete = false and doctor_id = ?2", nativeQuery = true)
	List<TDoctorTreatment> findByName(String name, Long doctorId);
	
	@Query(value="select * from t_doctor_treatment where name ilike concat('%',?1,'%') and is_delete = false", nativeQuery = true)
	Page<TDoctorTreatment> findByName(String name, Pageable page);
}
