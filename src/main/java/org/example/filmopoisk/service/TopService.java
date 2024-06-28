package org.example.filmopoisk.service;

import org.example.filmopoisk.client.KinopoiskApiV22Client;
import org.example.filmopoisk.entity.FilmCollectionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopService {
    private final KinopoiskApiV22Client kinopoiskApiV22Client;

    @Autowired
    public TopService(KinopoiskApiV22Client kinopoiskApiV22Client) {
        this.kinopoiskApiV22Client = kinopoiskApiV22Client;
    }

    public Optional<FilmCollectionResponse> fetchTopFilmsFromApi(int page) {
        try {
            return Optional.ofNullable(kinopoiskApiV22Client.getTopPopularFilms(page));
        } catch (Exception e) {
            // Логирование ошибки
            return Optional.empty();
        }
    }
}