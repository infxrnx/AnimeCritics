package com.project.anime.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.anime.aop.annotation.Logging;
import com.project.anime.aop.exception.InvalidRequestException;
import com.project.anime.aop.exception.ResourceNotFoundException;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Nomination;
import com.project.anime.repository.AnimeRepository;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Logging
@Service
public class AnimeService {
  private final AnimeRepository animeRepository;
  public AnimeService(AnimeRepository animeRepository) {
    this.animeRepository = animeRepository;
  }
  private static final String[] seasons = {
      "Winter", "Winter", "Spring", "Spring", "Summer", "Summer",
      "Summer", "Summer", "Fall", "Fall", "Winter", "Winter"
  };
  public String getSeason( Date date ) {
    return seasons[ date.getMonth() ];
  }
  public Integer totalPages(int animePerPage) {
    return (int) Math.ceil((double) animeRepository.count() / animePerPage);
  }

  public List<Anime> getAnimeByTitle(String title){
    return animeRepository.findByTitleContainingIgnoreCase(title);
  }

  public void createAnime(String title) {
    if (animeRepository.findByTitle(title).isPresent()) {
      return;
    }
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      RestTemplate restTemplate = new RestTemplate();
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
      String url = "https://api.jikan.moe/v4/anime?" + "q=" + title;
      HttpEntity<String> entity = new HttpEntity<>(headers);

      ResponseEntity<String> response =
          restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
      JsonNode jsonNode = objectMapper.readTree(response.getBody()).get("data").get(0);

      Date startDate = new Date(jsonNode.get("aired").get("prop").get("from").get("year").asInt() - 1900,
          jsonNode.get("aired").get("prop").get("from").get("month").asInt() - 1,
          jsonNode.get("aired").get("prop").get("from").get("day").asInt());
      Date endDate = startDate;
      if (jsonNode.get("aired").hasNonNull("to")){
          endDate = new Date(jsonNode.get("aired").get("prop").get("to").get("year").asInt() - 1900,
              jsonNode.get("aired").get("prop").get("to").get("month").asInt() - 1,
              jsonNode.get("aired").get("prop").get("to").get("day").asInt());
      }
      List<String> synonyms = new ArrayList<>();
      for (final JsonNode objNode : jsonNode.get("title_synonyms")) {
        synonyms.add(objNode.asText());
      }
      List<String> producers = new ArrayList<>();
      for (final JsonNode objNode : jsonNode.get("producers")) {
        producers.add(objNode.get("name").asText());
      }
      List<String> studios = new ArrayList<>();
      for (final JsonNode objNode : jsonNode.get("studios")) {
        studios.add(objNode.get("name").asText());
      }
      List<String> genres = new ArrayList<>();
      for (final JsonNode objNode : jsonNode.get("genres")) {
        genres.add(objNode.get("name").asText());
      }
      Anime anime = new Anime(
          jsonNode.get("title_english").asText(),
          startDate,
          endDate,
          jsonNode.get("images").get("jpg").get("image_url").asText(),
          jsonNode.get("episodes").asInt(),
          jsonNode.get("status").asText(),
          jsonNode.get("rating").asText(),
          jsonNode.get("title_japanese").asText(),
          String.join(", ", synonyms),
          jsonNode.get("type").asText(),
          Integer.parseInt(jsonNode.get("duration").asText().substring(0,2)),
          getSeason(startDate) + " " + (endDate.getYear() + 1900),
          String.join(", ", producers),
          String.join(", ", studios),
          String.join(", ", genres),
          jsonNode.get("synopsis").asText(),
          jsonNode.get("trailer").get("images").get("large_image_url").asText()
      );
      animeRepository.save(anime);
    } catch (Exception e){
      System.out.println(e.getMessage());
    }
  }

  public List<Anime> getAllAnime() {
    List<Anime> list = animeRepository.findAll();
    return list;
  }

  public Anime getAnimeById(Integer id) {
    return animeRepository.findById(id).orElseThrow(
          () -> new ResourceNotFoundException("Anime (with id = " + id + ") not found"));
  }

  public void updateAnime(Integer id, Anime newAnime) {
    try {
      newAnime.setId(id);
      animeRepository.save(newAnime);
    } catch (Exception e) {
      throw new InvalidRequestException(e.getMessage());
    }
  }

  public void partialUpdateAnime(Integer id, Anime updates) {
    Optional<Anime> optionalAnime = animeRepository.findById(id);
    if (optionalAnime.isEmpty()) {
      throw new InvalidRequestException("Anime was not found.");
    }
    Anime anime = optionalAnime.get();
    if (updates.getTitle() == null && updates.getStartDate() == null
        && updates.getEndDate() == null) {
      throw new InvalidRequestException("Updates were not provided.");
    }
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

  public void deleteAnime(Integer id) {
    Anime anime =
        animeRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Anime (with id = " + id + ") not found"));
    for (Nomination nomination : anime.getNominations()) {
      nomination.getCandidates().remove(anime);
    }
    anime.getNominations().clear();
    animeRepository.deleteById(id);
  }

}