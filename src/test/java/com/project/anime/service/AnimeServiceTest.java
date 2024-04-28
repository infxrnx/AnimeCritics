package com.project.anime.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.anime.aop.exception.InvalidRequestException;
import com.project.anime.aop.exception.ResourceNotFoundException;
import com.project.anime.cache.CacheEntity;
import com.project.anime.dto.anime.CreateAnime;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Nomination;
import com.project.anime.repository.AnimeRepository;
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

@ContextConfiguration(classes = {AnimeService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AnimeServiceTest {
  @MockBean
  private AnimeRepository animeRepository;

  @Autowired
  private AnimeService animeService;

  @MockBean
  private CacheEntity<Integer, Anime> cacheEntity;

  /**
   * Method under test: {@link AnimeService#createBulkAnime(List)}
   */
  @Test
  void testCreateBulkAnime() {
    ArrayList<CreateAnime> arrayList = new ArrayList<>();
    assertThrows(ResourceNotFoundException.class,
        () -> animeService.createBulkAnime(arrayList));
  }

  /**
   * Method under test: {@link AnimeService#createBulkAnime(List)}
   */
  @Test
  void testCreateBulkAnime2() {
    // Arrange
    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(new HashSet<>());
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);
    when(animeRepository.save(Mockito.<Anime>any())).thenReturn(anime);
    doNothing().when(cacheEntity).clear();

    ArrayList<CreateAnime> newAnime = new ArrayList<>();
    newAnime.add(new CreateAnime("Dr", mock(Date.class), mock(Date.class)));

    // Act
    animeService.createBulkAnime(newAnime);

    // Assert
    verify(cacheEntity).clear();
    verify(animeRepository).save(isA(Anime.class));
  }

  /**
   * Method under test: {@link AnimeService#createBulkAnime(List)}
   */
  @Test
  void testCreateBulkAnime3() {
    // Arrange
    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(new HashSet<>());
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);
    when(animeRepository.save(Mockito.<Anime>any())).thenReturn(anime);
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity).clear();

    ArrayList<CreateAnime> newAnime = new ArrayList<>();
    newAnime.add(new CreateAnime("Dr", mock(Date.class), mock(Date.class)));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> animeService.createBulkAnime(newAnime));
    verify(cacheEntity).clear();
    verify(animeRepository).save(isA(Anime.class));
  }

  /**
   * Method under test: {@link AnimeService#createBulkAnime(List)}
   */
  @Test
  void testCreateBulkAnime4() {
    // Arrange
    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(new HashSet<>());
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);
    when(animeRepository.save(Mockito.<Anime>any())).thenReturn(anime);
    doNothing().when(cacheEntity).clear();

    ArrayList<CreateAnime> newAnime = new ArrayList<>();
    newAnime.add(new CreateAnime("Dr", mock(Date.class), mock(Date.class)));
    newAnime.add(new CreateAnime("Dr", mock(Date.class), mock(Date.class)));

    // Act
    animeService.createBulkAnime(newAnime);

    // Assert
    verify(cacheEntity).clear();
    verify(animeRepository, atLeast(1)).save(Mockito.<Anime>any());
  }

  /**
   * Method under test: {@link AnimeService#createBulkAnime(List)}
   */
  @Test
  void testCreateBulkAnime5() {
    // Arrange
    doNothing().when(cacheEntity).clear();

    ArrayList<CreateAnime> newAnime = new ArrayList<>();
    newAnime.add(null);

    // Act and Assert
    assertThrows(IllegalArgumentException.class, () -> animeService.createBulkAnime(newAnime));
    verify(cacheEntity).clear();
  }

  /**
   * Method under test: {@link AnimeService#createAnime(CreateAnime)}
   */
  @Test
  void testCreateAnime() {
    // Arrange
    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(new HashSet<>());
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);
    when(animeRepository.save(Mockito.<Anime>any())).thenReturn(anime);

    // Act
    animeService.createAnime(new CreateAnime("Dr", mock(Date.class), mock(Date.class)));

    // Assert
    verify(animeRepository).save(isA(Anime.class));
  }

  /**
   * Method under test: {@link AnimeService#createAnime(CreateAnime)}
   */
  @Test
  void testCreateAnime2() {
    // Arrange, Act and Assert
    assertThrows(InvalidRequestException.class, () -> animeService.createAnime(null));
  }

  /**
   * Method under test: {@link AnimeService#createAnime(CreateAnime)}
   */
  @Test
  void testCreateAnime3() {
    // Arrange
    when(animeRepository.save(Mockito.<Anime>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));
    CreateAnime anime = new CreateAnime("Dr", mock(Date.class), mock(Date.class));
    // Act and Assert
    assertThrows(InvalidRequestException.class,
        () -> animeService.createAnime(anime));
    verify(animeRepository).save(isA(Anime.class));
  }

  /**
   * Method under test: {@link AnimeService#getAllAnime()}
   */
  @Test
  void testGetAllAnime() {
    // Arrange
    ArrayList<Anime> animeList = new ArrayList<>();
    when(animeRepository.findAll()).thenReturn(animeList);

    // Act
    List<Anime> actualAllAnime = animeService.getAllAnime();

    // Assert
    verify(animeRepository).findAll();
    assertTrue(actualAllAnime.isEmpty());
    assertSame(animeList, actualAllAnime);
  }

  /**
   * Method under test: {@link AnimeService#getAllAnime()}
   */
  @Test
  void testGetAllAnime2() {
    // Arrange
    when(animeRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> animeService.getAllAnime());
    verify(animeRepository).findAll();
  }

  /**
   * Method under test: {@link AnimeService#getAnimeById(Integer)}
   */
  @Test
  void testGetAnimeById() {
    // Arrange
    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(new HashSet<>());
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);
    when(cacheEntity.get(Mockito.<Integer>any())).thenReturn(anime);
    doNothing().when(cacheEntity).put(Mockito.<Integer>any(), Mockito.<Anime>any());

    // Act
    Anime actualAnimeById = animeService.getAnimeById(1);

    // Assert
    verify(cacheEntity).get(1);
    verify(cacheEntity).put(eq(1), isA(Anime.class));
    assertSame(anime, actualAnimeById);
  }

  /**
   * Method under test: {@link AnimeService#getAnimeById(Integer)}
   */
  @Test
  void testGetAnimeById2() {
    // Arrange
    when(cacheEntity.get(Mockito.<Integer>any())).thenThrow(
        new InvalidRequestException("An error occurred"));

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> animeService.getAnimeById(1));
    verify(cacheEntity).get(1);
  }

  /**
   * Method under test: {@link AnimeService#updateAnime(Integer, Anime)}
   */
  @Test
  void testUpdateAnime() {
    // Arrange
    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(new HashSet<>());
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);
    when(animeRepository.save(Mockito.<Anime>any())).thenReturn(anime);
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    Anime newAnime = new Anime();
    newAnime.setEndDate(mock(Date.class));
    newAnime.setId(1);
    newAnime.setNominations(new HashSet<>());
    newAnime.setReviewCount(3);
    newAnime.setReviews(new HashSet<>());
    newAnime.setStartDate(mock(Date.class));
    newAnime.setTitle("Dr");
    newAnime.setTotalRating(1);

    // Act
    animeService.updateAnime(1, newAnime);

    // Assert
    verify(cacheEntity).remove(1);
    verify(animeRepository).save(isA(Anime.class));
    assertEquals(1, newAnime.getId().intValue());
  }

  /**
   * Method under test: {@link AnimeService#updateAnime(Integer, Anime)}
   */
  @Test
  void testUpdateAnime2() {
    // Arrange
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    Anime newAnime = new Anime();
    newAnime.setEndDate(mock(Date.class));
    newAnime.setId(1);
    newAnime.setNominations(new HashSet<>());
    newAnime.setReviewCount(3);
    newAnime.setReviews(new HashSet<>());
    newAnime.setStartDate(mock(Date.class));
    newAnime.setTitle("Dr");
    newAnime.setTotalRating(1);

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> animeService.updateAnime(1, newAnime));
    verify(cacheEntity).remove(1);
  }

  /**
   * Method under test: {@link AnimeService#partialUpdateAnime(Integer, Anime)}
   */
  @Test
  void testPartialUpdateAnime() {
    // Arrange
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

    Anime anime2 = new Anime();
    anime2.setEndDate(mock(Date.class));
    anime2.setId(1);
    anime2.setNominations(new HashSet<>());
    anime2.setReviewCount(3);
    anime2.setReviews(new HashSet<>());
    anime2.setStartDate(mock(Date.class));
    anime2.setTitle("Dr");
    anime2.setTotalRating(1);
    when(animeRepository.save(Mockito.<Anime>any())).thenReturn(anime2);
    when(animeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    Anime updates = new Anime();
    updates.setEndDate(mock(Date.class));
    updates.setId(1);
    updates.setNominations(new HashSet<>());
    updates.setReviewCount(3);
    updates.setReviews(new HashSet<>());
    updates.setStartDate(mock(Date.class));
    updates.setTitle("Dr");
    updates.setTotalRating(1);

    // Act
    animeService.partialUpdateAnime(1, updates);

    // Assert
    verify(cacheEntity).remove(1);
    verify(animeRepository).findById(1);
    verify(animeRepository).save(isA(Anime.class));
  }

  /**
   * Method under test: {@link AnimeService#partialUpdateAnime(Integer, Anime)}
   */
  @Test
  void testPartialUpdateAnime2() {
    // Arrange
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
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    Anime updates = new Anime();
    updates.setEndDate(mock(Date.class));
    updates.setId(1);
    updates.setNominations(new HashSet<>());
    updates.setReviewCount(3);
    updates.setReviews(new HashSet<>());
    updates.setStartDate(mock(Date.class));
    updates.setTitle("Dr");
    updates.setTotalRating(1);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> animeService.partialUpdateAnime(1, updates));
    verify(cacheEntity).remove(1);
    verify(animeRepository).findById(1);
  }

  /**
   * Method under test: {@link AnimeService#partialUpdateAnime(Integer, Anime)}
   */
  @Test
  void testPartialUpdateAnime3() {
    // Arrange
    Optional<Anime> emptyResult = Optional.empty();
    when(animeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

    Anime updates = new Anime();
    updates.setEndDate(mock(Date.class));
    updates.setId(1);
    updates.setNominations(new HashSet<>());
    updates.setReviewCount(3);
    updates.setReviews(new HashSet<>());
    updates.setStartDate(mock(Date.class));
    updates.setTitle("Dr");
    updates.setTotalRating(1);

    // Act and Assert
    assertThrows(InvalidRequestException.class, () -> animeService.partialUpdateAnime(1, updates));
    verify(animeRepository).findById(1);
  }

  /**
   * Method under test: {@link AnimeService#deleteAnime(Integer)}
   */
  @Test
  void testDeleteAnime() {
    // Arrange
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
    doNothing().when(animeRepository).deleteById(Mockito.<Integer>any());
    when(animeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    // Act
    animeService.deleteAnime(1);

    // Assert
    verify(cacheEntity).remove(1);
    verify(animeRepository).deleteById(1);
    verify(animeRepository).findById(1);
  }

  /**
   * Method under test: {@link AnimeService#deleteAnime(Integer)}
   */
  @Test
  void testDeleteAnime2() {
    // Arrange
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
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> animeService.deleteAnime(1));
    verify(cacheEntity).remove(1);
    verify(animeRepository).findById(1);
  }

  /**
   * Method under test: {@link AnimeService#deleteAnime(Integer)}
   */
  @Test
  void testDeleteAnime3() {
    // Arrange
    Nomination nomination = new Nomination();
    nomination.setCandidates(new ArrayList<>());
    nomination.setId(1);
    nomination.setName("Name");

    HashSet<Nomination> nominations = new HashSet<>();
    nominations.add(nomination);

    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(nominations);
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);
    Optional<Anime> ofResult = Optional.of(anime);
    doNothing().when(animeRepository).deleteById(Mockito.<Integer>any());
    when(animeRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    // Act
    animeService.deleteAnime(1);

    // Assert
    verify(cacheEntity).remove(1);
    verify(animeRepository).deleteById(1);
    verify(animeRepository).findById(1);
  }

  /**
   * Method under test: {@link AnimeService#deleteAnime(Integer)}
   */
  @Test
  void testDeleteAnime4() {
    // Arrange
    Optional<Anime> emptyResult = Optional.empty();
    when(animeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> animeService.deleteAnime(1));
    verify(animeRepository).findById(1);
  }
}
