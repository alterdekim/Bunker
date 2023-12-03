package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Health;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HealthRepository extends JpaRepository<Health, Long> {
    Optional<Health> findById(Long id);

    List<Health> findAll();
}
