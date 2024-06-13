package org.example.filmopoisk.service;

import org.example.filmopoisk.entity.Film;
import org.example.filmopoisk.entity.FilmSearchResponse;
import org.example.filmopoisk.repository.FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private static final Logger log = LoggerFactory.getLogger(SearchService.class);

    private final FilmRepository filmRepository;
    private final RestTemplate restTemplate;
    private final FilmService filmService;

    @Autowired
    public SearchService(FilmRepository filmRepository, RestTemplate restTemplate, FilmService filmService) {
        this.filmRepository = filmRepository;
        this.restTemplate = restTemplate;
        this.filmService = filmService;
    }

    public List<Film> searchFilmsByKeyword(String keyword) {
        log.info("Поиск фильмов по ключевому слову: {}", keyword);
        List<Film> films = filmRepository.findByNameRuContainingIgnoreCase(keyword);
        if (films.size() >= 20) {
            log.info("Найдено {} фильмов в локальной базе данных", films.size());
            return films;
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-KEY", "aa19d895-3fc5-4792-8747-fbd7b7edf851"); // Установите ваш действующий ключ API здесь
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            String searchApiUrl = "https://kinopoiskapiunofficial.tech/api/v2.1/films/search-by-keyword?keyword=" + keyword + "&page=1";
            log.info("Отправка запроса к внешнему API: {}", searchApiUrl);
            try {
                ResponseEntity<FilmSearchResponse> searchResponse = restTemplate.exchange(
                        searchApiUrl, HttpMethod.GET, entity, FilmSearchResponse.class);

                if (searchResponse.getStatusCode() == HttpStatus.OK && searchResponse.getBody() != null) {
                    log.info("Успешный ответ от API");
                    List<Film> apiFilms = searchResponse.getBody().getFilms().stream()
                            .map(filmItem -> {
                                log.info("Обработка filmId: {}", filmItem.getFilmId());
                                return filmService.findFilmByIdOrFetchFromApi(filmItem.getFilmId()).orElse(null);
                            })
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    films.addAll(apiFilms);
                    log.info("Всего найдено фильмов: {}", films.size());
                } else {
                    log.error("Ошибка при получении ответа от API: статус {}", searchResponse.getStatusCode());
                }
            } catch (Exception e) {
                log.error("Ошибка при обращении к API 66", e);
            }
            return films;
        }
    }
}
