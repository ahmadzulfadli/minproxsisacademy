package com.miniproject335b.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TCurrentDoctorSpecialization;

public interface TCurrentDoctorSpecializationRepository extends JpaRepository<TCurrentDoctorSpecialization, Long>{

	@Query(value = "select * from t_current_doctor_specialization where is_delete = false and doctor_id = ?1", nativeQuery = true)
	TCurrentDoctorSpecialization findByDoctorId(Long doctorId);
}
