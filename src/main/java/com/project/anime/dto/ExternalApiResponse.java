package com.project.anime.dto;

public class ExternalApiResponse {

    private String title;

    private Integer  episodes;

    private String status;

    public String getTitle() {
        return title;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public ExternalApiResponse(String title, Integer episodes, String status){
        this.title = title;
        this.episodes = episodes;
        this.status = status;
    }
}

