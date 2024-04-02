package com.project.anime.repository;

import com.project.anime.entity.Nomination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NominationRepository extends JpaRepository<Nomination, Integer> {
}
