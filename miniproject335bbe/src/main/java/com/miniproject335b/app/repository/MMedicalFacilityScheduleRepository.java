package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MMedicalFacilitySchedule;

public interface MMedicalFacilityScheduleRepository extends JpaRepository<MMedicalFacilitySchedule, Long>{

    // Fadli query start
    // @Query(value = "SELECT mmfs.*\n" + //
	// 		"FROM t_doctor_office_schedule tdos\n" + //
	// 		"JOIN m_medical_facility_schedule mmfs\n" + //
	// 		"ON tdos.medical_facility_schedule_id = mmfs.id\n" + //
	// 		"JOIN m_medical_facility mmf\n" + //
	// 		"ON mmfs.medical_facility_id = mmf.id\n" + //
	// 		"WHERE tdos.doctor_id = ?1 AND mmf.medical_facility_category_id = 1",nativeQuery = true)
	// List<MMedicalFacilitySchedule> listScheduleOnline(Long doctorId);

	@Query(value = "SELECT mmfs.*, tdo.end_date\n" + //
			"FROM m_medical_facility_schedule mmfs\n" + //
			"JOIN m_medical_facility mmf\n" + //
			"ON mmfs.medical_facility_id = mmf.id\n" + //
			"JOIN t_doctor_office tdo\n" + //
			"ON tdo.medical_facility_id = mmf.id\n" + //
			"WHERE mmf.medical_facility_category_id != 1 AND tdo.doctor_id = ?1 AND tdo.end_date IS NULL",nativeQuery = true)
	List<MMedicalFacilitySchedule> listScheduleOnline(Long doctorId);
    // Fadli query end
}
