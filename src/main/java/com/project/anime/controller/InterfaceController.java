package com.project.anime.controller;

import static java.lang.Thread.sleep;

import com.project.anime.dto.review.CreateReview;
import com.project.anime.entity.Anime;
import com.project.anime.entity.Nomination;
import com.project.anime.service.AnimeService;
import com.project.anime.service.InterfaceService;
import com.project.anime.service.NominationService;
import com.project.anime.service.ReviewService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/")
public class InterfaceController {
  private final InterfaceService interfaceService;
  private final AnimeService animeService;
  private final ReviewService reviewService;
  private final NominationService nominationService;

  public InterfaceController(InterfaceService interfaceService, AnimeService animeService,
                             ReviewService reviewService,
                             NominationService nominationService) {
    this.interfaceService = interfaceService;
    this.animeService = animeService;
    this.reviewService = reviewService;
    this.nominationService = nominationService;
  }

  @PostMapping("/reviews")
  public String getReviews(Model model, @RequestBody CreateReview review){
    reviewService.createReview(review);
    model.addAttribute("reviews", reviewService.getReviewsByAnimeId(review.getAnimeId()));
    return "reviews";
  }
  @GetMapping("/anime")
  public String getOneAnime(Model model, @RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "page", required = false) Integer page) {
    int animePerPage = 20;
    List<Anime> anime;
    if (title == null || title.isEmpty()){
      anime = animeService.getAllAnime();
    }
    else {
      anime = animeService.getAnimeByTitle(title);
    }
    Integer totalPages = (int) Math.ceil((double) anime.size() / animePerPage);
    if (page == null || page < 1 || page > totalPages){
      page = 1;
    }
    if (anime.size() == 1){
      model.addAttribute("anime", anime.get(0));
      model.addAttribute("reviews", anime.get(0).getReviews());
      return "animePage";
    }
    model.addAttribute("anime", anime);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", totalPages);
    model.addAttribute("searchTitle", title);
    return "mainPage";
  }
  @GetMapping()
  public String mainPage(Model model){
    try {
      Integer animePerPage = 20;
      List<Anime> anime = animeService.getAllAnime();
      Integer totalPages = animeService.totalPages(animePerPage);
      model.addAttribute("anime", anime);
      model.addAttribute("currentPage", 1);
      model.addAttribute("totalPages", totalPages);
      return "mainPage";
    } catch (Exception e) {
      return "error";
    }
  }
  @GetMapping("/nominations")
  public String getNominations(Model model){
    try {
      List<Nomination> nominations = nominationService.getAllNominations();

      model.addAttribute("nominations", nominations);

      return "nominationsPage";
    } catch (Exception e) {
      return "error";
    }
  }
}
