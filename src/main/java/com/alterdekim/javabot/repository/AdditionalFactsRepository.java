package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.AdditionalFacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdditionalFactsRepository extends JpaRepository<AdditionalFacts, Long> {
    Optional<AdditionalFacts> findById(Long id);

    List<AdditionalFacts> findAll();
}
