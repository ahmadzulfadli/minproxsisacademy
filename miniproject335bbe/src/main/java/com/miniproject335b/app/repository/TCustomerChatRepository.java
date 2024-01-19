package com.miniproject335b.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TCustomerChat;

public interface TCustomerChatRepository extends JpaRepository<TCustomerChat, Long>{

	@Query(value = "select count(id) from t_customer_chat where doctor_id = ?1 and is_delete = false", nativeQuery = true)
	Long countChatByDoctorId(Long doctorId);
}
