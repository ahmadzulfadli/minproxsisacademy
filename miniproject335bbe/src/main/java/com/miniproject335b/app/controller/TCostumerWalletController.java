package com.miniproject335b.app.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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


import com.miniproject335b.app.model.TCostumerWallet;
import com.miniproject335b.app.repository.TCostumerWalletRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/costumerwallet/")
public class TCostumerWalletController {
    
    @Autowired
    private TCostumerWalletRepository tCostumerWalletRepository;

    // Response-----------------------------------------------------------------------------------------------------------
    private Map<String, Object> response(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        
        return response;
    }

    // Cek Pin-----------------------------------------------------------------------------------------------------------
    @PostMapping("verify")
    public ResponseEntity<Map<String, Object>> cekPin(@RequestParam("nominal") Double nominal,@RequestBody TCostumerWallet tCostumerWallet){
        try {
            Long id = tCostumerWallet.getId();
            TCostumerWallet tCostumerWalletCek = this.tCostumerWalletRepository.findById(id).orElse(null);
            
            if (nominal > tCostumerWalletCek.getBalance()) {
                return new ResponseEntity<>(response("failed", "Balance Not Enough", new ArrayList<>()), HttpStatus.OK);
            }

            if (tCostumerWalletCek == null) {
                return new ResponseEntity<>(response("failed", "No Data", tCostumerWalletCek), HttpStatus.OK);
            } else {
                String pin = tCostumerWallet.getPin();
                String pinCek = tCostumerWalletCek.getPin();
                if (pin.equals(pinCek)) {
                    return new ResponseEntity<>(response("success", "Success To Verify PIN", new ArrayList<>()), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(response("failed", "Sorry Your PIN Is Wrong", new ArrayList<>()), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // CREATE--------------------------------------------------------------------------------------------------------
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> createTCostumerWallet(@RequestBody TCostumerWallet tCostumerWallet){
        try {
            tCostumerWallet.setCreatedBy(1L);
            tCostumerWallet.setCreatedOn(new Date());
            tCostumerWallet.setIsDelete(false);
            TCostumerWallet tCostumerWalletSaved = this.tCostumerWalletRepository.save(tCostumerWallet);
            if (tCostumerWallet.equals(tCostumerWalletSaved)) {
                return new ResponseEntity<>(response("success", "Saved Success", tCostumerWalletSaved), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Saved Failed", tCostumerWalletSaved), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Saved Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // READ--------------------------------------------------------------------------------------------------------
    @GetMapping("show")
    public ResponseEntity<Map<String, Object>>  getAllTCostumerWallet(){
        try {
            List<TCostumerWallet> tCostumerWalletList = this.tCostumerWalletRepository.findByIsDelete(false);
            if (tCostumerWalletList.isEmpty()) {
                return new ResponseEntity<>(response("success", "No Data", tCostumerWalletList), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", tCostumerWalletList), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", e.toString(), new ArrayList<>()), HttpStatus.OK);
        }
    }
    @GetMapping("show/{id}")
    public ResponseEntity<Map<String, Object>> getTCostumerWalletById(@PathVariable("id") Long id){
        try {
            TCostumerWallet tCostumerWallet = this.tCostumerWalletRepository.findById(id).orElse(null);
            if (tCostumerWallet == null) {
                return new ResponseEntity<>(response("success", "No Data", tCostumerWallet), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("success", "Success Fetch Data", tCostumerWallet), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // UPDATE--------------------------------------------------------------------------------------------------------
    @PutMapping("update")
    public ResponseEntity<Map<String, Object>> updateTCostumerWallet(@RequestBody TCostumerWallet tCostumerWallet){
        Long id = tCostumerWallet.getId();
        try {
            TCostumerWallet tCostumerWalletUpdate = this.tCostumerWalletRepository.findById(id).orElse(null);
            if (tCostumerWalletUpdate == null) {
                return new ResponseEntity<>(response("success", "No Data", tCostumerWalletUpdate), HttpStatus.OK);
            } else {
                tCostumerWalletUpdate.setPin(tCostumerWallet.getPin());
                tCostumerWalletUpdate.setBalance(tCostumerWallet.getBalance());
                tCostumerWalletUpdate.setBarcode(tCostumerWallet.getBarcode());
                tCostumerWalletUpdate.setPoints(tCostumerWallet.getPoints());
                tCostumerWalletUpdate.setModifiedBy(1L);
                tCostumerWalletUpdate.setModifiedOn(new Date());
                TCostumerWallet tCostumerWalletSaved = this.tCostumerWalletRepository.save(tCostumerWalletUpdate);
                if (tCostumerWalletSaved.equals(tCostumerWalletUpdate)) {
                    return new ResponseEntity<>(response("success", "Update Success", tCostumerWalletSaved), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(response("failed", "Update Failed", tCostumerWalletSaved), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Update Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // DELETE--------------------------------------------------------------------------------------------------------
    @PutMapping("delete")
    public ResponseEntity<Map<String, Object>> deleteTCostumerWallet(@RequestBody TCostumerWallet tCostumerWallet){
        Long id = tCostumerWallet.getId();
        try {
            TCostumerWallet tCostumerWalletDelete = this.tCostumerWalletRepository.findById(id).orElse(null);
            if (tCostumerWalletDelete == null) {
                return new ResponseEntity<>(response("success", "No Data", tCostumerWalletDelete), HttpStatus.OK);
            } else {
                tCostumerWalletDelete.setIsDelete(true);
                tCostumerWalletDelete.setDeletedBy(1L);
                tCostumerWalletDelete.setDeletedOn(new Date());
                TCostumerWallet tCostumerWalletSaved = this.tCostumerWalletRepository.save(tCostumerWalletDelete);
                if (tCostumerWalletSaved.equals(tCostumerWalletDelete)) {
                    return new ResponseEntity<>(response("success", "Delete Success", tCostumerWalletSaved), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(response("failed", "Delete Failed", tCostumerWalletSaved), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Delete Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
}
