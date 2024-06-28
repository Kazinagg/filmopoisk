package org.example.filmopoisk.service;

import org.example.filmopoisk.client.KinopoiskApiV21Client;
import org.example.filmopoisk.entity.Film;
import org.example.filmopoisk.entity.FilmSearchResponse;
import org.example.filmopoisk.repository.FilmRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private KinopoiskApiV21Client kinopoiskApiV21Client;

    @Mock
    private FilmService filmService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void searchFilmsByKeyword_FoundInDatabase() {
        String keyword = "test";
        List<Film> mockFilms = Collections.nCopies(20, new Film());
        when(filmRepository.findByNameRuContainingIgnoreCase(keyword)).thenReturn(mockFilms);

        List<Film> films = searchService.searchFilmsByKeyword(keyword);

        assertEquals(20, films.size(), "Expected 20 films in the result");
        verify(filmRepository, times(1)).findByNameRuContainingIgnoreCase(keyword);
        verifyNoInteractions(kinopoiskApiV21Client, filmService);
    }

    @Test
    void searchFilmsByKeyword_NotFoundInDatabase_FoundInApi() {
        String keyword = "test";
        List<Film> databaseFilms = new ArrayList<>(Collections.nCopies(10, new Film()));
        FilmSearchResponse mockSearchResponse = createMockSearchResponse(1);
        Film mockFilm = createMockFilm(1);

        when(filmRepository.findByNameRuContainingIgnoreCase(keyword)).thenReturn(databaseFilms);
        when(kinopoiskApiV21Client.searchFilmsByKeyword(keyword, 1)).thenReturn(mockSearchResponse);
        when(filmService.findFilmByIdOrFetchFromApi(1)).thenReturn(Optional.of(mockFilm));

        List<Film> films = searchService.searchFilmsByKeyword(keyword);

        assertEquals(11, films.size(), "Expected 11 films in the result");
        verify(filmRepository, times(1)).findByNameRuContainingIgnoreCase(keyword);
        verify(kinopoiskApiV21Client, times(1)).searchFilmsByKeyword(keyword, 1);
        verify(filmService, times(1)).findFilmByIdOrFetchFromApi(1);
    }



    @Test
    void searchFilmsByKeyword_NotFoundInDatabaseAndApi() {
        String keyword = "test";
        when(filmRepository.findByNameRuContainingIgnoreCase(keyword)).thenReturn(Collections.emptyList());
        FilmSearchResponse mockSearchResponse = new FilmSearchResponse();
        mockSearchResponse.setFilms(Collections.emptyList());
        when(kinopoiskApiV21Client.searchFilmsByKeyword(keyword, 1)).thenReturn(mockSearchResponse);

        List<Film> films = searchService.searchFilmsByKeyword(keyword);

        assertTrue(films.isEmpty(), "Expected no films in the result");
        verify(filmRepository, times(1)).findByNameRuContainingIgnoreCase(keyword);
        verify(kinopoiskApiV21Client, times(1)).searchFilmsByKeyword(keyword, 1);
        verifyNoInteractions(filmService);
    }

    @Test
    void searchFilmsByKeyword_NotFoundInDatabase_ApiError() {
        String keyword = "test";
        when(filmRepository.findByNameRuContainingIgnoreCase(keyword)).thenReturn(Collections.emptyList());
        when(kinopoiskApiV21Client.searchFilmsByKeyword(keyword, 1)).thenThrow(new RuntimeException("API error"));

        List<Film> films = searchService.searchFilmsByKeyword(keyword);

        assertTrue(films.isEmpty(), "Expected no films in the result");
        verify(filmRepository, times(1)).findByNameRuContainingIgnoreCase(keyword);
        verify(kinopoiskApiV21Client, times(1)).searchFilmsByKeyword(keyword, 1);
        verifyNoInteractions(filmService);
    }


    @Test
    void searchFilmsByKeyword_ApiNetworkError() {
        String keyword = "test";
        when(filmRepository.findByNameRuContainingIgnoreCase(keyword)).thenReturn(Collections.emptyList());
        when(kinopoiskApiV21Client.searchFilmsByKeyword(keyword, 1)).thenThrow(new RuntimeException("Network error"));

        List<Film> films = searchService.searchFilmsByKeyword(keyword);

        assertTrue(films.isEmpty(), "Expected no films in the result");
        verify(filmRepository, times(1)).findByNameRuContainingIgnoreCase(keyword);
        verify(kinopoiskApiV21Client, times(1)).searchFilmsByKeyword(keyword, 1);
        verifyNoInteractions(filmService);
    }

    @Test
    void searchFilmsByKeyword_ApiAuthorizationError() {
        String keyword = "test";
        when(filmRepository.findByNameRuContainingIgnoreCase(keyword)).thenReturn(Collections.emptyList());
        when(kinopoiskApiV21Client.searchFilmsByKeyword(keyword, 1)).thenThrow(new RuntimeException("Authorization error"));

        List<Film> films = searchService.searchFilmsByKeyword(keyword);

        assertTrue(films.isEmpty(), "Expected no films in the result");
        verify(filmRepository, times(1)).findByNameRuContainingIgnoreCase(keyword);
        verify(kinopoiskApiV21Client, times(1)).searchFilmsByKeyword(keyword, 1);
        verifyNoInteractions(filmService);
    }

    private FilmSearchResponse createMockSearchResponse(int filmId) {
        FilmSearchResponse response = new FilmSearchResponse();
        FilmSearchResponse.FilmItem filmItem = new FilmSearchResponse.FilmItem();
        filmItem.setFilmId(filmId);
        response.setFilms(Collections.singletonList(filmItem));
        return response;
    }

    private Film createMockFilm(int kinopoiskId) {
        Film film = new Film();
        film.setKinopoiskId(kinopoiskId);
        return film;
    }

    private Film createMockFilmWithName(String name) {
        Film film = new Film();
        film.setNameRu(name);
        return film;
    }
}
