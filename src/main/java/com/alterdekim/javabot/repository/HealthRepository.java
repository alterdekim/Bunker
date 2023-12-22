package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Health;
import com.alterdekim.javabot.entities.Synergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HealthRepository extends JpaRepository<Health, Long> {
    Optional<Health> findById(Long id);

    List<Health> findAll();

    @Query(value = "SELECT new Synergy(s.id, s.firstEntityId, s.firstType, s.secondEntityId, s.secondType, s.probabilityValue) FROM Synergy s WHERE (s.firstType = 1 AND s.firstEntityId = :uuid) OR (s.secondType = 1 AND s.secondEntityId = :uuid)")
    List<Synergy> getSynergies(@Param(value = "uuid") Long healthId);
}
