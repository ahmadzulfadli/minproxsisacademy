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

import com.miniproject335b.app.model.MWalletDefaultNominal;
import com.miniproject335b.app.repository.MWalletDefaultNominalRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/walletdefaultnominal/")
public class MWalletDefaultNominalController {
    
    @Autowired
    private MWalletDefaultNominalRepository mWalletDefaultNominalRepository;

    // Response-------------------------------------------------------------------------------------
    private Map<String, Object> response(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        return response;
    }
    // CREATE---------------------------------------------------------------------------------------
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> createMWalletDefaultNominal(@RequestBody MWalletDefaultNominal mWalletDefaultNominal){
        try {
            mWalletDefaultNominal.setCreatedBy(1L);
            mWalletDefaultNominal.setCreatedOn(new Date());
            mWalletDefaultNominal.setIsDelete(false);
            MWalletDefaultNominal mDefaultNominalSaved = this.mWalletDefaultNominalRepository.save(mWalletDefaultNominal);
            if (mWalletDefaultNominal.equals(mDefaultNominalSaved)) {
                return new ResponseEntity<>(response("success", "Saved Success", mDefaultNominalSaved), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(response("failed", "Saved Failed", mDefaultNominalSaved), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Saved Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
    // READ-----------------------------------------------------------------------------------------
    @GetMapping("show")
    public ResponseEntity<Map<String, Object>> getAllMWalletDefaultNominal(){
        try {
            List<MWalletDefaultNominal> mWalletDefaultNominal = this.mWalletDefaultNominalRepository.findByIsDelete(false);
            if (mWalletDefaultNominal.isEmpty()) {
                return new ResponseEntity<>(response("success", "No Data", mWalletDefaultNominal), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response("success", "Success Fetch Data", mWalletDefaultNominal), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
    @GetMapping("show/{id}")
    public ResponseEntity<Map<String, Object>> getMWalletDefaultNominalById(@PathVariable("id") Long id){
        try {
            MWalletDefaultNominal mWalletDefaultNominal = this.mWalletDefaultNominalRepository.findById(id).orElse(null);
            if (mWalletDefaultNominal == null) {
                return new ResponseEntity<>(response("success", "No Data", mWalletDefaultNominal), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response("success", "Success Fetch Data", mWalletDefaultNominal), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }
    // UPDATE---------------------------------------------------------------------------------------
    @PutMapping("update")
    public ResponseEntity<Map<String, Object>> updateMWalletDefaultNominal(@RequestBody MWalletDefaultNominal mWalletDefaultNominal){
        Long id = mWalletDefaultNominal.getId();
        MWalletDefaultNominal mWalletDefaultNominalUpdate = this.mWalletDefaultNominalRepository.findById(id).orElse(null);
        if (mWalletDefaultNominalUpdate != null) {
            mWalletDefaultNominalUpdate.setNominal(mWalletDefaultNominal.getNominal());
            mWalletDefaultNominalUpdate.setModifiedBy(1L);
            mWalletDefaultNominalUpdate.setModifiedOn(new Date());

            MWalletDefaultNominal mWalletDefaultNominalSaved = this.mWalletDefaultNominalRepository.save(mWalletDefaultNominalUpdate);
            if (mWalletDefaultNominalUpdate.equals(mWalletDefaultNominalSaved)) {
                return new ResponseEntity<>(response("success", "Modified Success", mWalletDefaultNominalSaved), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("failed", "Modified Failed", mWalletDefaultNominalSaved), HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(response("failed", "Modified Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
    // DELETE---------------------------------------------------------------------------------------
    @PutMapping("delete")
    public ResponseEntity<Map<String, Object>> deleteMWalletDefaultNominal(@RequestBody MWalletDefaultNominal mWalletDefaultNominal){
        Long id = mWalletDefaultNominal.getId();
        MWalletDefaultNominal mWalletDefaultNominalDelete= this.mWalletDefaultNominalRepository.findById(id).orElse(null);
        if (mWalletDefaultNominalDelete != null) {
            mWalletDefaultNominalDelete.setIsDelete(true);
            mWalletDefaultNominalDelete.setDeletedBy(1L);
            mWalletDefaultNominalDelete.setDeletedOn(new Date());

            MWalletDefaultNominal mWalletDefaultNominalSaved = this.mWalletDefaultNominalRepository.save(mWalletDefaultNominalDelete);
            if (mWalletDefaultNominalDelete.equals(mWalletDefaultNominalSaved)) {
                return new ResponseEntity<>(response("success", "Delete Success", mWalletDefaultNominalSaved), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(response("failed", "Delete Failed", mWalletDefaultNominalSaved), HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(response("failed", "Delete Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
}
