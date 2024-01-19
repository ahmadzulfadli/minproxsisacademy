package com.miniproject335b.app.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MPaymentMethod;


public interface MPaymentMethodRepository extends JpaRepository<MPaymentMethod, Long> {
    
    @Query(value = "SELECT * FROM m_payment_method WHERE is_delete=?1 ORDER BY id ASC ", nativeQuery = true)
    List<MPaymentMethod> findByIsDelete(Boolean isDelete);

    @Query(value = "SELECT * FROM m_payment_method WHERE is_delete=false AND name ilike concat('%',?1,'%')", nativeQuery = true)
    Page<MPaymentMethod> findBySearch(Pageable pageable, String name);

    @Query(value = "SELECT * FROM m_payment_method WHERE is_delete=false", nativeQuery = true)
    Page<MPaymentMethod> getMPaymentMethodByPageable(Pageable pageable);


}
