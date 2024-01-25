package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MDoctor;

public interface MDoctorRepository extends JpaRepository<MDoctor, Long>{

	@Query(value = "select * from m_doctor where is_delete = false", nativeQuery = true)
	List<MDoctor> findByIsDelete();
	
	Page<MDoctor> findByIsDelete(Pageable page, Boolean isDelete);
	
	@Query(value = "select * from m_doctor where biodata_id = ?1 and is_delete = false", nativeQuery = true)
	MDoctor findByDoctorId(Long id);
	
	// Fadli query start
	@Query(value = "SELECT tdotp.price_start_from, tdotp.price_until_from, mmf.id, mmf.name, mmf.full_address \n" + //
			"FROM t_doctor_office_treatment_price tdotp \n" + //
			"JOIN t_doctor_office_treatment tdot \n" + //
			"ON tdotp.doctor_office_treatment_id = tdot.id\n" + //
			"JOIN t_doctor_office tdo\n" + //
			"ON tdot.doctor_office_id = tdo.id\n" + //
			"JOIN m_medical_facility mmf\n" + //
			"ON tdo.medical_facility_id = mmf.id\n" + //
			"WHERE tdo.doctor_id = ?1 AND mmf.medical_facility_category_id != 1 AND tdo.end_date IS NULL", nativeQuery = true)
    List<Object> findDoctorDetail(Long doctorId);

	@Query(value = "SELECT mmfs.day,mmfs.time_schedule_start, mmfs.time_schedule_end \n" + //
			"FROM t_doctor_office_schedule tdos\n" + //
			"JOIN m_medical_facility_schedule mmfs\n" + //
			"ON tdos.medical_facility_schedule_id = mmfs.id\n" + //
			"JOIN m_medical_facility mmf\n" + //
			"ON mmfs.medical_facility_id = mmf.id\n" + //
			"WHERE tdos.doctor_id = ?1 AND mmf.id = ?2 ORDER BY mmfs.day DESC",nativeQuery = true)
	List<Object> listSchedule(Long doctorId, Long medicalFacilityId);

	// Fadli query end
	
	@Query(value = "select * from m_doctor md\r\n"
			+ "join m_biodata mb on mb.id = md.biodata_id \r\n"
			+ "where mb.fullname ilike concat('%',?1,'%') and md.is_delete = false and mb.is_delete = false", nativeQuery = true)
	Page<MDoctor> findByNamePageable(String name,Pageable page);
	
	@Query(value = "select  md.id ,mb.fullname ,mmf.name,ms.\"name\",tdt.\"name\", \r\n"
			+ "ml.\"name\", ml.parent_id,mll.name, \r\n"
			+ "(extract (year from now()) - extract (year from tdo.start_date) )as pengalaman, \r\n"
			+ "mmfc.name, mb.image_path, mmfs.day \r\n"
			+ "from m_doctor md \r\n"
			+ "left join m_biodata mb on mb.id = md.biodata_id \r\n"
			+ "left join t_current_doctor_specialization tcds on tcds.doctor_id = md.id \r\n"
			+ "left join m_specialization ms on ms.id = tcds.specialization_id\r\n"
			+ "left join t_doctor_treatment tdt on tdt.doctor_id  = md.id \r\n"
			+ "left join t_doctor_office tdo on md.id = tdo.doctor_id \r\n"
			+ "left join m_medical_facility mmf on mmf.id = tdo.medical_facility_id \r\n"
			+ "left join m_medical_facility_category mmfc on mmf.medical_facility_category_id = mmfc.id  \r\n"
			+ "left join m_medical_facility_schedule mmfs on mmfs.medical_facility_id = mmf.id \r\n"
			+ "left join m_location ml on ml.id = mmf.location_id \r\n"
			+ "left join m_location_level mll on mll.id = location_level_id \r\n"
			+ "where mb.fullname ilike concat('%',?1,'%') and ms.name ilike concat('%',?2,'%') "
			+ "and tdt.name ilike concat('%',?3,'%')and ml.name ilike concat('%',?4,'%')"
			+ "order by md.id asc", nativeQuery = true)
	Page<Object> findBySearch(String name, String msid, String tdtid, String mlid, Pageable page);
	
	
}
