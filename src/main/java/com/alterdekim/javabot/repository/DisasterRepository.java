package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Disaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DisasterRepository extends JpaRepository<Disaster, Long> {
    Optional<Disaster> findById(Long id);

    List<Disaster> findAll();
}
