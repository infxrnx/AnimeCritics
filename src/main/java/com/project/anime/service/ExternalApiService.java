package com.project.anime.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.anime.dto.externalapi.ExternalApiRequest;
import com.project.anime.dto.externalapi.ExternalApiResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ExternalApiService {
    RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public ExternalApiService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public ExternalApiResponse getAnimeByTitle(ExternalApiRequest request){
        try {
            restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            String url = "https://api.jikan.moe/v4/anime?" + "q=" + request.getTitle();
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            Integer id = jsonNode.get("data").get(0).get("mal_id").asInt();
            url = "https://api.jikan.moe/v4/anime/" + id + "/relations";
            response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            System.out.print(response);
            return new ExternalApiResponse(jsonNode.get("data").get(0).get("titles").get(0).get("title").asText(),
                                            jsonNode.get("data").get(0).get("episodes").asInt(),
                                            jsonNode.get("data").get(0).get("status").asText());
        } catch (Exception exception){
            return null;
        }
    }
}
