package com.project.anime.service;

import com.project.anime.entity.Critics;
import com.project.anime.repository.CriticsRepository;
import org.springframework.stereotype.Service;

@Service
public class CriticsService {

    public CriticsService(CriticsRepository criticsRepository){
        this.criticsRepository = criticsRepository;
    }
    private final CriticsRepository criticsRepository;

    public String postCritics(Critics critics){
        criticsRepository.save(critics);
        return "saved";
    }

    public Iterable<Critics> getCritics(String title){
        if (title != null) {
            return criticsRepository.findAllByTitle(title);
        }
        else {
            return criticsRepository.findAll();
        }
    }

}
