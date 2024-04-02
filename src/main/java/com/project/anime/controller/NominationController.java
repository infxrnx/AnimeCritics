package com.project.anime.controller;

import com.project.anime.dto.nomination.CreateNomination;
import com.project.anime.entity.Nomination;
import com.project.anime.service.NominationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nominations")
public class NominationController {
    private final NominationService nominationService;

    public NominationController(NominationService nominationService) {
        this.nominationService = nominationService;
    }
    @PostMapping
    public ResponseEntity<Void> createNomination(@RequestBody CreateNomination review){
        nominationService.createNomination(review);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Nomination>> getAllNominations(){
        return ResponseEntity.ok(nominationService.getAllNominations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Nomination> getNominationById(@PathVariable Integer id){
        return ResponseEntity.of(nominationService.getNominationById(id)); //404 if not present()
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateNomination(@PathVariable Integer id, Nomination newNomination){
        nominationService.updateNomination(id, newNomination);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> partialUpdateNomination(@PathVariable Integer id, @RequestBody Nomination updates){
        nominationService.partialUpdateNomination(id, updates);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNomination(@PathVariable Integer id){
        nominationService.deleteNomination(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{nominationId}/anime/{animeId}")
    public ResponseEntity<Void> addAnimeToNomination(@PathVariable Integer nominationId, @PathVariable Integer animeId) {
        nominationService.addAnimeToNomination(nominationId, animeId);
        return ResponseEntity.ok().build();
    }
}
