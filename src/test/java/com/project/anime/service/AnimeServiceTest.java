package com.project.anime.service;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.project.anime.aop.exception.InvalidRequestException;
import com.project.anime.cache.CacheEntity;
import com.project.anime.dto.anime.CreateAnime;
import com.project.anime.entity.Anime;
import com.project.anime.repository.AnimeRepository;
import java.sql.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {
  @InjectMocks
  private AnimeService animeService;
  @Mock
  private AnimeRepository animeRepository;
  @Mock
  private CacheEntity<Integer, Anime> cache;

  @Test
  void testCreateAnime_Success() {
    CreateAnime anime = new CreateAnime("title", new Date(1), new Date(10000L));
    animeService.createAnime(anime);
    verify(animeRepository, times(1)).save(new Anime(anime.getTitle(), anime.getStartDate(), anime.getEndDate()));
  }
  @Test
  void testCreateAnime_Failure(){
    CreateAnime animeDto = new CreateAnime("title", new Date(1), new Date(10000L));
    Anime anime = new Anime("title", new Date(1), new Date(10000L));

    doThrow(new RuntimeException()).when(animeRepository).save(anime);

    assertThrows(InvalidRequestException.class, () -> animeService.createAnime(animeDto));
  }
}
