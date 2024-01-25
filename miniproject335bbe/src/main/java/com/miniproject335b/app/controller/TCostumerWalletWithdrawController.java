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
import com.miniproject335b.app.model.TCostumerWalletWithdraw;
import com.miniproject335b.app.model.TToken;
import com.miniproject335b.app.repository.MCustomerRepository;
import com.miniproject335b.app.repository.TCostumerWalletRepository;
import com.miniproject335b.app.repository.TCostumerWalletWithdrawRepository;
import com.miniproject335b.app.repository.TTokenRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/costumerwalletwithdraw/")
public class TCostumerWalletWithdrawController {

    @Autowired
    private TCostumerWalletWithdrawRepository tCostumerWalletWithdrawRepository;

    @Autowired
    private TCostumerWalletRepository tCostumerWalletRepository;

    @Autowired
    private TTokenRepository tTokenRepository;

    @Autowired
	private MCustomerRepository mCustomerRepository;

    // lessBalance-----------------------------------------------------------------------------------------------------------
    private void lessBalance(Long id, Double amount) {
        TCostumerWallet tCostumerWallet = this.tCostumerWalletRepository.findById(id).orElse(null);
        Double balance = tCostumerWallet.getBalance();
        Double balanceLess = balance - amount;
        tCostumerWallet.setBalance(balanceLess);
        tCostumerWallet.setModifiedBy(1L);
        tCostumerWallet.setModifiedOn(new Date());
        this.tCostumerWalletRepository.save(tCostumerWallet);
    }


    // Response-----------------------------------------------------------------------------------------------------------
    private Map<String, Object> response(String status, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", status);
        response.put("message", message);
        response.put("data", data);
        
        return response;
    }

    // Generate OTP--------------------------------------------------------------------------------------------------------
    private Integer generateOTP() {
        int randomOtp   =(int)(Math.random()*900000)+100000;
        return randomOtp;
    }

    // Save OTP To Token Table--------------------------------------------------------------------------------------------------------
    private String saveOtp(Long id, Integer otp) {
        try {
            Object users = this.mCustomerRepository.findMUserByIdCustomer(id);
    
            Long idUser = (Long) ((Object[]) users)[0];
            String email = (String) ((Object[]) users)[6];

            // System.out.println("id : "+idUser);
            // System.out.println("email : "+email);

            TToken token = new TToken();
            token.setCreatedBy(idUser);
            token.setCreatedOn(new Date());
            token.setEmail(email);
            token.setIsDelete(false);
            token.setIsExpired(false);
            token.setUsedFor("wallet withdraw");
            token.setUserId(idUser);
            token.setExpiredOn(new Date(System.currentTimeMillis() + 3600000));
            token.setToken(otp.toString());

            TToken tokenSaved = this.tTokenRepository.save(token);
            if (tokenSaved.equals(token)) {
            } else {
                return "Saved OTP Failed";
            }
            return "Saved OTP Success";
        } catch (Exception e) {
            return "Saved OTP Failed";
        }
    }
    

