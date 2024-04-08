package com.project.anime.dto.externalapi;

public class ExternalApiResponse {

  private String title;

  private Integer episodes;

  private String status;

  public ExternalApiResponse(String title, Integer episodes, String status) {
    this.title = title;
    this.episodes = episodes;
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getEpisodes() {
    return episodes;
  }

  public void setEpisodes(Integer episodes) {
    this.episodes = episodes;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}

