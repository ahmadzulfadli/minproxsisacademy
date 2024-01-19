// ----------------------------------------------------------------------
// Rancangan Response API MCourier
// {
//     "status": "success",
//     "message": "Success Message",
//     "pageSize": 1,
//     "currentPage": 0,
//     "totalItems": 1,
//     "totalPages": 1,
//     "data": {
//         "id": 1,
//         "name": "Gojek",
//         "createdBy": 1,
//         "createdOn": "2021-08-12T00:00:00.000+00:00",
//         "modifiedBy": null,
//         "modifiedOn": null,
//         "deletedBy": null,
//         "deletedOn": null,
//         "isDelete": false
//     }
// }
// ----------------------------------------------------------------------y


package com.miniproject335b.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject335b.app.model.MCourier;
import com.miniproject335b.app.repository.MCourierRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/courier/")
public class MCourierController {

    private Integer pageSize = 3;
    private Integer currentPage = 0;
    private Long totalItems = 0L;
    private Integer totalPages = 0;
    
    @Autowired
    private MCourierRepository mCourierRepository;

    // Response-------------------------------------------------------------------------------------
    private Map<String, Object> response(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", status);
        response.put("message", message);
        response.put("pageSize", this.pageSize);
        response.put("currentPage", this.currentPage);
        response.put("totalItems", this.totalItems);
        response.put("totalPages", this.totalPages);
        response.put("data", data);
        return response;
    }

    // CREATE---------------------------------------------------------------------------------------
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> createMCourier(@RequestBody MCourier mCourier){
        try {
            // Validtion ==============================
            // menghilangkan spasi di awal dan di akhir dari data mPaymentMethod pada field name
            mCourier.setName(mCourier.getName().trim());

            // cek apakah nama tidak null
            if (mCourier.getName() == null || mCourier.getName() == "") {
                return new ResponseEntity<>(response("failed", "Name cannot be null", new ArrayList<>()), HttpStatus.OK);
            }

            // cek apakah nama dengan isDelete=false sudah ada di database
            List<MCourier> mCourierList = this.mCourierRepository.findByIsDelete(false);
            for (MCourier mCourier2 : mCourierList) {
                if (mCourier.getName().toLowerCase().equals(mCourier2.getName().toLowerCase())) {
                    return new ResponseEntity<>(response("failed", "Name already exists", new ArrayList<>()), HttpStatus.OK);
                }
            }
            // end validation =========================

            mCourier.setCreatedBy(1L);
            mCourier.setCreatedOn(new Date());
            mCourier.setIsDelete(false);
            MCourier mCourierSaved = this.mCourierRepository.save(mCourier);
            if (mCourier.equals(mCourierSaved)) {
                return new ResponseEntity<>(response("success", "Saved Success", mCourierSaved), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Saved Failed", mCourierSaved), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Saved Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
    // READ----------------------------------------------------------------------------------------------------------
    @GetMapping("showall")
    public ResponseEntity<Map<String, Object>> getAllMCourier(){
        try {
            List<MCourier> mCourierList = this.mCourierRepository.findByIsDelete(false);
            if (mCourierList.isEmpty()) {
                return new ResponseEntity<>(response("failed", "Data Empty", mCourierList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", mCourierList), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    @GetMapping("show/{id}")
    public ResponseEntity<Map<String, Object>> getMCourierById(@PathVariable("id") Long id){
        try {
            MCourier mCourierSelected = this.mCourierRepository.findById(id).orElse(null);
            if (mCourierSelected == null) {
                return new ResponseEntity<>(response("failed", "Data Not Found", new ArrayList<>()), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", mCourierSelected), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    @GetMapping("show/pageable")
    public ResponseEntity<Map<String, Object>> getMCourierPageable(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "asc") String order) {

        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending()); 
            
            if (order.equals("desc")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending());
            }

            Page<MCourier> page = this.mCourierRepository.getMCourierPageable(pageable);
            List<MCourier> mCourier = page.getContent();
            if (mCourier.isEmpty()) {
                return new ResponseEntity<>(response("failed", "Failed Fetch Data", mCourier), HttpStatus.OK);
            } else {
                this.pageSize = page.getSize();
                this.currentPage = page.getNumber();
                this.totalItems = page.getTotalElements();
                this.totalPages = page.getTotalPages();

                return new ResponseEntity<>(response("success", "Success Fetch Data", mCourier), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    @GetMapping("show/search")
    public ResponseEntity<Map<String, Object>> getMCourierSearchPageable(
            @RequestParam() String name,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "asc") String order) {

        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending()); 
            
            if (order.equals("desc")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending());
            }

            Page<MCourier> page = this.mCourierRepository.findBySearch(pageable, name);
            List<MCourier> mCourier = page.getContent();
            if (mCourier.isEmpty()) {
                return new ResponseEntity<>(response("failed", "Failed Fetch Data", mCourier), HttpStatus.OK);
            } else {
                this.pageSize = page.getSize();
                this.currentPage = page.getNumber();
                this.totalItems = page.getTotalElements();
                this.totalPages = page.getTotalPages();

                return new ResponseEntity<>(response("success", "Success Fetch Data", mCourier), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // UPDATE--------------------------------------------------------------------------------------------------------
    @PutMapping("update")
    public ResponseEntity<Map<String, Object>> updateMCourier(@RequestBody MCourier mCourier){
        try {
            //V alidtion ==============================
            // menghilangkan spasi di awal dan di akhir dari data mPaymentMethod pada field name
            mCourier.setName(mCourier.getName().trim());

            // cek apakah nama tidak null
            if (mCourier.getName() == null || mCourier.getName() == "") {
                return new ResponseEntity<>(response("failed", "Name cannot be null", new ArrayList<>()), HttpStatus.OK);
            }

            // cek apakah nama dengan isDelete=false sudah ada di database
            List<MCourier> mCourierList = this.mCourierRepository.findByIsDelete(false);
            for (MCourier mCourier2 : mCourierList) {
                if (mCourier.getName().toLowerCase().equals(mCourier2.getName().toLowerCase())) {
                    return new ResponseEntity<>(response("failed", "Name already exists", new ArrayList<>()), HttpStatus.OK);
                }
            }
            // end validation =========================

            Long id = mCourier.getId();
            MCourier mCourierSelected = this.mCourierRepository.findById(id).orElse(null);

            mCourierSelected.setName(mCourier.getName());
            mCourierSelected.setModifiedBy(1L);
            mCourierSelected.setModifiedOn(new Date());
            MCourier mCourierUpdated = this.mCourierRepository.save(mCourierSelected);
            if (mCourierUpdated.equals(mCourierSelected)) {
                return new ResponseEntity<>(response("success", "Updated Success", mCourierUpdated), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("failed", "Updated Failed", mCourierUpdated), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Updated Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // DELETE--------------------------------------------------------------------------------------------------------
    @PutMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteMCourier(@PathVariable("id") Long id){
        try {
            MCourier mCourierSelected = this.mCourierRepository.findById(id).orElse(null);

            mCourierSelected.setIsDelete(true);
            mCourierSelected.setDeletedBy(1L);
            mCourierSelected.setDeletedOn(new Date());
            MCourier mCourierDeleted = this.mCourierRepository.save(mCourierSelected);
            if (mCourierDeleted.equals(mCourierSelected)) {
                return new ResponseEntity<>(response("success", "Deleted Success", mCourierDeleted), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("failed", "Deleted Failed", mCourierDeleted), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Deleted Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
}
