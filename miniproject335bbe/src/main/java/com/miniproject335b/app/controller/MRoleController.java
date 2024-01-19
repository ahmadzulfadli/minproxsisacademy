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

import com.miniproject335b.app.model.MRole;
import com.miniproject335b.app.repository.MRoleRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/role/")
public class MRoleController {
	@Autowired
	private MRoleRepository mRoleRepository;
	
	private Integer pageSize = 3;
    private Integer currentPage = 0;
    private Long totalItems = 0L;
    private Integer totalPages = 0;
	
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
	
	@GetMapping("showall")
    public ResponseEntity<Map<String, Object>> getAllMRole() {
        try {
            List<MRole> listMRole = this.mRoleRepository.findByIsDelete(false);
            if (listMRole.isEmpty()) {
                return new ResponseEntity<>(response("success", "No Data", listMRole), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", listMRole),
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
	
	@GetMapping("showpage")
	public ResponseEntity<Map<String, Object>> getMRolePageable(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "asc") String order) {

        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending()); 
            
            if (order.equals("desc")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending());
            }

            Page<MRole> page = this.mRoleRepository.getMRoleByPageable(pageable);
            List<MRole> mRole = page.getContent();
            if (mRole.isEmpty()) {
                return new ResponseEntity<>(response("failed", "Failed Fetch Data", mRole), HttpStatus.OK);
            } else {
                this.pageSize = page.getSize();
                this.currentPage = page.getNumber();
                this.totalItems = page.getTotalElements();
                this.totalPages = page.getTotalPages();

                return new ResponseEntity<>(response("success", "Success Fetch Data", mRole), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
	
	@GetMapping("show/search")
    public ResponseEntity<Map<String, Object>> getMRoleBySearch(@RequestParam("name") String name){
        try {
            List<MRole> listMRole = this.mRoleRepository.findBySearch(name);
            if (listMRole.isEmpty()) {
                return new ResponseEntity<>(response("success", "No Data", listMRole), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", listMRole),
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
	
	@GetMapping("showpage/search")
	public ResponseEntity<Map<String, Object>> getMRoleSearchPageable(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "asc") String order,
            @RequestParam("name") String name) {

        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").ascending()); 
            
            if (order.equals("desc")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending());
            }

            Page<MRole> page = this.mRoleRepository.findBySearchPageable(name,pageable);
            List<MRole> mRole = page.getContent();
            if (mRole.isEmpty()) {
                return new ResponseEntity<>(response("failed", "Failed Fetch Data", mRole), HttpStatus.OK);
            } else {
                this.pageSize = page.getSize();
                this.currentPage = page.getNumber();
                this.totalItems = page.getTotalElements();
                this.totalPages = page.getTotalPages();

                return new ResponseEntity<>(response("success", "Success Fetch Data", mRole), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
	
	@PostMapping("add/{user}")
    public ResponseEntity<Map<String, Object>> createMRole(@RequestBody MRole mRole, @PathVariable("user") Long user) {
        try {
            //Validtion =========================
            // menghilangkan spasi di awal dan di akhir dari data mPaymentMethod pada field name
            mRole.setName(mRole.getName().trim());

            // cek apakah nama tidak null
            if (mRole.getName() == "" || mRole.getName() == null) {
                return new ResponseEntity<>(response("failed", "Name Cannot Be Null", new ArrayList<>()),HttpStatus.OK);
            }
            // menghilangkan spasi di awal dan di akhir dari data mRole pada field name
            mRole.setCode(mRole.getCode().trim());

            // cek apakah nama tidak null
            if (mRole.getCode() == "" || mRole.getCode() == null) {
                return new ResponseEntity<>(response("failed", "Code Cannot Be Null", new ArrayList<>()),HttpStatus.OK);
            }
            // cek apakah nama dengan isDelete=false sudah ada di database
            List<MRole> listMRole = this.mRoleRepository.findByIsDelete(false);
            for (MRole role : listMRole) {
                if (role.getName().equals(mRole.getName())) {
                    return new ResponseEntity<>(response("failed", "Name Already Exist", new ArrayList<>()),HttpStatus.OK);
                }
                if (role.getCode().equals(mRole.getCode())) {
                    return new ResponseEntity<>(response("failed", "Code Already Exist", new ArrayList<>()),HttpStatus.OK);
                }
            }
            // end validation =========================

            mRole.setCreatedBy(user);
            mRole.setCreatedOn(new Date());
            mRole.setIsDelete(false);
            MRole mRoleSaved = this.mRoleRepository.save(mRole);

            if (mRole.equals(mRoleSaved)) {
                return new ResponseEntity<>(response("success", "Saved Success", mRoleSaved),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Saved Failed", mRoleSaved), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Saved Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
	
	@GetMapping("show/{id}")
    public ResponseEntity<Map<String, Object>> getMRoleById(@PathVariable("id") Long id) {
        try {
            MRole mRole = this.mRoleRepository.findById(id).orElse(null);
            if (mRole == null) {
                return new ResponseEntity<>(response("success", "No Data", mRole), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", mRole), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
	
	@PutMapping("edit/{id}/{user}")
    public ResponseEntity<Map<String, Object>> updateMRole(@PathVariable("id") Long id,
            @RequestBody MRole mRole, @PathVariable("user") Long user) {
        
		//Validtion =========================
        // menghilangkan spasi di awal dan di akhir dari data mPaymentMethod pada field name
        mRole.setName(mRole.getName().trim());

        // cek apakah nama tidak null
        if (mRole.getName() == "" || mRole.getName() == null) {
            return new ResponseEntity<>(response("failed", "Name Cannot Be Null", new ArrayList<>()),HttpStatus.OK);
        }
        // menghilangkan spasi di awal dan di akhir dari data mRole pada field name
        mRole.setCode(mRole.getCode().trim());

        // cek apakah nama tidak null
        if (mRole.getCode() == "" || mRole.getCode() == null) {
            return new ResponseEntity<>(response("failed", "Code Cannot Be Null", new ArrayList<>()),HttpStatus.OK);
        }
        // cek apakah nama dengan isDelete=false sudah ada di database
        List<MRole> listMRole = this.mRoleRepository.findByIsDelete(false);
        for (MRole role : listMRole) {
            if (role.getName().equals(mRole.getName())) {
                return new ResponseEntity<>(response("failed", "Name Already Exist", new ArrayList<>()),HttpStatus.OK);
            }
            if (role.getCode().equals(mRole.getCode())) {
                return new ResponseEntity<>(response("failed", "Code Already Exist", new ArrayList<>()),HttpStatus.OK);
            }
        }
        // end validation =========================

        MRole mRoleUpdate = this.mRoleRepository.findById(id).orElse(null);
        if (mRoleUpdate != null) {
            mRoleUpdate.setName(mRole.getName());
            mRoleUpdate.setCode(mRole.getCode());
            mRoleUpdate.setModifiedBy(user);
            mRoleUpdate.setModifiedOn(new Date());

            MRole mRoleSaved = this.mRoleRepository.save(mRoleUpdate);
            if (mRoleSaved.equals(mRoleUpdate)) {
                return new ResponseEntity<>(response("success", "Modified Success", mRoleSaved),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Modified Failed", mRoleSaved), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(response("failed", "Modified Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
	
	@PutMapping("delete/{id}/{user}")
    public ResponseEntity<Map<String, Object>> deleteMPaymentMethod(@PathVariable("id") Long id, @PathVariable("user") Long user) {
        MRole mRoleDelete = this.mRoleRepository.findById(id).orElse(null);
        if (mRoleDelete != null) {
            mRoleDelete.setIsDelete(true);
            mRoleDelete.setDeletedBy(user);
            mRoleDelete.setDeletedOn(new Date());

            MRole mRoleSaved = this.mRoleRepository.save(mRoleDelete);
            if (mRoleSaved.equals(mRoleDelete)) {
                return new ResponseEntity<>(response("success", "Deleted Success", mRoleSaved),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Deleted Failed", mRoleSaved), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(response("failed", "Deleted Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
}
