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

import com.miniproject335b.app.model.TDoctorOfficeSchedule;
import com.miniproject335b.app.repository.TDoctorOfficeScheduleRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/office/schedule/")
public class TDoctorOfficeScheduleController {
    private Integer pageSize = 3;
    private Integer currentPage = 0;
    private Long totalItems = 0L;
    private Integer totalPages = 0;

    @Autowired
    private TDoctorOfficeScheduleRepository tDoctorOfficeScheduleRepository;
    

    // Response-----------------------------------------------------------------------------------------------------------
    private Map<String, Object> response(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("pageSize", this.pageSize);
        response.put("currentPage", this.currentPage);
        response.put("totalItems", this.totalItems);
        response.put("totalPages", this.totalPages);
        response.put("data", data);

        return response;
    }
    
    // CREATE --------------------------------------------------------------------------------------------------------------------------------
    // READ ----------------------------------------------------------------------------------------------------------------------------------
    @GetMapping("doctor/{id}")
    public ResponseEntity<Map<String, Object>> getDoctorOfficeSchedule(@PathVariable("id") Long id) {
        try {
            List<TDoctorOfficeSchedule> tDoctorOfficeSchedule = this.tDoctorOfficeScheduleRepository.findByDoctorId(id);
            if (tDoctorOfficeSchedule.isEmpty()) {
                return new ResponseEntity<>(response("success", "No Data", tDoctorOfficeSchedule), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", tDoctorOfficeSchedule), HttpStatus.OK);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
    // UPDATE --------------------------------------------------------------------------------------------------------------------------------
    // DELETE --------------------------------------------------------------------------------------------------------------------------------
}
