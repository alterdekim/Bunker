package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Luggage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LuggageRepository extends JpaRepository<Luggage, Long> {
    Optional<Luggage> findById(Long id);

    List<Luggage> findAll();
}
