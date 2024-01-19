package com.miniproject335b.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.miniproject335b.app.model.TToken;

public interface TTokenRepository extends JpaRepository<TToken, Long>{
	@Query(value="select * from t_token tt where tt.user_id=?1 and tt.created_on BETWEEN NOW() - INTERVAL '10 MINUTES' AND NOW()", nativeQuery=true)
	List<TToken> findActiveTokens(Long id);
	
	@Query(value="select * from t_token tt where tt.user_id=?1", nativeQuery=true)
	List<TToken> findTokenById(Long id);
	
	List<TToken> findTokenByToken(String token);
}
