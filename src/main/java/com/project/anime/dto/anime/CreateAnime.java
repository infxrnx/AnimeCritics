package com.project.anime.dto.anime;

import java.sql.Date;
import lombok.Data;


@Data
public class CreateAnime {
    private String title;
    private Date startDate;
    private Date endDate;
}
