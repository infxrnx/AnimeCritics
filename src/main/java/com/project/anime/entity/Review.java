package com.project.anime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String text;

    private Byte grade;

    @JsonIgnoreProperties({"reviews", "totalRating", "reviewCount", "startDate", "endDate", "nominations"})
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="anime_id")
    private Anime anime;

    public Review(String title, String text, Byte grade) {
        this.title = title;
        this.text = text;
        this.grade = grade;
    }


    public Review() {

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Byte getGrade() {
        return grade;
    }

    public void setGrade(Byte animationGrade) {
        this.grade = animationGrade;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }
}
