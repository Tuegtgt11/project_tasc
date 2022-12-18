package com.tass.project_tasc.database.repository;

import com.tass.project_tasc.database.entities.Role;
import com.tass.project_tasc.database.entities.User;
import com.tass.project_tasc.database.entities.myenums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    Optional<User> findByUsernameAndStatus(String username, UserStatus status);

    Optional<User> findByUsername(String username);

    List<User> findAllByStatus(UserStatus status);

    List<User> findAllByRole(Role role);

}
