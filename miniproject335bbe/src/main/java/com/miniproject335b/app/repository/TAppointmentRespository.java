package com.miniproject335b.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TAppointment;

public interface TAppointmentRespository extends JpaRepository<TAppointment, Long>{

	@Query(value = "select count(ta.id)  from t_appointment ta join t_doctor_office tdo on ta.doctor_office_id = tdo.id where doctor_id = ?1 and ta.is_delete = false", nativeQuery = true)
	Long countAppointmentByDoctorId(Long DoctorId);
}
