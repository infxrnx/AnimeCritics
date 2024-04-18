package com.project.anime.controller;

import com.project.anime.dto.nomination.CreateNomination;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Nomination;
import com.project.anime.service.NominationService;
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
@RequestMapping("/api/nominations")
public class NominationController {
  private static final String DELETE_SUCCESS_MESSAGE = "Deleted successfully!";
  private static final String UPDATE_SUCCESS_MESSAGE = "Updated successfully!";
  private static final String CREATE_SUCCESS_MESSAGE = "Created successfully!";
  private final NominationService nominationService;

  public NominationController(NominationService nominationService) {
    this.nominationService = nominationService;
  }

  @PostMapping
  public ResponseEntity<String> createNomination(@RequestBody CreateNomination review) {
    nominationService.createNomination(review);
    return new ResponseEntity<>(CREATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Nomination>> getAllNominations() {
    return new ResponseEntity<>(nominationService.getAllNominations(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Nomination> getNominationById(@PathVariable Integer id) {
    return new ResponseEntity<>(nominationService.getNominationById(id),
        HttpStatus.OK); //404 if not present()
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateNomination(@PathVariable Integer id,
                                                 Nomination newNomination) {
    nominationService.updateNomination(id, newNomination);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<String> partialUpdateNomination(@PathVariable Integer id,
                                                        @RequestBody Nomination updates) {
    nominationService.partialUpdateNomination(id, updates);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteNomination(@PathVariable Integer id) {
    nominationService.deleteNomination(id);
    return new ResponseEntity<>(DELETE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @PostMapping("/{nominationId}/anime/{animeId}")
  public ResponseEntity<String> addAnimeToNomination(@PathVariable Integer nominationId,
                                                     @PathVariable Integer animeId) {
    nominationService.addAnimeToNomination(nominationId, animeId);
    return new ResponseEntity<>(UPDATE_SUCCESS_MESSAGE, HttpStatus.OK);
  }

  @GetMapping("/filter/{nominationId}")
  public ResponseEntity<List<Anime>> findAnimeInNominationWithAverageRatingGreaterThanThreshold(
      @PathVariable Integer nominationId, @RequestParam Integer min) {
    return new ResponseEntity<>(
        nominationService.findAnimeInNominationWithAverageRatingGreaterThanThreshold(nominationId,
            min), HttpStatus.OK);
  }
}
