package com.project.anime.repository;

import com.project.anime.entity.Critics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriticsRepository extends JpaRepository<Critics, Integer> {
    List<Critics> findAllByTitle(String title);
}
