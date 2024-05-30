package com.project.anime.controller;

import com.project.anime.service.RequestStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stats")
public class RequestStatsController {

  private final RequestStatsService requestStatsService;

  public RequestStatsController(RequestStatsService requestStatsService) {
    this.requestStatsService = requestStatsService;
  }

  @GetMapping
  public ResponseEntity<Integer> getRequestStats() {
    return ResponseEntity.ok(requestStatsService.getCount());
  }
}