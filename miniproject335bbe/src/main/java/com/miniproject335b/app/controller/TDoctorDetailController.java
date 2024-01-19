package com.miniproject335b.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MMedicalFacilitySchedule;
import com.miniproject335b.app.model.TDoctorOffice;
import com.miniproject335b.app.repository.MDoctorRepository;
import com.miniproject335b.app.repository.MMedicalFacilityScheduleRepository;
import com.miniproject335b.app.repository.TDoctorOfficeRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/doctor/")
public class TDoctorDetailController {

    @Autowired
	private MDoctorRepository mDoctorRepository;

	@Autowired
	private TDoctorOfficeRepository tDoctorOfficeRepository;

	@Autowired
	private MMedicalFacilityScheduleRepository mMedicalFacilityScheduleRepository;

    // Response-----------------------------------------------------------------------------------------------------------
    private Map<String, Object> response(String status, String message,Object detail, Object schedule) {

		List<String> keyData = new ArrayList<>();
		keyData.add("priceStartFrom");
		keyData.add("priceUntilFrom");
		keyData.add("id");
		keyData.add("name");
		keyData.add("fullAddress");
		
		
		List<Object> responseDetail = new ArrayList<>();
		List<Object> doctorDetail = (List<Object>) detail;
		for (int i = 0; i < doctorDetail.size(); i++) {
			Map<String, Object> doctorDetailMap = new HashMap<>();
			Object[] doctorDetailObj = (Object[]) doctorDetail.get(i);
			for (int j = 0; j < doctorDetailObj.length; j++) {
				doctorDetailMap.put(keyData.get(j), doctorDetailObj[j]);
			}
			responseDetail.add(doctorDetailMap);
		}


		List<String> keySchedule = new ArrayList<>();
		keySchedule.add("day");
		keySchedule.add("timeScheduleStart");
		keySchedule.add("timeScheduleEnd");
		
		List<Object> responseSchedule = new ArrayList<>();
		Map<String, Object> scheduleMap = (Map<String, Object>) schedule;
		for (int i = 0; i < scheduleMap.size(); i++) {
			List<Object> scheduleList = (List<Object>) scheduleMap.get("data"+i);
			List<Object> scheduleListTemp = new ArrayList<>();
			for (int j = 0; j < scheduleList.size(); j++) {
				Map<String, Object> scheduleMapTemp = new HashMap<>();
				Object[] scheduleObj = (Object[]) scheduleList.get(j);
				for (int k = 0; k < scheduleObj.length; k++) {
					scheduleMapTemp.put(keySchedule.get(k), scheduleObj[k]);
				}
				scheduleListTemp.add(scheduleMapTemp);
			}
			responseSchedule.add(scheduleListTemp);
		}

        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("detail", responseDetail);
		response.put("schedule", responseSchedule);
		
        return response;
    }

	private Map<String, Object> response(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);

        return response;
    }
	// Doctor detail-----------------------------------------------------------------------------------------------------------
	@GetMapping("detail/{id}")
	public ResponseEntity<Map<String, Object>> getDetailDoctorById(@PathVariable("id") Long id){
		try {
			List<Object> doctorDetail= this.mDoctorRepository.findDoctorDetail(id);
			
			List<Object> doctorDetailTemp = (List<Object>) doctorDetail;

			Long medicalFacilityId = 0L;
			Map<String, Object> schedule = new HashMap<>();
			for (int i = 0; i < doctorDetailTemp.size(); i++) {
				Object[] doctorDetailObj = (Object[]) doctorDetailTemp.get(i);
				medicalFacilityId = (Long) doctorDetailObj[2];
				List<Object> scheduleData = this.mDoctorRepository.listSchedule(id, medicalFacilityId);
				schedule.put("data"+i, scheduleData);
			}

			if (doctorDetail.isEmpty()) {
				return new ResponseEntity<>(response("success", "No Data",doctorDetail, schedule), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(response("success", "Success Fetch Data",doctorDetail, schedule), HttpStatus.OK);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(response("failed", "Failed Fetch Data",new ArrayList<>(), new ArrayList<>()), HttpStatus.OK);
		}
	}
    
    @GetMapping("detail/online/{id}")
    public ResponseEntity<Map<String, Object>> getDetailDoctorOnlineById(@PathVariable("id") Long id){
        try {
			List<MMedicalFacilitySchedule> listData = this.mMedicalFacilityScheduleRepository.listScheduleOnline(id);
			if (listData.isEmpty()) {
				return new ResponseEntity<>(response("success", "No Data",listData), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(response("success", "Success Fetch Data",listData), HttpStatus.OK);
			}	
        } catch(Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data",new ArrayList<>(), new ArrayList<>()), HttpStatus.OK);
        }
    }

	// office location
	@GetMapping("detail/location/{id}")
	public ResponseEntity<Map<String, Object>> getDetailDoctorLocationById(@PathVariable("id") Long id){
		try {
			List<TDoctorOffice> listData = this.tDoctorOfficeRepository.findLocationByDoctorId(id);
			if (listData.isEmpty()) {
				return new ResponseEntity<>(response("success", "No Data",listData), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(response("success", "Success Fetch Data",listData), HttpStatus.OK);
			}
		} catch(Exception e) {
			return new ResponseEntity<>(response("failed", "Failed Fetch Data",new ArrayList<>()), HttpStatus.OK);
		}
			
	}
    
}
