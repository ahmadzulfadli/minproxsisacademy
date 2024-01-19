package com.miniproject335b.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject335b.app.model.TCostumerWalletWithdraw;

@Repository
public interface TCostumerWalletWithdrawRepository extends JpaRepository<TCostumerWalletWithdraw, Long>{
    
}
