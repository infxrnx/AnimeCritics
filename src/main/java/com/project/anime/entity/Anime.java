package com.project.anime.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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

    public Anime(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> revies) {
        this.reviews = revies;
    }

    public List<Nomination> getNominations() {
        return nominations;
    }

    public void setNominations(List<Nomination> nominations) {
        this.nominations = nominations;
    }


    public Anime(String title, Date startDate, Date endDate) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalRating = 0;
        this.reviewCount = 0;
    }
}
