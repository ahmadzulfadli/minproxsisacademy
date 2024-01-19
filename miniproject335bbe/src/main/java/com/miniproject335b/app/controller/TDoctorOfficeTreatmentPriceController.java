// ----------------------------------------------------------------------
// Rancangan Response Price
// {
//     "status": "success",
//     "message": "Success Message",
//     "pageSize": 1,
//     "currentPage": 0,
//     "totalItems": 1,
//     "totalPages": 1,
//     "data": {
//         "id": 1,
//         "name": "Cash",
//         "createdBy": 1,
//         "createdOn": "2021-08-12T00:00:00.000+00:00",
//         "modifiedBy": null,
//         "modifiedOn": null,
//         "deletedBy": null,
//         "deletedOn": null,
//         "isDelete": false
//     }
// }
// ----------------------------------------------------------------------

package com.miniproject335b.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.TDoctorOfficeTreatmentPrice;
import com.miniproject335b.app.repository.TDoctorOfficeTreatmentPriceRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/office/treatment/")
public class TDoctorOfficeTreatmentPriceController {

    private Integer pageSize = 3;
    private Integer currentPage = 0;
    private Long totalItems = 0L;
    private Integer totalPages = 0;

    @Autowired
    private TDoctorOfficeTreatmentPriceRepository tDoctorOfficeTreatmentPriceRepository;

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
    @GetMapping("price/doctor/{id}")
    public ResponseEntity<Map<String, Object>> getDoctorOfficeTreatmentPriceByIdDoctor(@PathVariable("id") Long id) {
        try {
            TDoctorOfficeTreatmentPrice tDoctorOfficeTreatmentPrice = this.tDoctorOfficeTreatmentPriceRepository.findDoctorPriceChatOnline(id).get(0);
            if (tDoctorOfficeTreatmentPrice == null) {
                return new ResponseEntity<>(response("success", "No Data", tDoctorOfficeTreatmentPrice), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", tDoctorOfficeTreatmentPrice), HttpStatus.OK);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
    // UPDATE --------------------------------------------------------------------------------------------------------------------------------
    // DELETE --------------------------------------------------------------------------------------------------------------------------------
}
