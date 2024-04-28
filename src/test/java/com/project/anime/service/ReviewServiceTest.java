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
import com.project.anime.dto.review.CreateReview;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Review;
import com.project.anime.repository.AnimeRepository;
import com.project.anime.repository.ReviewRepository;
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

@ContextConfiguration(classes = {ReviewService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ReviewServiceTest {
  @MockBean
  private AnimeRepository animeRepository;

  @MockBean
  private CacheEntity<Integer, Review> cacheEntity;

  @MockBean
  private ReviewRepository reviewRepository;

  @Autowired
  private ReviewService reviewService;

  /**
   * Method under test: {@link ReviewService#createReview(CreateReview)}
   */
  @Test
  void testCreateReview() {
    // Arrange
    Optional<Anime> emptyResult = Optional.empty();
    when(animeRepository.findById(Mockito.<Integer>any())).thenReturn(emptyResult);

    CreateReview review = new CreateReview();
    review.setAnimeId(1);
    review.setGrade((byte) 'A');
    review.setText("Text");
    review.setTitle("Dr");

    // Act and Assert
    assertThrows(RuntimeException.class, () -> reviewService.createReview(review));
    verify(animeRepository).findById(1);
  }

  /**
   * Method under test: {@link ReviewService#getAllReviews()}
   */
  @Test
  void testGetAllReviews() {
    // Arrange
    ArrayList<Review> reviewList = new ArrayList<>();
    when(reviewRepository.findAll()).thenReturn(reviewList);

    // Act
    List<Review> actualAllReviews = reviewService.getAllReviews();

    // Assert
    verify(reviewRepository).findAll();
    assertTrue(actualAllReviews.isEmpty());
    assertSame(reviewList, actualAllReviews);
  }

  /**
   * Method under test: {@link ReviewService#getAllReviews()}
   */
  @Test
  void testGetAllReviews2() {
    // Arrange
    when(reviewRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> reviewService.getAllReviews());
    verify(reviewRepository).findAll();
  }

  /**
   * Method under test: {@link ReviewService#getReviewById(Integer)}
   */
  @Test
  void testGetReviewById() {
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

    Review review = new Review();
    review.setAnime(anime);
    review.setGrade((byte) 'A');
    review.setId(1);
    review.setText("Text");
    review.setTitle("Dr");
    when(cacheEntity.get(Mockito.<Integer>any())).thenReturn(review);

    // Act
    Review actualReviewById = reviewService.getReviewById(1);

    // Assert
    verify(cacheEntity).get(1);
    assertSame(review, actualReviewById);
  }

  /**
   * Method under test: {@link ReviewService#getReviewById(Integer)}
   */
  @Test
  void testGetReviewById2() {
    // Arrange
    when(cacheEntity.get(Mockito.<Integer>any())).thenThrow(
        new ResourceNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> reviewService.getReviewById(1));
    verify(cacheEntity).get(1);
  }

  /**
   * Method under test: {@link ReviewService#updateReview(Integer, Review)}
   */
  @Test
  void testUpdateReview() {
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

    Review review = new Review();
    review.setAnime(anime);
    review.setGrade((byte) 'A');
    review.setId(1);
    review.setText("Text");
    review.setTitle("Dr");
    when(reviewRepository.save(Mockito.<Review>any())).thenReturn(review);
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    Anime anime2 = new Anime();
    anime2.setEndDate(mock(Date.class));
    anime2.setId(1);
    anime2.setNominations(new HashSet<>());
    anime2.setReviewCount(3);
    anime2.setReviews(new HashSet<>());
    anime2.setStartDate(mock(Date.class));
    anime2.setTitle("Dr");
    anime2.setTotalRating(1);

    Review newReview = new Review();
    newReview.setAnime(anime2);
    newReview.setGrade((byte) 'A');
    newReview.setId(1);
    newReview.setText("Text");
    newReview.setTitle("Dr");

    // Act
    reviewService.updateReview(1, newReview);

    // Assert
    verify(cacheEntity).remove(1);
    verify(reviewRepository).save(isA(Review.class));
    assertEquals(1, newReview.getId().intValue());
  }

  /**
   * Method under test: {@link ReviewService#updateReview(Integer, Review)}
   */
  @Test
  void testUpdateReview2() {
    // Arrange
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    Anime anime = new Anime();
    anime.setEndDate(mock(Date.class));
    anime.setId(1);
    anime.setNominations(new HashSet<>());
    anime.setReviewCount(3);
    anime.setReviews(new HashSet<>());
    anime.setStartDate(mock(Date.class));
    anime.setTitle("Dr");
    anime.setTotalRating(1);

    Review newReview = new Review();
    newReview.setAnime(anime);
    newReview.setGrade((byte) 'A');
    newReview.setId(1);
    newReview.setText("Text");
    newReview.setTitle("Dr");

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> reviewService.updateReview(1, newReview));
    verify(cacheEntity).remove(1);
  }

  /**
   * Method under test: {@link ReviewService#partialUpdateReview(Integer, Review)}
   */
  @Test
  void testPartialUpdateReview() {
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

    Review review = new Review();
    review.setAnime(anime);
    review.setGrade((byte) 'A');
    review.setId(1);
    review.setText("Text");
    review.setTitle("Dr");
    Optional<Review> ofResult = Optional.of(review);

    Anime anime2 = new Anime();
    anime2.setEndDate(mock(Date.class));
    anime2.setId(1);
    anime2.setNominations(new HashSet<>());
    anime2.setReviewCount(3);
    anime2.setReviews(new HashSet<>());
    anime2.setStartDate(mock(Date.class));
    anime2.setTitle("Dr");
    anime2.setTotalRating(1);

    Review review2 = new Review();
    review2.setAnime(anime2);
    review2.setGrade((byte) 'A');
    review2.setId(1);
    review2.setText("Text");
    review2.setTitle("Dr");
    when(reviewRepository.save(Mockito.<Review>any())).thenReturn(review2);
    when(reviewRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    Anime anime3 = new Anime();
    anime3.setEndDate(mock(Date.class));
    anime3.setId(1);
    anime3.setNominations(new HashSet<>());
    anime3.setReviewCount(3);
    anime3.setReviews(new HashSet<>());
    anime3.setStartDate(mock(Date.class));
    anime3.setTitle("Dr");
    anime3.setTotalRating(1);

    Review updates = new Review();
    updates.setAnime(anime3);
    updates.setGrade((byte) 'A');
    updates.setId(1);
    updates.setText("Text");
    updates.setTitle("Dr");

    // Act
    reviewService.partialUpdateReview(1, updates);

    // Assert
    verify(cacheEntity).remove(1);
    verify(reviewRepository).findById(1);
    verify(reviewRepository).save(isA(Review.class));
  }

  /**
   * Method under test: {@link ReviewService#partialUpdateReview(Integer, Review)}
   */
  @Test
  void testPartialUpdateReview2() {
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

    Review review = new Review();
    review.setAnime(anime);
    review.setGrade((byte) 'A');
    review.setId(1);
    review.setText("Text");
    review.setTitle("Dr");
    Optional<Review> ofResult = Optional.of(review);
    when(reviewRepository.findById(Mockito.<Integer>any())).thenReturn(ofResult);
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    Anime anime2 = new Anime();
    anime2.setEndDate(mock(Date.class));
    anime2.setId(1);
    anime2.setNominations(new HashSet<>());
    anime2.setReviewCount(3);
    anime2.setReviews(new HashSet<>());
    anime2.setStartDate(mock(Date.class));
    anime2.setTitle("Dr");
    anime2.setTotalRating(1);

    Review updates = new Review();
    updates.setAnime(anime2);
    updates.setGrade((byte) 'A');
    updates.setId(1);
    updates.setText("Text");
    updates.setTitle("Dr");

    // Act and Assert
    assertThrows(ResourceNotFoundException.class,
        () -> reviewService.partialUpdateReview(1, updates));
    verify(cacheEntity).remove(1);
    verify(reviewRepository).findById(1);
  }

  /**
   * Method under test: {@link ReviewService#deleteReview(Integer)}
   */
  @Test
  void testDeleteReview() {
    // Arrange
    doNothing().when(reviewRepository).deleteById(Mockito.<Integer>any());
    doNothing().when(cacheEntity).remove(Mockito.<Integer>any());

    // Act
    reviewService.deleteReview(1);

    // Assert that nothing has changed
    verify(cacheEntity).remove(1);
    verify(reviewRepository).deleteById(1);
  }

  /**
   * Method under test: {@link ReviewService#deleteReview(Integer)}
   */
  @Test
  void testDeleteReview2() {
    // Arrange
    doThrow(new ResourceNotFoundException("An error occurred")).when(cacheEntity)
        .remove(Mockito.<Integer>any());

    // Act and Assert
    assertThrows(ResourceNotFoundException.class, () -> reviewService.deleteReview(1));
    verify(cacheEntity).remove(1);
  }
}
