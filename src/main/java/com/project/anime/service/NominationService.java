package com.project.anime.service;

import com.project.anime.aop.annotation.Logging;
import com.project.anime.aop.exception.ResourceNotFoundException;
import com.project.anime.dto.nomination.CreateNomination;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Nomination;
import com.project.anime.repository.AnimeRepository;
import com.project.anime.repository.NominationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Logging
@Service
public class NominationService {
  private final NominationRepository nominationRepository;
  private final AnimeRepository animeRepository;

  public NominationService(NominationRepository nominationRepository,
                           AnimeRepository animeRepository) {
    this.nominationRepository = nominationRepository;
    this.animeRepository = animeRepository;
  }

  public void createNomination(CreateNomination newNominationData) {
    Nomination newNomination = new Nomination(newNominationData.getName());
    nominationRepository.save(newNomination);
  }
  public Optional<Nomination> getNominationByName(String name) {
    return nominationRepository.findNominationByNameIgnoreCase(name);
  }
  public List<Nomination> getAllNominations() {
    return nominationRepository.findAll();
  }

  public Nomination getNominationById(Integer id) {
    return nominationRepository.findById(id).orElseThrow(
          () -> new ResourceNotFoundException("Nomination (with id = " + id + ") not found"));
  }

  public void updateNomination(Integer id, Nomination newNomination) {
    newNomination.setId(id);
    nominationRepository.save(newNomination);
  }

  public void partialUpdateNomination(Integer id, Nomination updates) {
    Nomination nomination = nominationRepository.findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Nomination (with id = " + id + ") not found"));

    if (updates.getName() != null) {
      nomination.setName(updates.getName());
    }
    nominationRepository.save(nomination);
  }

  public void deleteNomination(Integer id) {
    nominationRepository.deleteById(id);
  }

  public void addAnimeToNomination(Integer nominationId, Integer animeId) {

    Nomination nomination = nominationRepository.findById(nominationId)
        .orElseThrow(() -> new ResourceNotFoundException("Номинация с ID " + nominationId + " не найдена"));

    Anime anime = animeRepository.findById(animeId)
        .orElseThrow(() -> new ResourceNotFoundException("Аниме с ID " + animeId + " не найдено"));

    nomination.addCandidates(anime);
    anime.addNomination(nomination);
    nominationRepository.save(nomination);
  }

  public List<Anime> findAnimeInNominationWithAverageRatingGreaterThanThreshold(
      Integer nominationId, Integer min) {
    return nominationRepository.findAnimeInNominationWithAverageRatingGreaterThanThreshold(
        nominationId, min);
  }
}
