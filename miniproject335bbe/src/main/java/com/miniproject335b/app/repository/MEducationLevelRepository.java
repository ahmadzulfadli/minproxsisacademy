package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MEducationLevel;

public interface MEducationLevelRepository extends JpaRepository<MEducationLevel, Long>{
	
	List<MEducationLevel> findByIsDelete(Boolean isDelete);
	
	@Query(value="select * from m_education_level mel where name ilike concat('%',?1,'%') and is_delete = false", nativeQuery = true)
	List<MEducationLevel> searchByName(String name);
}
