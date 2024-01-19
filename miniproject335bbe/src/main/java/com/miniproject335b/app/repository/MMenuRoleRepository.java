package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MMenuRole;

public interface MMenuRoleRepository extends JpaRepository<MMenuRole, Long> {
	
	List<MMenuRole> findByIsDelete(Boolean isDelete);
	
	@Query(value="select * from m_menu_role mmr where name ilike concat('%',?1,'%') and is_delete = false", nativeQuery = true)
	List<MMenuRole> searchByName(String name);
	
	@Query(value="select mmr.* from m_role mr \r\n"
			+ "left join m_menu_role mmr on mmr.role_id  = mr.id  \r\n"
			+ "left join m_menu mm on mm.id = mmr.menu_id\r\n"
			+ "where mr.id = ?1", nativeQuery=true)
	List<MMenuRole> findAllMenuRoleById(Long id);
	
	@Query(value="select * from m_menu_role mmr where role_id = ?1", nativeQuery=true)
	List<MMenuRole> findMenuRoleById(Long id);

}
