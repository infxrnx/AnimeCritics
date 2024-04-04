package com.project.anime.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Anime {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private Date startDate;

    private Date endDate;

    private Integer totalRating;

    private Integer reviewCount;

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
}
