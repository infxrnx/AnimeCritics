package com.project.anime.controller;

import com.project.anime.dto.anime.CreateAnime;
import com.project.anime.entity.Anime;
import com.project.anime.service.AnimeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/anime")
public class AnimeController {
  private static final String DELETE_ERROR_MESSAGE = "Error while deleting!";
  private static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully!";
  private static final String UPDATE_ERROR_MESSAGE = "Error while updating!";
  private static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully!";
  private static final String GET_ERROR_MESSAGE = "Error while getting!";
  private static final String CREATE_ERROR_MESSAGE = "Error while creating!";
  private static final String CREATE_SUCCESS_MESSAGE = "Created successfully!";
  private final AnimeService animeService;

  public AnimeController(AnimeService animeService) {
    this.animeService = animeService;
  }

  @PostMapping
  public ResponseEntity<String> createAnime(@RequestBody CreateAnime newAnime) {
    animeService.createAnime(newAnime);
    return new ResponseEntity<>(CREATE_SUCCESS_MESSAGE, HttpStatus.NOT_ACCEPTABLE);
  }

  @GetMapping
  public ResponseEntity<List<Anime>> getAllAnime(
      @RequestParam(required = false) Double minRating,
      @RequestParam(required = false, defaultValue = "10")
      Double maxRating) {
    if (minRating != null && maxRating != null) {
      return new ResponseEntity<>(animeService.getAllAnimeWithCertainRating(minRating, maxRating),
          HttpStatus.OK);
    }
    return new ResponseEntity<>(animeService.getAllAnime(), HttpStatus.OK);
  }


  @GetMapping("/{id}")
  public ResponseEntity<Anime> getAnimeById(@PathVariable Integer id) {
    return new ResponseEntity<>(animeService.getAnimeById(id),
        HttpStatus.OK); //404 if not present()
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateAnime(@PathVariable Integer id, @RequestBody Anime newAnime) {
    animeService.updateAnime(id, newAnime);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<String> partialUpdateAnime(@PathVariable Integer id,
                                                   @RequestBody Anime updates) {
    animeService.partialUpdateAnime(id, updates);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteAnime(@PathVariable Integer id) {
    animeService.deleteAnime(id);
    return new ResponseEntity<>(DELETE_SUCCESS_MESSAGE, HttpStatus.OK);
  }
}
