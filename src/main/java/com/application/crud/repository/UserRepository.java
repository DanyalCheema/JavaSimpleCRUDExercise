package com.application.crud.repository;

import com.application.crud.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("SELECT u FROM User u JOIN u.role r WHERE r.roleName = :roleName")
  List<User> findUsersByRoleName(@Param("roleName") String roleName);

  Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
