package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miniproject335b.app.model.MCustomerRelation;

public interface MCustomerRelationRepository extends JpaRepository<MCustomerRelation, Long>{
	List<MCustomerRelation> findByIsDelete(Boolean isDelete);


}
