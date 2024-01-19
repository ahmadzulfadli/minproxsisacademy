package com.miniproject335b.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MUser;

public interface MUserRepository extends JpaRepository<MUser, Long>{
	@Query(value="select * from m_user mu where mu.email=?1 and mu.is_delete=false", nativeQuery=true)
	List<MUser> findActiveByEmail(String email);
	
	@Query(value="select id from m_user mu order by id desc limit 1", nativeQuery=true)
	public Long maxId();
	
	@Query(value = "select * from m_user where email = ?1 and is_delete = false", nativeQuery = true)
	MUser findByEmail(String email);
	
	@Query(value = "select * from m_user where id=?1 and is_delete=false", nativeQuery=true)
	Optional<MUser> findById(Long id);
}
