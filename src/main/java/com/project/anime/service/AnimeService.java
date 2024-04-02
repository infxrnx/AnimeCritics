package com.project.anime.service;

import com.project.anime.dto.anime.CreateAnime;
import com.project.anime.entity.Anime;
import com.project.anime.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {
    private final AnimeRepository animeRepository;

    public AnimeService(AnimeRepository animeRepository){
        this.animeRepository = animeRepository;
    }

    public void createAnime(CreateAnime anime){
        Anime newAnime = new Anime(anime.getTitle(), anime.getStartDate(), anime.getEndDate());
        animeRepository.save(newAnime);
    }

    public List<Anime> getAllAnime(){
        return animeRepository.findAll();
    }

    public Optional<Anime> getAnimeById(Integer id){
        return animeRepository.findById(id);
    }

    public void updateAnime(Integer id, Anime newAnime){
        newAnime.setId(id);
        animeRepository.save(newAnime);
    }

    public void partialUpdateAnime(Integer id, Anime updates){
        Anime anime = animeRepository.findById(id).orElseThrow(() -> new RuntimeException("Anime not found"));

        if (updates.getTitle() != null) {
            anime.setTitle(updates.getTitle());
        }
        if (updates.getStartDate() != null) {
            anime.setStartDate(updates.getStartDate());
        }
        if (updates.getEndDate() != null) {
            anime.setEndDate(updates.getEndDate());
        }

        animeRepository.save(anime);
    }

    public void deleteAnime(Integer id){
        animeRepository.deleteById(id);
    }

}