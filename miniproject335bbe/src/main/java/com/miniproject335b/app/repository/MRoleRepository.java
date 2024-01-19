package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MRole;

public interface MRoleRepository extends JpaRepository<MRole, Long>{
	@Query(value = "SELECT * FROM m_role WHERE is_delete=false", nativeQuery = true)
    Page<MRole> getMRoleByPageable(Pageable pageable);
	
	@Query(value = "SELECT * FROM m_role WHERE is_delete=?1 ORDER BY id ASC ", nativeQuery = true)
    List<MRole> findByIsDelete(Boolean isDelete);
	
	@Query(value = "SELECT * FROM m_role WHERE is_delete=false AND name ilike concat('%',?1,'%') ORDER BY name ASC", nativeQuery = true)
    List<MRole> findBySearch(String name);
	
	@Query(value = "SELECT * FROM m_role WHERE is_delete=false AND name ilike concat('%',?1,'%') ORDER BY name ASC", nativeQuery = true)
    Page<MRole> findBySearchPageable(String name, Pageable pageable);
}
