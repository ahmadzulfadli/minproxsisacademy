package com.miniproject335b.app.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TCostumerWallet;

public interface TCostumerWalletRepository extends JpaRepository<TCostumerWallet, Long>{

    @Query(value = "SELECT * FROM t_costumer_wallet WHERE is_delete = ?1 ORDER BY id ASC", nativeQuery = true)
    List<TCostumerWallet> findByIsDelete(Boolean isDelete);
}
