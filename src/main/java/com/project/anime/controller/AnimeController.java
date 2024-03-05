package com.project.anime.controller;

import com.project.anime.dto.ExternalApiRequest;
import com.project.anime.dto.ExternalApiResponse;
import com.project.anime.entity.Critics;
import com.project.anime.service.CriticsService;
import com.project.anime.service.ExternalApiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnimeController {
    private final ExternalApiService externalApiService;

    private final CriticsService criticsService;
    public AnimeController(ExternalApiService externalApiService, CriticsService criticsService){
        this.externalApiService = externalApiService;
        this.criticsService = criticsService;
    }

    @PostMapping("/critics")
    public String postCritics(@RequestParam String text,
                              @RequestParam String title){
        return criticsService.postCritics(new Critics(text, title));
    }

    @GetMapping("/critics")
    public Iterable<Critics> getCritics(@RequestParam(required = false) String title){
        return criticsService.getCritics(title);
    }



    @GetMapping("/anime/{animeTitle}")
    public ExternalApiResponse getAnimeByTitle(@PathVariable String animeTitle){
        return externalApiService.getAnimeByTitle(new ExternalApiRequest(animeTitle));
    }

}
