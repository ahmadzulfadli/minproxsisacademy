package com.miniproject335b.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MBiodata;

public interface MBiodataRepository extends JpaRepository<MBiodata, Long>{

	@Query(value = "select * from m_biodata where is_delete = false", nativeQuery = true)
	List<MBiodata> findByIsDelete();
	@Query(value = "select max(id) from m_biodata mc", nativeQuery=true)
	Long findMaxBiodataId();
	
	@Query(value="select * from m_biodata mb where fullname ilike concat('%',?1,'%')", nativeQuery=true)
	List<MBiodata> searchByName(String name);
	
	@Query(value = "select * from m_biodata mb where id = ?1 and is_delete = ?2", nativeQuery = true)
	Optional<MBiodata> findByIdFalse(Long id, boolean isDelete);
	
	@Query(value="select * from m_biodata mb where fullname ilike concat('%',?1,'%') and is_delete=false", nativeQuery=true)
	Page<MBiodata> searchByName(Pageable page ,String name);
}
