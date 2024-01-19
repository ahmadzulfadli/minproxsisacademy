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
import com.miniproject335b.app.model.TCustomerCustomNominal;
import com.miniproject335b.app.repository.MWalletDefaultNominalRepository;
import com.miniproject335b.app.repository.TCustomerCustomNominalRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/customernominal/")
public class TCustomerCustomNominalController {

    @Autowired
    private TCustomerCustomNominalRepository tCustomerCustomNominalRepository;

    @Autowired
    private MWalletDefaultNominalRepository mWalletDefaultNominalRepository;

    // Response-----------------------------------------------------------------------------------------------------------
    private Map<String, Object> response(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        
        return response;
    }

    // CREATE-----------------------------------------------------------------------------------------------------------
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> createTCustomerCustomNominal(@RequestBody TCustomerCustomNominal tCustomerCustomNominal){
        try {
            if (!(tCustomerCustomNominal.getNominal() > 0) || tCustomerCustomNominal.getCustomerId() == null) {
                return new ResponseEntity<>(response("failed", "Nominal Cannot Be Empty", tCustomerCustomNominal), HttpStatus.OK);
            }

            List<TCustomerCustomNominal> listTCustomerCustomNominal = this.tCustomerCustomNominalRepository.findByCostumerId(tCustomerCustomNominal.getCustomerId());
            for (TCustomerCustomNominal tCustomerCustomNominalData : listTCustomerCustomNominal) {
                if (tCustomerCustomNominalData.getNominal().equals(tCustomerCustomNominal.getNominal())) {
                    return new ResponseEntity<>(response("success", "Nominal Is Exists", tCustomerCustomNominal), HttpStatus.OK);
                }
            }

            List<MWalletDefaultNominal> listMWalletDefaultNominal = this.mWalletDefaultNominalRepository.findByIsDelete(false);
            for (MWalletDefaultNominal mWalletDefaultNominal : listMWalletDefaultNominal) {
                if (mWalletDefaultNominal.getNominal().equals(tCustomerCustomNominal.getNominal())) {
                    return new ResponseEntity<>(response("success", "Nominal Is Exists", tCustomerCustomNominal), HttpStatus.OK);
                }
            }

            tCustomerCustomNominal.setCreatedBy(1l);
            tCustomerCustomNominal.setCreatedOn(new Date());
            tCustomerCustomNominal.setIsDelete(false);
            TCustomerCustomNominal tCustomerCustomNominalSave = this.tCustomerCustomNominalRepository.save(tCustomerCustomNominal);
            if (tCustomerCustomNominal.equals(tCustomerCustomNominalSave)) {
                return new ResponseEntity<>(response("success", "Saved Success", tCustomerCustomNominalSave), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response("failed", "Saved Failed", tCustomerCustomNominalSave), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Saved Failed", new HashMap<>()), HttpStatus.OK);
        }
    }

    // READ-----------------------------------------------------------------------------------------
    @GetMapping("show/{id}")
    public ResponseEntity<Map<String, Object>> getDataByCustomerId(@PathVariable("id") Long id){
        try {
            List<TCustomerCustomNominal> tCustomerCustomNominal = this.tCustomerCustomNominalRepository.findByCostumerId(id);
            if (tCustomerCustomNominal.isEmpty()) {
                return new ResponseEntity<>(response("success", "No Data", tCustomerCustomNominal), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response("success", "Success Fetch Data", tCustomerCustomNominal), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // UPDATE---------------------------------------------------------------------------------------
    
    // DELETE---------------------------------------------------------------------------------------
    @PutMapping("delete")
    public ResponseEntity<Map<String, Object>> deleteTcutomerCustomNominal(@RequestBody TCustomerCustomNominal tCustomerCustomNominal){
        try {
            Long id = tCustomerCustomNominal.getId();
            TCustomerCustomNominal tCustomerCustomNominalUpdate = this.tCustomerCustomNominalRepository.findById(id).orElse(null);
            if (tCustomerCustomNominalUpdate != null) {
                tCustomerCustomNominalUpdate.setIsDelete(true);
                tCustomerCustomNominalUpdate.setDeletedBy(1L);
                tCustomerCustomNominalUpdate.setDeletedOn(new Date());

                TCustomerCustomNominal tCustomerCustomNominalSave = this.tCustomerCustomNominalRepository.save(tCustomerCustomNominalUpdate);
                if (tCustomerCustomNominalUpdate.equals(tCustomerCustomNominalSave)) {
                    return new ResponseEntity<>(response("success", "Deleted Success", tCustomerCustomNominalUpdate), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(response("failed", "Deleted Failed", tCustomerCustomNominalUpdate),HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<>(response("failed", "No Data", tCustomerCustomNominalUpdate), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Deleted Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
    
}
