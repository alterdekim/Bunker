package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Optional<Work> findById(Long id);

    List<Work> findAll();
}
