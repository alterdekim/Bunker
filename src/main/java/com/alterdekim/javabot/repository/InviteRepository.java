package com.alterdekim.javabot.repository;

import com.alterdekim.javabot.entities.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
    Optional<Invite> findById(Integer id);
}
