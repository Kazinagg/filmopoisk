package org.example.filmopoisk.service;

import org.example.filmopoisk.entity.FilmCollectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class TopService {
    private final RestTemplate restTemplate;

    @Autowired
    public TopService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Optional<FilmCollectionResponse> fetchTopFilmsFromApi(int page) {
        return fetchTopFilmsFromApiAndAddFilms(page);
    }


    private Optional<FilmCollectionResponse> fetchTopFilmsFromApiAndAddFilms(int page) {
        String apiUrl = "https://kinopoiskapiunofficial.tech/api/v2.2/films/collections?type=TOP_POPULAR_ALL&page=" + page;
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "aa19d895-3fc5-4792-8747-fbd7b7edf851");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<FilmCollectionResponse> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<FilmCollectionResponse>() {});

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return Optional.of(response.getBody());
        } else {
            return Optional.empty();
        }
    }
}
