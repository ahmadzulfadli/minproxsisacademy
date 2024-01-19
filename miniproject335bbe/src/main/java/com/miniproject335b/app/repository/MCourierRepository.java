package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.miniproject335b.app.model.MCourier;

@Repository
public interface MCourierRepository extends JpaRepository<MCourier, Long>{

    @Query(value = "SELECT * FROM m_courier WHERE is_delete = ?1", nativeQuery = true)
    List<MCourier> findByIsDelete(Boolean isDelete);
    
    @Query(value = "SELECT * FROM m_courier WHERE is_delete=false AND name ilike concat('%',?1,'%')", nativeQuery = true)
    Page<MCourier> findBySearch(Pageable pageable, String name);

    @Query(value = "SELECT * FROM m_courier WHERE is_delete=false", nativeQuery = true)
    Page<MCourier> getMCourierPageable(Pageable pageable);
} 