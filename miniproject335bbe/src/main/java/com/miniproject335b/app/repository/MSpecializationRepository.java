package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MPaymentMethod;
import com.miniproject335b.app.model.Mspecialization;

public interface MSpecializationRepository extends JpaRepository<Mspecialization, Long>{
	@Query(value="select * from m_specialization where is_delete = false", nativeQuery = true)
	List<Mspecialization> findByIsDelete();
	
	@Query(value="select * from m_specialization where name ilike concat('%',?1,'%') and is_delete = false", nativeQuery = true)
	List<Mspecialization> findByName(String name);
	
	Page<Mspecialization> findByIsDelete(Pageable page, Boolean isDelete);
	
	@Query(value = "SELECT * FROM m_payment_method WHERE is_delete=false", nativeQuery = true)
    Page<MPaymentMethod> getMPaymentMethodByPageable(Pageable pageable);
	
	@Query(value="select * from m_specialization where name ilike concat('%',?1,'%') and is_delete = false",nativeQuery = true)
	Page<Mspecialization> findByNamePageable(String name,Pageable page);
	
}
