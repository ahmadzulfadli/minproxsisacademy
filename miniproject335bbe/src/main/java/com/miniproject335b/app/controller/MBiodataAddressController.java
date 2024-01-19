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

import com.miniproject335b.app.model.MBiodataAddress;
import com.miniproject335b.app.repository.MBiodataAddressRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/")
public class MBiodataAddressController {

	private Integer pageSize = 3;
	private Integer currentPage = 0;
	private Long totalItems = 0L;
	private Integer totalPages = 0;

	@Autowired
	private MBiodataAddressRepository mBiodataAddressRepository;

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

	// Read (Get)
	@GetMapping("biodataaddress")
	public ResponseEntity<Map<String, Object>> getAllMBiodataAddress() {

		try {
			List<MBiodataAddress> listMBiodataAddress = this.mBiodataAddressRepository.findByIsDelete(false);
			if (listMBiodataAddress.isEmpty()) {
				return new ResponseEntity<>(response("success", "No Data", listMBiodataAddress), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(response("success", "Success Fetch Data", listMBiodataAddress),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
		}

	}

	@GetMapping("biodataaddress/search")
    public ResponseEntity<Map<String, Object>> getMBiodataAddressSearch(
			@RequestParam(defaultValue = "") String address,
			@RequestParam(defaultValue = "") String recipient,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "label") String sort) {

        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("label").ascending()); 
            
            if (sort.equals("recipient")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("recipient").ascending());
            }

			if(sort.equals("address")) {
				pageable = PageRequest.of(pageNumber, pageSize, Sort.by("address").ascending());
			}

            Page<MBiodataAddress> page = this.mBiodataAddressRepository.getByAddressAndRecipient(pageable, address, recipient);
            List<MBiodataAddress> mBiodataAddresss = page.getContent();
            if (mBiodataAddresss.isEmpty()) {
                return new ResponseEntity<>(response("failed", "Failed Fetch Data", mBiodataAddresss), HttpStatus.OK);
            } else {
                this.pageSize = page.getSize();
                this.currentPage = page.getNumber();
                this.totalItems = page.getTotalElements();
                this.totalPages = page.getTotalPages();

                return new ResponseEntity<>(response("success", "Success Fetch Data", mBiodataAddresss), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    @GetMapping("biodataaddress/pageable")
    public ResponseEntity<Map<String, Object>> getMBiodataAddressPageable(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "3") Integer pageSize,
            @RequestParam(defaultValue = "label") String sort) {

        try {
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("label").ascending()); 
            
            if (sort.equals("recipient")) {
                pageable = PageRequest.of(pageNumber, pageSize, Sort.by("recipient").ascending());
            }
			
			if(sort.equals("address")) {
				pageable = PageRequest.of(pageNumber, pageSize, Sort.by("address").ascending());
			}

            Page<MBiodataAddress> page = this.mBiodataAddressRepository.getMBiodataAddressByPageable(pageable);
            List<MBiodataAddress> mBiodataAddresss = page.getContent();
            if (mBiodataAddresss.isEmpty()) {
                return new ResponseEntity<>(response("failed", "Failed Fetch Data", mBiodataAddresss), HttpStatus.OK);
            } else {
                this.pageSize = page.getSize();
                this.currentPage = page.getNumber();
                this.totalItems = page.getTotalElements();
                this.totalPages = page.getTotalPages();

                return new ResponseEntity<>(response("success", "Success Fetch Data", mBiodataAddresss), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
	
	@GetMapping("biodataaddress/byid/{id}")
	public ResponseEntity<Map<String, Object>> getMBiodataAddressById(@PathVariable("id") Long id) {
        try {
            MBiodataAddress mBiodataAddress = this.mBiodataAddressRepository.findById(id).orElse(null);
            if (mBiodataAddress == null) {
                return new ResponseEntity<>(response("success", "No Data", mBiodataAddress), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", mBiodataAddress), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
	}
	
//	@GetMapping("biodataaddress/search")
//	public ResponseEntity<Map<String, Object>> searchMBiodataAddressPaging(
//			@RequestParam() String name,
//			@RequestParam(defaultValue ="0") Integer pageNumber,
//			@RequestParam(defaultValue ="3") Integer pageSize,
//			@RequestParam(defaultValue ="asc") String order){
//		try {
//			Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by("address").ascending());
//			if(order.equals(("desc"))) {
//				pageable = PageRequest.of(pageNumber,pageSize, Sort.by("address").descending());
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//	}
	
	
	// Create (Post)
	@PostMapping("biodataaddress/create")
	public ResponseEntity<Map<String, Object>> createMBiodataAddress(@RequestBody MBiodataAddress mBiodataAddress) {
		try {

			if (mBiodataAddress.getLabel() == "" || mBiodataAddress.getLabel() == null) {
				return new ResponseEntity<>(response("failed", "Label Cannot Be Null", new ArrayList<>()),
						HttpStatus.OK);
			}
			if (mBiodataAddress.getRecipient() == "" || mBiodataAddress.getRecipient() == null) {
				return new ResponseEntity<>(response("failed", "Recipient Cannot Be Null", new ArrayList<>()),
						HttpStatus.OK);
			}
			if (mBiodataAddress.getRecipientPhoneNumber() == "" || mBiodataAddress.getRecipientPhoneNumber() == null) {
				return new ResponseEntity<>(
						response("failed", "RecipientPhoneNumber Cannot Be Null", new ArrayList<>()), HttpStatus.OK);
			}
			if (mBiodataAddress.getAddress() == "" || mBiodataAddress.getAddress() == null) {
				return new ResponseEntity<>(response("failed", "Address Cannot Be Null", new ArrayList<>()),
						HttpStatus.OK);
			}
			if (mBiodataAddress.getLocationId() == null) {
				return new ResponseEntity<>(response("failed", "LocationId Cannot Be Null", new ArrayList<>()),
						HttpStatus.OK);
			}

			List<MBiodataAddress> listMBiodataAddress = this.mBiodataAddressRepository.findByIsDelete(false);
			for (MBiodataAddress biodataAddress : listMBiodataAddress) {
				if (biodataAddress.getRecipient().equals(mBiodataAddress.getRecipient())) {
					return new ResponseEntity<>(response("failed", "Recipient Already Exist", new ArrayList<>()),
							HttpStatus.OK);
				}
			}
			mBiodataAddress.setCreatedBy(1L);
			mBiodataAddress.setCreatedOn(new Date());
			mBiodataAddress.setIsDelete(false);
			MBiodataAddress mBiodataAddressSaved = this.mBiodataAddressRepository.save(mBiodataAddress);

			if (mBiodataAddress.equals(mBiodataAddressSaved)) {
				return new ResponseEntity<>(response("success", "Save Data Success", mBiodataAddressSaved),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(response("failed", "Save Data Failed", mBiodataAddressSaved), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(response("failed", "Save Data Failed", new ArrayList<>()), HttpStatus.OK);
		}

	}

	// Update (Put)
	@PutMapping("biodataaddress/edit/{id}")
	public ResponseEntity<Map<String, Object>> createMBiodataAddress(@RequestBody MBiodataAddress mBiodataAddress,
			@PathVariable("id") Long Id) {
		if (mBiodataAddress.getLabel() == "" || mBiodataAddress.getLabel() == null) {
			return new ResponseEntity<>(response("failed", "Label Cannot Be Null", new ArrayList<>()), HttpStatus.OK);
		}
		if (mBiodataAddress.getRecipient() == "" || mBiodataAddress.getRecipient() == null) {
			return new ResponseEntity<>(response("failed", "Recipient Cannot Be Null", new ArrayList<>()),
					HttpStatus.OK);
		}
		if (mBiodataAddress.getRecipientPhoneNumber() == "" || mBiodataAddress.getRecipientPhoneNumber() == null) {
			return new ResponseEntity<>(response("failed", "RecipientPhoneNumber Cannot Be Null", new ArrayList<>()),
					HttpStatus.OK);
		}
		if (mBiodataAddress.getAddress() == "" || mBiodataAddress.getAddress() == null) {
			return new ResponseEntity<>(response("failed", "Address Cannot Be Null", new ArrayList<>()), HttpStatus.OK);
		}
		if (mBiodataAddress.getLocationId() == null) {
			return new ResponseEntity<>(response("failed", "LocationId Cannot Be Null", new ArrayList<>()),
					HttpStatus.OK);
		}

		List<MBiodataAddress> listMBiodataAddress = this.mBiodataAddressRepository.findByIsDelete(false);
		for (MBiodataAddress biodataAddress : listMBiodataAddress) {
			if (biodataAddress.getRecipient().equals(mBiodataAddress.getRecipient())) {
				return new ResponseEntity<>(response("failed", "Recipient Already Exist", new ArrayList<>()),
						HttpStatus.OK);
			}
		}

		MBiodataAddress mBiodataAddressUpdate = this.mBiodataAddressRepository.findById(Id).orElse(null);
		if (mBiodataAddressUpdate != null) {
			mBiodataAddressUpdate.setBiodataId(mBiodataAddress.getBiodataId());
			mBiodataAddressUpdate.setLabel(mBiodataAddress.getLabel());
			mBiodataAddressUpdate.setRecipient(mBiodataAddress.getRecipient());
			mBiodataAddressUpdate.setRecipientPhoneNumber(mBiodataAddress.getRecipientPhoneNumber());
			mBiodataAddressUpdate.setLocationId(mBiodataAddress.getLocationId());
			mBiodataAddressUpdate.setPostalCode(mBiodataAddress.getPostalCode());
			mBiodataAddressUpdate.setAddress(mBiodataAddress.getAddress());
			mBiodataAddressUpdate.setModifiedBy(1L);
			mBiodataAddressUpdate.setModifiedOn(new Date());

			MBiodataAddress mBiodataAddressSaved = this.mBiodataAddressRepository.save(mBiodataAddressUpdate);
			if (mBiodataAddressSaved.equals(mBiodataAddressUpdate)) {
				return new ResponseEntity<>(response("success", "Update Data Success", mBiodataAddressSaved),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(response("failed", "Update Data Failed", mBiodataAddressSaved), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(response("failed", "Update Data Failed", new ArrayList<>()), HttpStatus.OK);

		}
	}
	// Delete (Put)
	@PutMapping("biodataaddress/delete/{id}")
	public ResponseEntity<Map<String, Object>> deleteMBiodataAddress(@PathVariable("id") Long id) {
        MBiodataAddress mBiodataAddressDelete = this.mBiodataAddressRepository.findById(id).orElse(null);
        if (mBiodataAddressDelete != null) {
        	mBiodataAddressDelete.setIsDelete(true);
        	mBiodataAddressDelete.setDeletedBy(1L);
        	mBiodataAddressDelete.setDeletedOn(new Date());

            MBiodataAddress mBiodataAddressSaved = this.mBiodataAddressRepository.save(mBiodataAddressDelete);
            if (mBiodataAddressSaved.equals(mBiodataAddressDelete)) {
                return new ResponseEntity<>(response("success", "Delete Data Success", mBiodataAddressSaved),
                        HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Delete Data Failed", mBiodataAddressSaved), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(response("failed", "Delete Data Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
}
