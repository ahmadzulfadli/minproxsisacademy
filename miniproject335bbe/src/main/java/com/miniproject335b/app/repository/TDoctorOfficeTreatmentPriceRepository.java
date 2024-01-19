package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TDoctorOfficeTreatmentPrice;

public interface TDoctorOfficeTreatmentPriceRepository extends JpaRepository<TDoctorOfficeTreatmentPrice, Long>{
    
    // Fadli query start
	@Query(value = "SELECT tdotp.* \n" + //
			"FROM t_doctor_office_treatment_price tdotp \n" + //
			"JOIN t_doctor_office_treatment tdot \n" + //
			"ON tdotp.doctor_office_treatment_id = tdot.id\n" + //
			"JOIN t_doctor_office tdo\n" + //
			"ON tdot.doctor_office_id = tdo.id\n" + //
			"JOIN m_medical_facility mmf\n" + //
			"ON tdo.medical_facility_id = mmf.id\n" + //
			"WHERE tdo.doctor_id = ?1 AND mmf.medical_facility_category_id = 1", nativeQuery = true)
    List<TDoctorOfficeTreatmentPrice> findDoctorPriceChatOnline(Long doctorId);
}
