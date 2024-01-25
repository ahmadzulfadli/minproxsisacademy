package com.miniproject335b.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MWalletDefaultNominal;

public interface MWalletDefaultNominalRepository extends JpaRepository<MWalletDefaultNominal, Long>{

    @Query(value = "SELECT * FROM m_wallet_default_nominal WHERE is_delete = false ORDER BY id ASC", nativeQuery = true)
    List<MWalletDefaultNominal> findByIsDelete();
    
    @Query(value = "SELECT * FROM m_wallet_default_nominal WHERE is_delete = false AND nominal <= ?1 ORDER BY id ASC", nativeQuery = true)
    List<MWalletDefaultNominal> findByLimitBalance(Double balance);
}
