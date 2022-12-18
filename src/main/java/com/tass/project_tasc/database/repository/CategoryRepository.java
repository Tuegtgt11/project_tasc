package com.tass.project_tasc.database.repository;

import com.tass.project_tasc.database.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>  {
    Optional<Category> findByName(String name);
}
