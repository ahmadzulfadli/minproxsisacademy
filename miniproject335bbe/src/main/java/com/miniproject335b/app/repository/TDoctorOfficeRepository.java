package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TDoctorOffice;

public interface TDoctorOfficeRepository extends JpaRepository<TDoctorOffice, Long>{
	@Query(value = "select * from t_doctor_office where is_delete = false", nativeQuery = true)
	List<TDoctorOffice> findByIsDelete();
	
	@Query(value = "select * from t_doctor_office tdo where is_delete = false and doctor_id = ?1", nativeQuery = true)
	List<TDoctorOffice> findByDoctorId(Long id);

	// query detail dokter
	@Query(value = "SELECT tdo.* \n" + //
		"FROM t_doctor_office tdo \n" + //
		"JOIN m_medical_facility mmf \n" + //
		"ON tdo.medical_facility_id = mmf.id \n" + //
		"WHERE mmf.location_id IS NOT NULL \n" + //
		"AND tdo.is_delete = false \n" + //
		"AND tdo.doctor_id = ?1 ORDER BY tdo.start_date DESC", nativeQuery = true)
	List<TDoctorOffice> findLocationByDoctorId(Long id);
}
