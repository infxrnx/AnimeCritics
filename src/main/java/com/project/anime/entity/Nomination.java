package com.project.anime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Nomination {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @JsonIgnoreProperties({"reviews", "totalRating", "reviewCount", "startDate", "endDate", "nominations"})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "nomination_anime",
    joinColumns = {@JoinColumn(name="nomination_id")},
    inverseJoinColumns = {@JoinColumn(name="anime_id")})
    private List<Anime> candidates = new ArrayList<>();

    public Nomination(String name){
        this.name = name;
    }

    public void addCandidates(Anime anime){this.candidates.add(anime);}
}
