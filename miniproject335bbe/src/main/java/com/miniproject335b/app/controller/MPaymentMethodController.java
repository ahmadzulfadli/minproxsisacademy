// ----------------------------------------------------------------------
// Rancangan Response API MPaymentMethod
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
// ----------------------------------------------------------------------y

package com.miniproject335b.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.miniproject335b.app.model.MPaymentMethod;
import com.miniproject335b.app.repository.MPaymentMethodRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/paymentmethod/")
public class MPaymentMethodController {

    private Integer pageSize = 3;
    private Integer currentPage = 0;
    private Long totalItems = 0L;
    private Integer totalPages = 0;

    @Autowired
    private MPaymentMethodRepository mPaymentMethodRepository;

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

    // CREATE--------------------------------------------------------------------------------------------------------
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> createMPaymentMethod(@RequestBody MPaymentMethod mPaymentMethod) {
        try {
            //Validtion =========================
            // menghilangkan spasi di awal dan di akhir dari data mPaymentMethod pada field name
            mPaymentMethod.setName(mPaymentMethod.getName().trim());

            // cek apakah nama tidak null
            if (mPaymentMethod.getName() == "" || mPaymentMethod.getName() == null) {
                return new ResponseEntity<>(response("failed", "Name Cannot Be Null", new ArrayList<>()),HttpStatus.OK);
            }
            // cek apakah nama dengan isDelete=false sudah ada di database
            List<MPaymentMethod> listMPaymentMethod = this.mPaymentMethodRepository.findByIsDelete(false);
            for (MPaymentMethod paymentMethod : listMPaymentMethod) {
                if (paymentMethod.getName().toLowerCase().equals(mPaymentMethod.getName().toLowerCase())) {
                    return new ResponseEntity<>(response("failed", "Name Already Exist", new ArrayList<>()),HttpStatus.OK);
                }
            }
            // end validation =========================

            mPaymentMethod.setCreatedBy(1L);
            mPaymentMethod.setCreatedOn(new Date());
            mPaymentMethod.setIsDelete(false);
            MPaymentMethod mPaymentMethodSaved = this.mPaymentMethodRepository.save(mPaymentMethod);

            if (mPaymentMethod.equals(mPaymentMethodSaved)) {
                return new ResponseEntity<>(response("success", "Saved Success", mPaymentMethodSaved),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Saved Failed", mPaymentMethodSaved), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Saved Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // READ----------------------------------------------------------------------------------------------------------
    @GetMapping("showall")
    public ResponseEntity<Map<String, Object>> getAllMPaymentMethod() {
        try {
            List<MPaymentMethod> listMPaymentMethod = this.mPaymentMethodRepository.findByIsDelete(false);
            if (listMPaymentMethod.isEmpty()) {
                return new ResponseEntity<>(response("success", "No Data", listMPaymentMethod), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", listMPaymentMethod),
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    @GetMapping("show/{id}")
    public ResponseEntity<Map<String, Object>> getMPaymentMethodById(@PathVariable("id") Long id) {
        try {
            MPaymentMethod mPaymentMethod = this.mPaymentMethodRepository.findById(id).orElse(null);
            if (mPaymentMethod == null) {
                return new ResponseEntity<>(response("success", "No Data", mPaymentMethod), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", mPaymentMethod), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    @GetMapping("show/pageable")
    public ResponseEntity<Map<String, Object>> getMPaymentMethodPageable(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "asc") String order) {

        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending()); 
            
            if (order.equals("desc")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending());
            }

            Page<MPaymentMethod> page;
            if(search.trim().equals("")){
                page = this.mPaymentMethodRepository.getMPaymentMethodByPageable(pageable);
            }else{
                page = this.mPaymentMethodRepository.findBySearch(pageable, search);
            }

            List<MPaymentMethod> mPaymentMethods = page.getContent();
            if (mPaymentMethods.isEmpty()) {
                return new ResponseEntity<>(response("failed", "Failed Fetch Data", mPaymentMethods), HttpStatus.OK);
            } else {
                this.pageSize = page.getSize();
                this.currentPage = page.getNumber();
                this.totalItems = page.getTotalElements();
                this.totalPages = page.getTotalPages();

                return new ResponseEntity<>(response("success", "Success Fetch Data", mPaymentMethods), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // UPDATE--------------------------------------------------------------------------------------------------------
    @PutMapping("update/{id}")
    public ResponseEntity<Map<String, Object>> updateMPaymentMethod(@PathVariable("id") Long id,
            @RequestBody MPaymentMethod mPaymentMethod) {
        
        //Validtion =========================
        // menghilangkan spasi di awal dan di akhir dari data mPaymentMethod pada field name
        mPaymentMethod.setName(mPaymentMethod.getName().trim());

        // cek apakah nama tidak null
        if (mPaymentMethod.getName() == "" || mPaymentMethod.getName() == null) {
            return new ResponseEntity<>(response("failed", "Name Cannot Be Null", new ArrayList<>()),HttpStatus.OK);
        }
        // cek apakah nama dengan isDelete=false sudah ada di database
        List<MPaymentMethod> listMPaymentMethod = this.mPaymentMethodRepository.findByIsDelete(false);
        for (MPaymentMethod paymentMethod : listMPaymentMethod) {
            if (paymentMethod.getName().toLowerCase().equals(mPaymentMethod.getName().toLowerCase())) {
                return new ResponseEntity<>(response("failed", "Name Already Exist", new ArrayList<>()),HttpStatus.OK);
            }
        }
        // end validation =========================

        MPaymentMethod mPaymentMethodUpdate = this.mPaymentMethodRepository.findById(id).orElse(null);
        if (mPaymentMethodUpdate != null) {
            mPaymentMethodUpdate.setName(mPaymentMethod.getName());
            mPaymentMethodUpdate.setModifiedBy(1L);
            mPaymentMethodUpdate.setModifiedOn(new Date());

            MPaymentMethod mPaymentMethodSaved = this.mPaymentMethodRepository.save(mPaymentMethodUpdate);
            if (mPaymentMethodSaved.equals(mPaymentMethodUpdate)) {
                return new ResponseEntity<>(response("success", "Modified Success", mPaymentMethodSaved),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Modified Failed", mPaymentMethodSaved), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(response("failed", "Modified Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // DELETE--------------------------------------------------------------------------------------------------------
    @PutMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteMPaymentMethod(@PathVariable("id") Long id) {
        MPaymentMethod mPaymentMethodDelete = this.mPaymentMethodRepository.findById(id).orElse(null);
        if (mPaymentMethodDelete != null) {
            mPaymentMethodDelete.setIsDelete(true);
            mPaymentMethodDelete.setDeletedBy(1L);
            mPaymentMethodDelete.setDeletedOn(new Date());

            MPaymentMethod mPaymentMethodSaved = this.mPaymentMethodRepository.save(mPaymentMethodDelete);
            if (mPaymentMethodSaved.equals(mPaymentMethodDelete)) {
                return new ResponseEntity<>(response("success", "Deleted Success", mPaymentMethodSaved),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Deleted Failed", mPaymentMethodSaved), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(response("failed", "Deleted Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
}