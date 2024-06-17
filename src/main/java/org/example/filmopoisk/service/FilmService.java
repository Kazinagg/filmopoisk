package org.example.filmopoisk.service;

import org.example.filmopoisk.repository.FilmRepository;
import org.example.filmopoisk.entity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;

import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public FilmService(FilmRepository filmRepository, RestTemplate restTemplate) {
        this.filmRepository = filmRepository;
        this.restTemplate = restTemplate;
    }

    public List<Film> getRandomFilms(int quantity) {

        return filmRepository.random(quantity);
    }


    public Optional<Film> findFilmByIdOrFetchFromApi(int id) {
        return filmRepository.findById(id)
                .or(() -> fetchFilmFromApiAndSave(id));
    }


    private Optional<Film> fetchFilmFromApiAndSave(int id) {
        String apiUrl = "https://kinopoiskapiunofficial.tech/api/v2.2/films/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "aa19d895-3fc5-4792-8747-fbd7b7edf851");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Film> response = restTemplate.exchange(
                apiUrl, HttpMethod.GET, entity, Film.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Film film = response.getBody();
            filmRepository.save(film);
            return Optional.of(film);
        } else {
            return Optional.empty();
        }
    }

}


