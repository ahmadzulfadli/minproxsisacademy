package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.MMenu;
import com.miniproject335b.app.model.MUser;

public interface MMenuRepository extends JpaRepository<MMenu, Long>{
	
	@Query(value="select * from m_menu where is_delete = false", nativeQuery = true)
	List<MMenu> findByIsDelete();
	
	@Query(value="select mm.* from m_menu mm "
			+ "join m_menu_role mmr on mm.id = mmr.menu_id "
			+ "join m_role mr on mmr.role_id = mr.id "
			+ "join m_user mu on mu.role_id = mr.id "
			+ "where mu.id = ?1 and mm.is_delete = false", nativeQuery = true)
	List<MMenu> findByIsDeleteMenu(Long id);
	
	@Query(value="select mu.* from m_user mu \r\n"
			+ "join m_role mr on mu.role_id = mr.id \r\n"
			+ "join m_menu_role mmr on mmr.role_id = mr.id \r\n"
			+ "join m_menu mm on mm.id = mmr.menu_id \r\n"
			+ "where mu.id = ?1 and mu.is_delete = false \r\n"
			+ "limit 1;", nativeQuery = true)
	List<MUser> findByIsDeleteUser(Long id);
	

	//CreatedByBayu
	@Query(value="select * from m_menu mm where parent_id isnull and is_delete = false order by mm.id", nativeQuery=true)
	List<MMenu> findTopMenu();
	@Query(value="select * from m_menu mm where parent_id = ?1 and is_delete = false", nativeQuery=true)
	List<MMenu> findSubMenu(Long id);
}
