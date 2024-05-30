package com.project.anime.dto.anime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseAnime {
  public ResponseAnime(ParseAnime parseAnime, Double rating, Integer reviewsCount) {
    this.title = parseAnime.getTitle();
    this.startDate = parseAnime.getStartDate();
    this.endDate = parseAnime.getEndDate();
    this.imageUrl = parseAnime.getImageUrl();
    this.episodes = parseAnime.getEpisodes();
    this.status = parseAnime.getStatus();
    this.ageRating = parseAnime.getAgeRating();
    this.rating = rating;
    this.reviewsCount = reviewsCount;
    this.description = parseAnime.getDescription();
    this.backgroundUrl = parseAnime.getBackgroundUrl();
  }
  private String title;
  private String startDate;
  private String endDate;
  private String imageUrl;
  private Integer episodes;
  private String status;
  private String ageRating;
  private Double rating;
  private Integer reviewsCount;
  private String description;
  private String backgroundUrl;
}
