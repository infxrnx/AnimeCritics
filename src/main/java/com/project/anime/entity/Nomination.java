package com.project.anime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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

    public Nomination(){

    }

    public Nomination(String name){
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Anime> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Anime> candidates) {
        this.candidates = candidates;
    }

    public void addCandidates(Anime anime){this.candidates.add(anime);}
}
