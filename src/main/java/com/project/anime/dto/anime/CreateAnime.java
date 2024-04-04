package com.project.anime.dto.anime;

import lombok.Data;

import java.sql.Date;


@Data
public class CreateAnime {
    private String title;
    private Date startDate;
    private Date endDate;
}
