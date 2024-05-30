package com.project.anime.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
public class Anime {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String title;
  private Date startDate;
  private Date endDate;
  private String imageUrl;
  private Integer episodes;
  private String status;
  private String ageRating;
  private Double rating;
  private Integer totalRating;
  private Integer reviewsCount;
  private String titleJapanese;
  private String synonyms;
  private String type;
  private Integer duration;
  private String season;
  private String producers;
  private String studios;
  private String genres;
  @Column(length=1500)
  private String description;
  private String backgroundUrl;
  @CreationTimestamp
  private Date createdDate;

  @JsonIgnoreProperties()
  @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL)
  private Set<Review> reviews = new HashSet<>();

  @ManyToMany(mappedBy = "candidates")
  private Set<Nomination> nominations = new HashSet<>();

  public Anime(String title, Date startDate, Date endDate) {
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.totalRating = 0;
    this.reviewsCount = 0;
  }

  public Anime(String title, Date startDate, Date endDate, String imageUrl, Integer episodes,
               String status, String ageRating, String titleJapanese, String synonyms, String type,
               Integer duration, String season, String producers, String studios, String genres,
               String description, String backgroundUrl) {
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.imageUrl = imageUrl;
    this.episodes = episodes;
    this.status = status;
    this.ageRating = ageRating;
    this.rating = 0.0;
    this.totalRating = 0;
    this.reviewsCount = 0;
    this.titleJapanese = titleJapanese;
    this.synonyms = synonyms;
    this.type = type;
    this.duration = duration;
    this.season = season;
    this.producers = producers;
    this.studios = studios;
    this.genres = genres;
    this.description = description;
    this.backgroundUrl = backgroundUrl;
  }

  public void addReview(Review review) {
    reviews.add(review);
  }

  public void addNomination(Nomination nomination) {
    nominations.add(nomination);
  }
}