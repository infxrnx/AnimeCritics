package com.project.anime.dto.anime;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SearchAnime {
  private String search;
  private Integer page;
}

