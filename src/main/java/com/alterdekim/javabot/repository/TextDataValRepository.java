package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.TextDataVal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextDataValRepository extends JpaRepository<TextDataVal, Long> {
    Optional<TextDataVal> findById(Long id);
}
