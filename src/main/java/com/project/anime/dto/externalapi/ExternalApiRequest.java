package com.project.anime.dto.externalapi;

public class ExternalApiRequest {

  private String title;

  public ExternalApiRequest(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
