package com.project.anime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Nomination {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private String name;

  @JsonIgnoreProperties({"reviews", "totalRating", "reviewCount", "startDate", "endDate",
      "nominations"})
  @ManyToMany()
  @JoinTable(name = "nomination_anime",
      joinColumns = {@JoinColumn(name = "nomination_id")},
      inverseJoinColumns = {@JoinColumn(name = "anime_id")})
  private List<Anime> candidates = new ArrayList<>();

  public Nomination(String name) {
    this.name = name;
  }

  public void addCandidates(Anime anime) {
    this.candidates.add(anime);
  }
}
