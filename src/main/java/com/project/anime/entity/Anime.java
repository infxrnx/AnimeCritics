package com.project.anime.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
<<<<<<< Updated upstream
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

=======
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
>>>>>>> Stashed changes
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< Updated upstream
=======

@SuppressWarnings("ALL")
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    @JsonIgnoreProperties({"anime"})
    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany(mappedBy = "candidates")
    private List<Nomination> nominations = new ArrayList<>();

    public void addReview(Review review){
        reviews.add(review);
    }

    public void addNomination(Nomination nomination){
        nominations.add(nomination);
    }

    public Anime(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalRating = 0;
        this.reviewCount = 0;
    }
=======
  @JsonIgnoreProperties({"anime", "candidates"})
  @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Review> reviews = new ArrayList<>();

  @ManyToMany(mappedBy = "candidates", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Nomination> nominations = new ArrayList<>();

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
>>>>>>> Stashed changes
}
