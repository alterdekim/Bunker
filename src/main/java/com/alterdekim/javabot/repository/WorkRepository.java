package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Synergy;
import com.alterdekim.javabot.entities.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Long> {
    Optional<Work> findById(Long id);

    List<Work> findAll();

    @Query(value = "SELECT new Synergy(s.id, s.firstEntityId, s.firstType, s.secondEntityId, s.secondType, s.probabilityValue) FROM Synergy s WHERE (s.firstType = 4 AND s.firstEntityId = :uuid) OR (s.secondType = 4 AND s.secondEntityId = :uuid)")
    List<Synergy> getSynergies(@Param(value = "uuid") Long workId);
}
