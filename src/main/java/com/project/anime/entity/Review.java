package com.project.anime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String title;

  private String text;

  private Byte grade;

  @CreationTimestamp
  private Date createdDate;

  @JsonIgnoreProperties({"reviews", "totalRating", "reviewCount", "startDate", "endDate",
      "nominations"})
  @ManyToOne(cascade= CascadeType.PERSIST)
  @JoinColumn(name = "anime_id")
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Anime anime;

  public Review(String title, String text, Byte grade) {
    this.title = title;
    this.text = text;
    this.grade = grade;
  }
}
