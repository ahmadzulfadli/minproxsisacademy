package com.miniproject335b.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TDoctorOfficeSchedule;

public interface TDoctorOfficeScheduleRepository extends JpaRepository<TDoctorOfficeSchedule, Long>{
    
    @Query(value="SELECT tdos.* FROM t_doctor_office_schedule tdos JOIN m_medical_facility_schedule mmfs ON tdos.medical_facility_schedule_id = mmfs.id JOIN m_medical_facility mmf ON mmfs.medical_facility_id = mmf.id  WHERE tdos.doctor_id = 1 AND mmf.is_delete = false", nativeQuery = true)
    List<TDoctorOfficeSchedule> findByDoctorId(Long id);
}
