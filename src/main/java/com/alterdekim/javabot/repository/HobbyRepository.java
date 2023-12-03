package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HobbyRepository extends JpaRepository<Hobby, Long> {
    Optional<Hobby> findById(Long id);

    List<Hobby> findAll();
}
