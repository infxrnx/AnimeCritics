package com.project.anime.repository;

import com.project.anime.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
  List<Review> findAllByTitle(String title);
}
