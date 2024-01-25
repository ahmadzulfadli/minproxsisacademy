package com.miniproject335b.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MCustomer;
//import com.miniproject335b.app.model.PasienHolder;
import com.miniproject335b.app.model.MUser;

public interface MCustomerRepository extends JpaRepository<MCustomer, Long> {
	List<MCustomer> findByIsDelete(Boolean isDelete);
	Page<MCustomer> findByIsDelete(Pageable page, Boolean isDelete);
	//Paging
//	@Query(value="select mcm.id , mcm.customer_id , mcm.customer_relation_id , mcm.parent_biodata_id ,mb.fullname,\r\n"
//			+ "extract (year from now())- extract (year from dob) as \"usia\",mc.gender,mbg.code,mc.rhesus_type,mc.\"height\",mc.weight,mcr.\"name\" \r\n"
//			+ "from m_customer_member mcm \r\n"
//			+ "left join m_customer mc on mc.id = mcm.customer_id \r\n"
//			+ "left join m_biodata mb on mb.id = mc.biodata_id \r\n"
//			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id \r\n"
//			+ "left join m_blood_group mbg on mbg.id = mc.blood_group_id", nativeQuery=true)
//	Page<Object> findByIsDelete(Pageable pageable, Boolean isDelete);
	
	@Query(value = "select max(id) from m_customer mc", nativeQuery=true)
	Long getMaxCustomerId();
	//TampilData
	@Query(value="select mb.fullname,extract (year from now()) - extract (year from dob) as \"usia\",mc.gender,mbg.code,mc.rhesus_type,mc.\"height\",mc.weight,mcr.\"name\" from m_customer_member mcm \r\n"
			+ "left join m_customer mc on mc.id = mcm.customer_id \r\n"
			+ "left join m_biodata mb on mb.id = mc.biodata_id \r\n"
			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id \r\n"
			+ "left join m_blood_group mbg on mbg.id = mc.blood_group_id;", nativeQuery=true)
	List<Object> findAllPasien();
	
	@Query(value="select * from m_customer mc where is_delete = false", nativeQuery=true)
	Page<MCustomer> getMCustomerByPageable(Pageable pageable);
	
	//SortByName
	@Query(value="select mb.fullname as name ,extract (year from now()) - extract (year from dob) as usia, mc.gender,mbg.code,mc.rhesus_type,mc.\"height\",mc.weight,mcr.\"name\" as relasi from m_customer_member mcm \r\n"
			+ "left join m_customer mc on mc.id = mcm.customer_id \r\n"
			+ "left join m_biodata mb on mb.id = mc.biodata_id \r\n"
			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id \r\n"
			+ "left join m_blood_group mbg on mbg.id = mc.blood_group_id\r\n"
			+ "group by mb.fullname, mc.dob, mc.gender , mbg.code , mc.rhesus_type , mc.\"height\",mc.weight,mcr.\"name\"\r\n", nativeQuery=true)
	Page<Object> sortByName(Pageable pageable);
	
	MCustomer findByBiodataId(Long biodataId);

	// query fadli
	@Query(value = "SELECT mu.*\n" + //
			"FROM m_user mu\n" + //
			"JOIN m_biodata mb\n" + //
			"ON mu.biodata_id = mb.id\n" + //
			"JOIN m_customer mc\n" + //
			"ON mc.biodata_id = mb.id\n" + //
			"WHERE mc.id = ?1", nativeQuery = true)
	Object findMUserByIdCustomer(Long idCustomer);
}
