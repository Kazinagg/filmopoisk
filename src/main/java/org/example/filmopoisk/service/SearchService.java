package org.example.filmopoisk.service;

import org.example.filmopoisk.client.KinopoiskApiV21Client;
import org.example.filmopoisk.entity.Film;
import org.example.filmopoisk.entity.FilmSearchResponse;
import org.example.filmopoisk.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    private final FilmRepository filmRepository;
    private final KinopoiskApiV21Client kinopoiskApiV21Client;
    private final FilmService filmService;

    @Autowired
    public SearchService(FilmRepository filmRepository, KinopoiskApiV21Client kinopoiskApiV21Client, FilmService filmService) {
        this.filmRepository = filmRepository;
        this.kinopoiskApiV21Client = kinopoiskApiV21Client;
        this.filmService = filmService;
    }

    public List<Film> searchFilmsByKeyword(String keyword) {
        log.info("Поиск фильмов по ключевому слову: {}", keyword);
        List<Film> films = filmRepository.findByNameRuContainingIgnoreCase(keyword);
        if (films.size() >= 20) {
            log.info("Найдено {} фильмов в локальной базе данных", films.size());
            return films;
        } else {
            try {
                FilmSearchResponse searchResponse = kinopoiskApiV21Client.searchFilmsByKeyword(keyword, 1);
                if (searchResponse != null && searchResponse.getFilms() != null) {
                    log.info("Успешный ответ от API");
                    List<Film> apiFilms = searchResponse.getFilms().stream()
                            .map(filmItem -> {
                                log.info("Обработка filmId: {}", filmItem.getFilmId());
                                return filmService.findFilmByIdOrFetchFromApi(filmItem.getFilmId()).orElse(null);
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    films.addAll(apiFilms);
                    log.info("Всего найдено фильмов: {}", films.size());
                } else {
                    log.error("Ошибка при получении ответа от API: пустой ответ");
                }
            } catch (Exception e) {
                log.error("Ошибка при обращении к API", e);
            }
            return films;
        }
    }
}