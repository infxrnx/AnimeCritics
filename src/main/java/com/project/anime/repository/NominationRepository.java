package com.project.anime.repository;

import com.project.anime.entity.Anime;
import com.project.anime.entity.Nomination;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NominationRepository extends JpaRepository<Nomination, Integer> {
  @Query("SELECT a FROM Anime a JOIN a.nominations n WHERE n.id = :nominationId "
      + "AND (a.reviewsCount > 0 AND a.totalRating / a.reviewsCount > :ratingThreshold)")
  List<Anime> findAnimeInNominationWithAverageRatingGreaterThanThreshold(
      @Param("nominationId") Integer nominationId,
      @Param("ratingThreshold") Integer ratingThreshold);
}
