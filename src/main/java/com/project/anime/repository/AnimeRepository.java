package com.project.anime.repository;


import com.project.anime.entity.Anime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnimeRepository extends JpaRepository<Anime, Integer> {
  @Query("SELECT a FROM Anime a WHERE COALESCE(a.totalRating / a.reviewCount, 0) "
      + "< :maxRating AND COALESCE(a.totalRating / a.reviewCount, 0) > :minRating")
  List<Anime> findAnimeByAverageRatingBetween(@Param("minRating") Double minRating,
                                              @Param("maxRating") Double maxRating);
}
