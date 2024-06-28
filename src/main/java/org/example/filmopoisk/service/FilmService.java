package org.example.filmopoisk.service;

import org.example.filmopoisk.client.KinopoiskApiV22Client;
import org.example.filmopoisk.repository.FilmRepository;
import org.example.filmopoisk.entity.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;
    private final KinopoiskApiV22Client kinopoiskApiV22Client;

    @Autowired
    public FilmService(FilmRepository filmRepository, KinopoiskApiV22Client kinopoiskApiV22Client) {
        this.filmRepository = filmRepository;
        this.kinopoiskApiV22Client = kinopoiskApiV22Client;
    }

    public List<Film> getRandomFilms() {
        return filmRepository.random20();
    }


    public Optional<Film> findFilmByIdOrFetchFromApi(int id) {
        return filmRepository.findById(id)
                .or(() -> fetchFilmFromApiAndSave(id));
    }


    private Optional<Film> fetchFilmFromApiAndSave(int id) {
        try {
            Film film = kinopoiskApiV22Client.getFilmById(id);
            filmRepository.save(film);
            return Optional.of(film);
        } catch (Exception e) {
            // Обработка исключений, например, если фильм не найден
            return Optional.empty();
        }
    }

}