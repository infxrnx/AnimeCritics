package com.project.anime.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.anime.aop.exception.ResourceNotFoundException;
import com.project.anime.cache.CacheEntity;
import com.project.anime.dto.nomination.CreateNomination;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Nomination;
import com.project.anime.repository.AnimeRepository;
import com.project.anime.repository.NominationRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {NominationService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class NominationServiceTest {
  @MockBean
  private AnimeRepository animeRepository;

  @MockBean
  private CacheEntity<Integer, Nomination> cacheEntity;

  @MockBean
  private NominationRepository nominationRepository;

  @Autowired
  private NominationService nominationService;

  /**
   * Method under test:
   * {@link NominationService#createNomination(CreateNomination)}
   */
  @Test
  void testCreateNomination() {
    // Arrange
    Nomination nomination = new Nomination();
    nomination.setCandidates(new ArrayList<>());
    nomination.setId(1);
    nomination.setName("Name");
    when(nominationRepository.save(Mockito.<Nomination>any())).thenReturn(nomination);

    CreateNomination newNominationData = new CreateNomination();
    newNominationData.setName("Name");

    // Act
    nominationService.createNomination(newNominationData);

    // Assert
    verify(nominationRepository).save(isA(Nomination.class));
  }

  /**
   * Method under test:
   * {@link NominationService#createNomination(CreateNomination)}
   */
  @Test
  void testCreateNomination2() {
    // Arrange
    when(nominationRepository.save(Mockito.<Nomination>any()))
        .thenThrow(new ResourceNotFoundException("An error occurred"));

    CreateNomination newNominationData = new CreateNomination();
    newNominationData.setName("Name");

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> nominationService.createNomination(newNominationData));
    verify(nominationRepository).save(isA(Nomination.class));
  }

  /**
   * Method under test: {@link NominationService#getAllNominations()}
   */
  @Test
  void testGetAllNominations() {
    // Arrange
    ArrayList<Nomination> nominationList = new ArrayList<>();
    when(nominationRepository.findAll()).thenReturn(nominationList);

    // Act
    List<Nomination> actualAllNominations = nominationService.getAllNominations();

    // Assert
    verify(nominationRepository).findAll();
    assertTrue(actualAllNominations.isEmpty());
    assertSame(nominationList, actualAllNominations);
  }

  /**
   * Method under test: {@link NominationService#getAllNominations()}
   */
  @Test
  void testGetAllNominations2() {
    // Arrange
    when(nominationRepository.findAll()).thenThrow(
        new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> nominationService.getAllNominations());
    verify(nominationRepository).findAll();
  }

  /**
   * Method under test: {@link NominationService#getNominationById(Integer)}
   */
  @Test
  void testGetNominationById() {
    // Arrange
    Nomination nomination = new Nomination();
    nomination.setCandidates(new ArrayList<>());
    nomination.setId(1);
    nomination.setName("Name");
    when(cacheEntity.get(Mockito.<Integer>any())).thenReturn(nomination);

    // Act
    Nomination actualNominationById = nominationService.getNominationById(1);

    // Assert
    verify(cacheEntity).get(eq(1));
    assertSame(nomination, actualNominationById);
  }

  /**
   * Method under test: {@link NominationService#getNominationById(Integer)}
   */
  @Test
  void testGetNominationById2() {
    // Arrange
    when(cacheEntity.get(Mockito.<Integer>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> nominationService.getNominationById(1));
    verify(cacheEntity).get(eq(1));
  }

  /**
   * Method under test:
   * {@link NominationService#updateNomination(Integer, Nomination)}
   */
  @Test
  void testUpdateNomination() {
    // Arrange
    Nomination nomination = new Nomination();
    nomination.setCandidates(new ArrayList<>());
    nomination.setId(1);
    nomination.setName("Name");
    when(nominationRepository.save(Mockito.<Nomination>any())).thenReturn(nomination);
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    Nomination newNomination = new Nomination();
    newNomination.setCandidates(new ArrayList<>());
    newNomination.setId(1);
    newNomination.setName("Name");

    // Act
    nominationService.updateNomination(1, newNomination);

    // Assert
    verify(cacheEntity).remove(eq(1));
    verify(nominationRepository).save(isA(Nomination.class));
    assertEquals(1, newNomination.getId().intValue());
  }

  /**
   * Method under test:
   * {@link NominationService#updateNomination(Integer, Nomination)}
   */
  @Test
  void testUpdateNomination2() {
    // Arrange
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    Nomination newNomination = new Nomination();
    newNomination.setCandidates(new ArrayList<>());
    newNomination.setId(1);
    newNomination.setName("Name");

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> nominationService.updateNomination(1, newNomination));
    verify(cacheEntity).remove(eq(1));
  }

  /**
   * Method under test:
   * {@link NominationService#partialUpdateNomination(Integer, Nomination)}
   */
  @Test
  void testPartialUpdateNomination() {
    // Arrange
    Nomination nomination = new Nomination();
    nomination.setCandidates(new ArrayList<>());
    nomination.setId(1);
    nomination.setName("Name");
    Optional<Nomination> ofResult = Optional.of(nomination);

    Nomination nomination2 = new Nomination();
    nomination2.setCandidates(new ArrayList<>());
    nomination2.setId(1);
    nomination2.setName("Name");
    when(nominationRepository.save(Mockito.<Nomination>any())).thenReturn(nomination2);
    when(nominationRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    Nomination updates = new Nomination();
    updates.setCandidates(new ArrayList<>());
    updates.setId(1);
    updates.setName("Name");

    // Act
    nominationService.partialUpdateNomination(1, updates);

    // Assert
    verify(cacheEntity).remove(eq(1));
    verify(nominationRepository).findById(eq(1));
    verify(nominationRepository).save(isA(Nomination.class));
  }

  /**
   * Method under test:
   * {@link NominationService#partialUpdateNomination(Integer, Nomination)}
   */
  @Test
  void testPartialUpdateNomination2() {
    // Arrange
    Nomination nomination = new Nomination();
    nomination.setCandidates(new ArrayList<>());
    nomination.setId(1);
    nomination.setName("Name");
    Optional<Nomination> ofResult = Optional.of(nomination);
    when(nominationRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    Nomination updates = new Nomination();
    updates.setCandidates(new ArrayList<>());
    updates.setId(1);
    updates.setName("Name");

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> nominationService.partialUpdateNomination(1, updates));
    verify(cacheEntity).remove(eq(1));
    verify(nominationRepository).findById(eq(1));
  }

  /**
   * Method under test:
   * {@link NominationService#partialUpdateNomination(Integer, Nomination)}
   */
  @Test
  void testPartialUpdateNomination3() {
    // Arrange
    Optional<Nomination> emptyResult = Optional.empty();
    when(nominationRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

    Nomination updates = new Nomination();
    updates.setCandidates(new ArrayList<>());
    updates.setId(1);
    updates.setName("Name");

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> nominationService.partialUpdateNomination(1, updates));
    verify(nominationRepository).findById(eq(1));
  }

  /**
   * Method under test: {@link NominationService#deleteNomination(Integer)}
   */
  @Test
  void testDeleteNomination() {
    // Arrange
    doNothing().when(nominationRepository).deleteById(Mockito.<Integer>any());
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    // Act
    nominationService.deleteNomination(1);

    // Assert that nothing has changed
    verify(cacheEntity).remove(eq(1));
    verify(nominationRepository).deleteById(eq(1));
  }

  /**
   * Method under test: {@link NominationService#deleteNomination(Integer)}
   */
  @Test
  void testDeleteNomination2() {
    // Arrange
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> nominationService.deleteNomination(1));
    verify(cacheEntity).remove(eq(1));
  }

  /**
   * Method under test:
   * {@link NominationService#addAnimeToNomination(Integer, Integer)}
   */
  @Test
  void testAddAnimeToNomination() {
    // Arrange
    Optional<Nomination> emptyResult = Optional.empty();
    when(nominationRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(new HashSet<>());
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);
    Optional<Anime> ofResult = Optional.of(anime);
    when(animeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> nominationService.addAnimeToNomination(1, 1));
    verify(nominationRepository).findById(eq(1));
  }

  /**
   * Method under test:
   * {@link NominationService#addAnimeToNomination(Integer, Integer)}
   */
  @Test
  void testAddAnimeToNomination2() {
    // Arrange
    Nomination nomination = new Nomination();
    nomination.setCandidates(new ArrayList<>());
    nomination.setId(1);
    nomination.setName("Name");
    Optional<Nomination> ofResult = Optional.of(nomination);
    when(nominationRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    Optional<Anime> emptyResult = Optional.empty();
    when(animeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> nominationService.addAnimeToNomination(1, 1));
    verify(animeRepository).findById(eq(1));
    verify(nominationRepository).findById(eq(1));
  }

  /**
   * Method under test:
   * {@link NominationService#findAnimeInNominationWithAverageRatingGreaterThanThreshold(Integer, Integer)}
   */
  @Test
  void testFindAnimeInNominationWithAverageRatingGreaterThanThreshold() {
    // Arrange
    ArrayList<Anime> animeList = new ArrayList<>();
    when(nominationRepository.findAnimeInNominationWithAverageRatingGreaterThanThreshold(
        Mockito.<Integer>any(),
        Mockito.<Integer>any())).thenReturn(animeList);

    // Act
    List<Anime> actualFindAnimeInNominationWithAverageRatingGreaterThanThresholdResult =
        nominationService
            .findAnimeInNominationWithAverageRatingGreaterThanThreshold(1, 1);

    // Assert
    verify(nominationRepository).findAnimeInNominationWithAverageRatingGreaterThanThreshold(eq(1),
        eq(1));
    assertTrue(actualFindAnimeInNominationWithAverageRatingGreaterThanThresholdResult.isEmpty());
    assertSame(animeList, actualFindAnimeInNominationWithAverageRatingGreaterThanThresholdResult);
  }

  /**
   * Method under test:
   * {@link NominationService#findAnimeInNominationWithAverageRatingGreaterThanThreshold(Integer, Integer)}
   */
  @Test
  void testFindAnimeInNominationWithAverageRatingGreaterThanThreshold2() {
    // Arrange
    when(nominationRepository.findAnimeInNominationWithAverageRatingGreaterThanThreshold(
        Mockito.<Integer>any(),
        Mockito.<Integer>any())).thenThrow(new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> nominationService.findAnimeInNominationWithAverageRatingGreaterThanThreshold(1, 1));
    verify(nominationRepository).findAnimeInNominationWithAverageRatingGreaterThanThreshold(eq(1),
        eq(1));
  }
}
