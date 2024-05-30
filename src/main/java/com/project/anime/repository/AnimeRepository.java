package com.project.anime.repository;


import com.project.anime.entity.Anime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {
  List<Anime> findByTitleContainingIgnoreCase(String title);
  Optional<Anime> findByTitle(String title);
}
