package com.project.anime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
<<<<<<< Updated upstream
import jakarta.persistence.*;
=======
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
>>>>>>> Stashed changes
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String title;

  private String text;

  private Byte grade;

  @JsonIgnoreProperties({"reviews", "totalRating", "reviewCount", "startDate", "endDate",
      "nominations"})
  @ManyToOne()
  @JoinColumn(name = "anime_id")
  private Anime anime;

<<<<<<< Updated upstream
    public Review(String title, String text, Byte grade){
        this.title = title;
        this.text = text;
        this.grade = grade;
    }
=======
  public Review(String title, String text, Byte grade) {
    this.title = title;
    this.text = text;
    this.grade = grade;
  }
>>>>>>> Stashed changes
}
