package com.project.anime.dto;

public class ExternalApiRequest {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ExternalApiRequest(String title){
        this.title = title;
    }
}
