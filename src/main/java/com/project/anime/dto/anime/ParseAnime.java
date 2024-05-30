package com.project.anime.dto.anime;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ParseAnime {
  private String title;
  private String startDate;
  private String endDate;
  private String imageUrl;
  private Integer episodes;
  private String status;
  private String ageRating;
  private String description;
  private String backgroundUrl;
}
