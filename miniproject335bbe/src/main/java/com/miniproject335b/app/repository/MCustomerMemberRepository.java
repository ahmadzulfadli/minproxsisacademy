package com.miniproject335b.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MCustomerMember;

public interface MCustomerMemberRepository extends JpaRepository<MCustomerMember, Long> {
	List<MCustomerMember> findByIsDelete(Boolean isDelete);

	@Query(value = "select mcm.id , mcm.customer_id , mcm.customer_relation_id , mcm.parent_biodata_id ,mb.fullname,\r\n"
			+ "extract (year from now())- extract (year from dob) as \"usia\",mc.gender,mbg.code,mc.rhesus_type,mc.\"height\",mc.weight,mcr.\"name\" \r\n"
			+ "from m_customer_member mcm \r\n" + "left join m_customer mc on mc.id = mcm.customer_id \r\n"
			+ "left join m_biodata mb on mb.id = mc.biodata_id \r\n"
			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id \r\n"
			+ "left join m_blood_group mbg on mbg.id = mc.blood_group_id\r\n"
			+ "where mcm.is_delete=false and mb.fullname ilike concat('%',?2,'%')", nativeQuery = true)
	Page<MCustomerMember> findBySearch(Pageable pageable, String name);
	
	//paging
	Page<MCustomerMember> findByIsDelete(Pageable page, Boolean isDelete);
	//search
	@Query(value = "select mcm.id ,mb.fullname as name ,mc.dob as usia, mcr.name as relasi from m_customer_member mcm \r\n"
			+ "left join m_customer mc on mc.id = mcm.customer_id \r\n"
			+ "left join m_biodata mb on mb.id = mc.biodata_id \r\n"
			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id \r\n"
			+ "left join m_blood_group mbg on mbg.id = mc.blood_group_id\r\n"
			+ "where mcm.is_delete=false and mb.fullname ilike concat('%',?1,'%')", nativeQuery = true)
	List<Object> searchByName(String name);

	// testpagingsort
	@Query(value = "select  mcm.*, mb.fullname, mc.dob from m_customer_member mcm\r\n"
			+ "left join m_customer mc on mc.id = mcm.customer_id\r\n"
			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id\r\n"
			+ "left join m_biodata mb on mb.id = mc.biodata_id\r\n" + "where mcm.is_delete = false\r\n"
			+ "order by case when ?1 = 'fullname' then mb.fullname end asc, case when ?1 = 'name' then mcr.name end asc, case when ?1 = 'dob' then mc.dob end asc;", nativeQuery = true)
	Page<MCustomerMember> findByIsDelete(Pageable page, String key, Boolean isDelete);

	// sort
	@Query(value = "select  mcm.*, mb.fullname, mc.dob from m_customer_member mcm\r\n"
			+ "left join m_customer mc on mc.id = mcm.customer_id\r\n"
			+ "left join m_customer_relation mcr on mcr.id = mcm.customer_relation_id\r\n"
			+ "left join m_biodata mb on mb.id = mc.biodata_id\r\n" + "where mcm.is_delete = false\r\n"
			+ "order by case when ?1 = 'fullname' then mb.fullname end asc, case when ?1 = 'name' then mcr.name end asc, case when ?1 = 'dob' then mc.dob end asc;", nativeQuery = true)
	Page<MCustomerMember> findByIsDelete(Pageable page, String key);
	//find id
	@Query(value = "select * from m_customer_member mcm where id = ?1 and is_delete = ?2", nativeQuery = true)
	Optional<MCustomerMember> findByIdFalse(Long id, boolean isDelete);
	
	//MultipleDelete
	@Query(value="select * from m_customer_member mcm where id in (:ids)", nativeQuery=true)
	List<MCustomerMember> findByMultiSelect(List<Integer> ids);

}
