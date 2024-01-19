package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.miniproject335b.app.model.TCustomerCustomNominal;

@Repository
public interface TCustomerCustomNominalRepository extends JpaRepository<TCustomerCustomNominal, Long>{
    
    @Query(value = "SELECT * FROM t_customer_custom_nominal WHERE is_delete = false AND customer_id = ?1", nativeQuery = true)
    List<TCustomerCustomNominal> findByCostumerId(Long customerId);
}
