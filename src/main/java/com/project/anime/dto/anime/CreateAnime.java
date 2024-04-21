package com.project.anime.dto.anime;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CreateAnime {
  private String title;
  private Date startDate;
  private Date endDate;
}
