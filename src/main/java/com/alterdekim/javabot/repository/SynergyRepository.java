package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Synergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SynergyRepository extends JpaRepository<Synergy, Long> {
    Optional<Synergy> findById(Long id);

    List<Synergy> findAll();
}
