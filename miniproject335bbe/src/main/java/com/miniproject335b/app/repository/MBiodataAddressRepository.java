package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MBiodataAddress;

public interface MBiodataAddressRepository extends JpaRepository<MBiodataAddress, Long>{
	
	@Query(value="select * from m_biodata_address mba where mba.is_delete = false order by id asc", nativeQuery = true)
	List<MBiodataAddress> findByIsDelete(Boolean isDelete);
	
	// @Query(value="select * from m_biodata_address mba  \r\n"
	// 		+ "where is_delete=false \r\n"
	// 		+ "and mba.address ilike concat('%',?1,'%') \r\n"
	// 		+ "or mba.recipient ilike concat('%',?1,'%')", nativeQuery = true)
	// Page<MBiodataAddress> findBySearch(Pageable pageable, String name);

	// @Query(value="SELECT * FROM m_biodata_address mba \n" + //
	// 		"WHERE is_delete=false\n" + //
	// 		"AND mba.address ilike concat('%',?1,'%')\n" + //
	// 		"OR mba.recipient ilike concat('%',?2,'%')", nativeQuery = true)
	// Page<MBiodataAddress> findByAddressAndRecipient(Pageable pageable, String address, String recipient);

	@Query(value="SELECT * FROM m_biodata_address mba \n" + //
			"WHERE is_delete=false\n" + //
			"AND address ilike concat('%',?1,'%')\n" + //
			"OR recipient ilike concat('%',?2,'%')", nativeQuery = true)
	Page<MBiodataAddress> getByAddressAndRecipient(Pageable pageable, String address, String recipient);
	
	@Query(value="select * from m_biodata_address mba where is_delete=false", nativeQuery = true)
	Page<MBiodataAddress> getMBiodataAddressByPageable(Pageable pageable);
}
