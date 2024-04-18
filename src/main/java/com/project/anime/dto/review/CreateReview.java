package com.project.anime.dto.review;


import lombok.Data;

@Data
public class CreateReview {
  private String title;
  private String text;
  private Byte grade;
  private Integer animeId;
}
