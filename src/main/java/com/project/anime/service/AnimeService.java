package com.project.anime.service;

import com.project.anime.aop.annotation.Logging;
import com.project.anime.aop.exception.InvalidRequestException;
import com.project.anime.aop.exception.ResourceNotFoundException;
import com.project.anime.cache.CacheEntity;
import com.project.anime.dto.anime.CreateAnime;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Nomination;
import com.project.anime.repository.AnimeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Logging
@Service
public class AnimeService {
  private final AnimeRepository animeRepository;
  private final CacheEntity<Integer, Anime> cache;

  public AnimeService(AnimeRepository animeRepository, CacheEntity<Integer, Anime> cache) {
    this.animeRepository = animeRepository;
    this.cache = cache;
  }

  public void createAnime(CreateAnime anime) {
    try {
      Anime newAnime = new Anime(anime.getTitle(), anime.getStartDate(), anime.getEndDate());
      animeRepository.save(newAnime);
    } catch (Exception e) {
      throw new InvalidRequestException(e.getMessage());
    }
  }

  public List<Anime> getAllAnime() {
    return animeRepository.findAll();
  }

  public Anime getAnimeById(Integer id) {
    Anime anime = cache.get(id);
    if (anime == null) {
      anime = animeRepository.findById(id).orElseThrow(
          () -> new ResourceNotFoundException("Anime (with id = " + id + ") not found"));
    }
    cache.put(id, anime);
    return anime;
  }

  public void updateAnime(Integer id, Anime newAnime) {
    try {
      newAnime.setId(id);
      cache.remove(id);
      animeRepository.save(newAnime);
    } catch (Exception e) {
      throw new InvalidRequestException(e.getMessage());
    }
  }

  public void partialUpdateAnime(Integer id, Anime updates) {
    Optional<Anime> optionalAnimeanime = animeRepository.findById(id);
    if (!optionalAnimeanime.isPresent()) {
      return;
    }
    Anime anime = optionalAnimeanime.get();
    if (updates.getTitle() == null && updates.getStartDate() == null
        && updates.getEndDate() == null) {
      throw new InvalidRequestException("Updates were not provided.");
    }
    if (updates.getTitle() != null) {
      anime.setTitle(updates.getTitle());
    }
    if (updates.getStartDate() != null) {
      anime.setStartDate(updates.getStartDate());
    }
    if (updates.getEndDate() != null) {
      anime.setEndDate(updates.getEndDate());
    }
    cache.remove(id);
    animeRepository.save(anime);
  }

  public void deleteAnime(Integer id) {
    Anime anime =
        animeRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Anime (with id = " + id + ") not found"));
    for (Nomination nomination : anime.getNominations()) {
      nomination.getCandidates().remove(anime);
    }
    anime.getNominations().clear();
    cache.remove(id);
    animeRepository.deleteById(id);
  }

}