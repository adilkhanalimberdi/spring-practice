package com.alimberdi.gatekeeper.repository;

import com.alimberdi.gatekeeper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	@Query("SELECT u.enabled FROM User u WHERE u.email = :email")
	boolean enabledByEmail(@Param("email") String email);

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

}