    // CREATE--------------------------------------------------------------------------------------------------------
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> createTCostumerWallet(@RequestParam("idCostumerWallet") Long idCostumerWallet,@RequestBody TCostumerWalletWithdraw tCostumerWalletWithdraw){
        try {
            TCostumerWallet tCostumerWallet = this.tCostumerWalletRepository.findById(idCostumerWallet).orElse(null);
            Double balance = tCostumerWallet.getBalance();
            if (tCostumerWalletWithdraw.getAmount() > balance) {
                return new ResponseEntity<>(response("failed", "Balance Not Enough", new ArrayList<>()), HttpStatus.OK);
            }

            if (tCostumerWalletWithdraw.getAmount() <= 0) {
                return new ResponseEntity<>(response("failed", "Amount Must More Than 0", new ArrayList<>()), HttpStatus.OK);
            }

            Integer otp = generateOTP();

            tCostumerWalletWithdraw.setBankName("Bank BCA");
            tCostumerWalletWithdraw.setAccountNumber("1234567890");
            tCostumerWalletWithdraw.setAccountName("Agus Aristianto");
            tCostumerWalletWithdraw.setOtp(otp);
            tCostumerWalletWithdraw.setCreatedBy(1L);
            tCostumerWalletWithdraw.setCreatedOn(new Date());
            tCostumerWalletWithdraw.setIsDelete(false);
            TCostumerWalletWithdraw tCostumerWalletSaved = this.tCostumerWalletWithdrawRepository.save(tCostumerWalletWithdraw);
            if (tCostumerWalletWithdraw.equals(tCostumerWalletSaved)) {
                String statusOtp = saveOtp(tCostumerWalletSaved.getCostumerId(), otp);
                System.out.println("statusOtp : "+statusOtp);
                
                lessBalance(idCostumerWallet, tCostumerWalletSaved.getAmount().doubleValue());
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
    public ResponseEntity<Map<String, Object>> getAllTCostumerWalletWhitdraw(){
        try {
            List<TCostumerWalletWithdraw> tCostumerWalletWithdraw = this.tCostumerWalletWithdrawRepository.findAll();
            if (tCostumerWalletWithdraw.isEmpty()) {
                return new ResponseEntity<>(response("success", "No Data", tCostumerWalletWithdraw), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response("success", "Success Fetch Data", tCostumerWalletWithdraw), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Read Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }

    @GetMapping("show/{id}")
    public ResponseEntity<Map<String,Object>> getTCostumerWalletWhitdraw(@PathVariable("id") Long id){
        try {
            TCostumerWalletWithdraw tCostumerWalletWithdrawUpdate = this.tCostumerWalletWithdrawRepository.findById(id).orElse(null);
            if (tCostumerWalletWithdrawUpdate == null) {
                return new ResponseEntity<>(response("success", "No Data", tCostumerWalletWithdrawUpdate),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(response("success", "Success Fetch Data", tCostumerWalletWithdrawUpdate), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // UPDATE--------------------------------------------------------------------------------------------------------
    @PutMapping("update")
    public ResponseEntity<Map<String, Object>> updateTCostumerWalletWhitdraw(@RequestBody TCostumerWalletWithdraw tCostumerWalletWithdraw){
        try {
            Long id = tCostumerWalletWithdraw.getId();
            TCostumerWalletWithdraw tCostumerWalletWithdrawUpdate = this.tCostumerWalletWithdrawRepository.findById(id).orElse(null);
            if (tCostumerWalletWithdrawUpdate != null) {
                tCostumerWalletWithdrawUpdate.setCostumerId(tCostumerWalletWithdraw.getCostumerId());
                tCostumerWalletWithdrawUpdate.setAmount(tCostumerWalletWithdraw.getAmount());
                tCostumerWalletWithdrawUpdate.setBankName(tCostumerWalletWithdraw.getBankName());
                tCostumerWalletWithdrawUpdate.setAccountNumber(tCostumerWalletWithdraw.getAccountNumber());
                tCostumerWalletWithdrawUpdate.setAccountName(tCostumerWalletWithdraw.getAccountName());
                tCostumerWalletWithdrawUpdate.setOtp(tCostumerWalletWithdraw.getOtp());
                tCostumerWalletWithdrawUpdate.setModifiedBy(1L);
                tCostumerWalletWithdrawUpdate.setModifiedOn(new Date());
                TCostumerWalletWithdraw tCostumerWalletWithdrawSaved = this.tCostumerWalletWithdrawRepository.save(tCostumerWalletWithdrawUpdate);
                if (tCostumerWalletWithdrawSaved.equals(tCostumerWalletWithdrawUpdate)) {
                    return new ResponseEntity<>(response("success", "Modified Success", tCostumerWalletWithdrawSaved), HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(response("failed", "Modified Failed", tCostumerWalletWithdrawSaved), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<>(response("failed", "Modified Failed", tCostumerWalletWithdrawUpdate), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Modified Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }

    // DELETE--------------------------------------------------------------------------------------------------------
    @PutMapping("delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteTCostumerWalletWhitdraw(@PathVariable("id") Long id){
        try {
            TCostumerWalletWithdraw tCostumerWalletWithdrawDelete = this.tCostumerWalletWithdrawRepository.findById(id).orElse(null);
            if (tCostumerWalletWithdrawDelete != null) {
                tCostumerWalletWithdrawDelete.setIsDelete(true);
                tCostumerWalletWithdrawDelete.setDeletedBy(1L);
                tCostumerWalletWithdrawDelete.setDeletedOn(new Date());
                TCostumerWalletWithdraw tCostumerWalletWithdrawSaved = this.tCostumerWalletWithdrawRepository.save(tCostumerWalletWithdrawDelete);
                if (tCostumerWalletWithdrawSaved.equals(tCostumerWalletWithdrawDelete)) {
                    return new ResponseEntity<>(response("success", "Delete Success", tCostumerWalletWithdrawSaved), HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>(response("failed", "Delete Failed", tCostumerWalletWithdrawSaved), HttpStatus.OK);
                }
            }else{
                return new ResponseEntity<>(response("failed", "Delete Failed", tCostumerWalletWithdrawDelete), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(response("failed", "Delete Failed", new ArrayList<>()), HttpStatus.OK);
        }
    }
    
}
