package com.project.anime.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
public class Anime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String title;

  private Date startDate;

  private Date endDate;

  private Integer totalRating;

  private Integer reviewCount;

  @JsonIgnoreProperties({"anime"})
  @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL)
  @ToString.Exclude
  private Set<Review> reviews = new HashSet<>();

  @ManyToMany(mappedBy = "candidates")
  @ToString.Exclude
  private Set<Nomination> nominations = new HashSet<>();

  public Anime(String title, Date startDate, Date endDate) {
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.totalRating = 0;
    this.reviewCount = 0;
  }

  public void addReview(Review review) {
    reviews.add(review);
  }

  public void addNomination(Nomination nomination) {
    nominations.add(nomination);
  }
}
