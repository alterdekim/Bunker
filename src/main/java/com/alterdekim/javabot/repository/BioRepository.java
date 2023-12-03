package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Bio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BioRepository extends JpaRepository<Bio, Long> {
    Optional<Bio> findById(Long id);

    List<Bio> findAll();
}