package com.miniproject335b.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.miniproject335b.app.model.MCustomerMember;
import com.miniproject335b.app.repository.MCustomerMemberRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class MCustomerMemberController {
	
	private Integer pageSize = 3;
    private Integer currentPage = 0;
    private Long totalItems = 0L;
    private Integer totalPages = 0;
	
	@Autowired
	MCustomerMemberRepository mCustomerMemberRepository;
	
	//Response
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
	
	//Read	 (Get)
	@GetMapping("customermember")
	public ResponseEntity<List<MCustomerMember>> getAllMCustomerRelation(){
		try {
			List<MCustomerMember> listMCustomerMember = this.mCustomerMemberRepository.findByIsDelete(false);
			return new ResponseEntity<>(listMCustomerMember, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	//Paging
	@GetMapping("customermember/paging")
	public ResponseEntity<Map<String, Object>> getMCustomerPaging(
			@RequestParam(defaultValue = "5") int per_page,
			@RequestParam(defaultValue = "0") int page){
		try {
			List<MCustomerMember> mcustomermember = new ArrayList();
			Pageable pagingSort = PageRequest.of(page, per_page);
			Page<MCustomerMember> pages;
			pages = this.mCustomerMemberRepository.findByIsDelete(pagingSort, false);
			mcustomermember = pages.getContent();
			
			Map<String, Object> response = new HashMap();
			response.put("page", pages.getNumber()+1);
			response.put("data", mcustomermember);
			response.put("per_pages", per_page);
			response.put("total", pages.getTotalElements());
			response.put("total_pages", pages.getTotalPages());
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("customermember/sort/test")
    public ResponseEntity<Map<String, Object>> sortBy(
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int per_page,
            @RequestParam("sort") String sort) {
        try {
            List<MCustomerMember> mCustomerMember = new ArrayList();
            Pageable pagingSort = PageRequest.of(page, per_page);
            Page<MCustomerMember> pages;
            pages = this.mCustomerMemberRepository.findByIsDelete(pagingSort, sort, false);
            mCustomerMember = pages.getContent();

            Map<String, Object> response = new HashMap();
			response.put("page", pages.getNumber()+1);
			response.put("data", mCustomerMember);
			response.put("per_pages", per_page);
			response.put("total", pages.getTotalElements());
			response.put("total_pages", pages.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

	@GetMapping("customermember/search")
    public ResponseEntity<List<Object>> getCustomerMemberByName(@RequestParam("keyword") String keyword) {
        List<Object> listCustomerMember = this.mCustomerMemberRepository.searchByName(keyword);
        if (listCustomerMember != null) {
            return new ResponseEntity<List<Object>>(listCustomerMember, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	@GetMapping("customermember/sort")
    public ResponseEntity<List<MCustomerMember>> soryBy( 
    		@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int per_page,
            @RequestParam("sort") String sort) {
        try {
            List<MCustomerMember> mCustomerMember;
            Pageable pagingSort = PageRequest.of(page, per_page);

            Page<MCustomerMember> pages;
            pages = this.mCustomerMemberRepository.findByIsDelete(pagingSort, sort);

            mCustomerMember = pages.getContent();
            System.out.println(sort);

            return new ResponseEntity<>(mCustomerMember, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//	// SortByName
//		@GetMapping("customermember/sort")
//		public ResponseEntity<List<MCustomerMember>> sortByName(@RequestParam(defaultValue = "0") Integer pageNumber,
//				@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "name") String sort) {
//			Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sort));
//
//			System.out.println(sort);
//
//			Page<MCustomerMember> page = this.mCustomerMemberRepository.findByIsDelete(pageable);
//			List<MCustomerMember> mCustomers = page.getContent();
//
//			return new ResponseEntity<>(mCustomers, HttpStatus.OK);
//
//		}
//	@GetMapping("customermember/search")
//    public ResponseEntity<Map<String, Object>> getMCourierSearchPageable(
//            @RequestParam() String name,
//            @RequestParam(defaultValue = "0") Integer pageNumber,
//            @RequestParam(defaultValue = "3") Integer pageSize,
//            @RequestParam(defaultValue = "asc") String order) {
//
//        try {
//            Pageable pageable = PageRequest.of(pageNumber, pageSize); 
//            
//            //if (order.equals("desc")) {
//            //    pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name").descending());
//            //}
//
//            Page<MCustomerMember> page = this.mCustomerMemberRepository.findBySearch(pageable, name);	
//            List<MCustomerMember> mPaymentMethod = page.getContent();
//            if (mPaymentMethod.isEmpty()) {
//                return new ResponseEntity<>(response("failed", "Failed Fetch Data", mPaymentMethod), HttpStatus.OK);
//            } else {
//                this.pageSize = page.getSize();
//                this.currentPage = page.getNumber();
//                this.totalItems = page.getTotalElements();
//                this.totalPages = page.getTotalPages();
//
//                return new ResponseEntity<>(response("success", "Success Fetch Data", mPaymentMethod), HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
//        }
//    }
	@GetMapping("customermember/{id}")
	public ResponseEntity<Optional<MCustomerMember>> getCustomerMemberById(@PathVariable("id") Long id) {
		try {
			Optional<MCustomerMember> mCustomerMemberData = this.mCustomerMemberRepository.findByIdFalse(id, false);
			return new ResponseEntity<Optional<MCustomerMember>>(mCustomerMemberData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Optional<MCustomerMember>>(HttpStatus.NO_CONTENT);
		}
	}
	
	//MultiSelect|MultiDelete
	@GetMapping("customermember/multiselect")
	public ResponseEntity<List<MCustomerMember>> multiSelect(@RequestParam("ids") List<Integer> ids) {
		try {
			List<MCustomerMember> listData = this.mCustomerMemberRepository.findByMultiSelect(ids);
			return new ResponseEntity<List<MCustomerMember>>(listData, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// delete
	@PutMapping("delete")
	public ResponseEntity<Object> multiDelete(@RequestParam("listId") String listId){
		try {
			String[] idData = listId.trim().split(",");
			for (int i = 0; i < idData.length; i++) {
				long id = Long.parseLong(idData[i]);
				MCustomerMember mCustomerMemberDelete = this.mCustomerMemberRepository.findById(id).orElse(null);
				if (mCustomerMemberDelete != null) {
					mCustomerMemberDelete.setIsDelete(true);
					mCustomerMemberDelete.setDeletedBy(1L);
					mCustomerMemberDelete.setDeletedOn(new Date());
					this.mCustomerMemberRepository.save(mCustomerMemberDelete);
				}
			}
			return new ResponseEntity<>("Delete Data Success", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	//Create (Post)
	@PostMapping("customermember/add")
	public ResponseEntity<Object> getCustomer(@RequestBody MCustomerMember mcustomermember){
		mcustomermember.setCreatedBy((long)1);
		mcustomermember.setCreatedOn(new Date());
		mcustomermember.setIsDelete(false);
		
		MCustomerMember mCustomerMemberData = this.mCustomerMemberRepository.save(mcustomermember);
		if(mCustomerMemberData.equals(mcustomermember)) {
			return new ResponseEntity<>("Save Data Success", HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}
	//Update (Put)
	@PutMapping("customermember/edit")
	public ResponseEntity<Object> editCustomerMember(@RequestBody MCustomerMember mcustomerMember) {
		Long id = mcustomerMember.getId();
		Optional<MCustomerMember> customerMemberData = this.mCustomerMemberRepository.findByIdFalse(id, false);

		if (customerMemberData.isPresent()) {
			mcustomerMember.setId(id);
			mcustomerMember.setModifiedBy((long) 1);
			mcustomerMember.setModifiedOn(new Date());
			mcustomerMember.setCreatedBy(customerMemberData.get().getCreatedBy());
			mcustomerMember.setCreatedOn(customerMemberData.get().getCreatedOn());
			mcustomerMember.setIsDelete(customerMemberData.get().getIsDelete());
			mcustomerMember.setCustomerId(customerMemberData.get().getCustomerId());
			this.mCustomerMemberRepository.save(mcustomerMember);
			return new ResponseEntity<Object>("Update Success", HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}

	}
	//Delete (Put)
	@PutMapping("customermember/delete/{id}")
	public ResponseEntity<Object> deleteMCustomerMember(@PathVariable("id") Long id){
		try {
			Optional<MCustomerMember> mCustomerMemberDelete = this.mCustomerMemberRepository.findById(id);
			if(mCustomerMemberDelete.isPresent()) {
				MCustomerMember mCustomerMemberData = mCustomerMemberDelete.get();
				mCustomerMemberData.setIsDelete(true);
				mCustomerMemberData.setDeletedBy((long) 1);
				mCustomerMemberData.setDeletedOn(new Date());
				
				this.mCustomerMemberRepository.save(mCustomerMemberData);	
				return new ResponseEntity<>("Delete Data Success", HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Delete Success", HttpStatus.BAD_REQUEST);

			}
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
}
