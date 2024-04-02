package com.project.anime.controller;

import com.project.anime.dto.anime.CreateAnime;

import com.project.anime.entity.Anime;
import com.project.anime.service.AnimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {

    private final AnimeService animeService;
    public AnimeController(AnimeService animeService){
        this.animeService = animeService;
    }
    @PostMapping
    public ResponseEntity<Void> createAnime(@RequestBody CreateAnime newAnime){
        animeService.createAnime(newAnime);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Anime>> getAllAnime(){
        return ResponseEntity.ok(animeService.getAllAnime());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anime> getAnimeById(@PathVariable Integer id){
        return ResponseEntity.of(animeService.getAnimeById(id)); //404 if not present()
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAnime(@PathVariable Integer id, @RequestBody Anime newAnime){
        animeService.updateAnime(id, newAnime);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partialUpdateAnime(@PathVariable Integer id, @RequestBody Anime updates){
        animeService.partialUpdateAnime(id, updates);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnime(@PathVariable Integer id){
        animeService.deleteAnime(id);
        return ResponseEntity.ok().build();
    }
}
