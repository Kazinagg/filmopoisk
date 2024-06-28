package org.example.filmopoisk.service;

import org.example.filmopoisk.client.KinopoiskApiV22Client;
import org.example.filmopoisk.entity.Film;
import org.example.filmopoisk.repository.FilmRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private FilmRepository filmRepository;

    @Mock
    private KinopoiskApiV22Client kinopoiskApiV22Client;

    @InjectMocks
    private FilmService filmService;

    @BeforeEach
    void setUp() {
        // Инициализация моков и других зависимостей перед каждым тестом
    }

    @AfterEach
    void tearDown() {
        // Очистка ресурсов после каждого теста (необязательно)
    }

    @Test
    void getRandomFilms() {
        List<Film> mockFilms = Collections.singletonList(new Film());
        when(filmRepository.random20()).thenReturn(mockFilms);
        List<Film> films = filmService.getRandomFilms();
        assertEquals(mockFilms, films);
        verify(filmRepository, times(1)).random20();
    }

    @Test
    void findFilmByIdOrFetchFromApi_FoundInDatabase() {
        int filmId = 1;
        Film mockFilm = new Film();
        mockFilm.setKinopoiskId(filmId);

        // Замокайте findById, чтобы он возвращал mockFilm
        when(filmRepository.findById(filmId)).thenReturn(Optional.of(mockFilm));

        Optional<Film> film = filmService.findFilmByIdOrFetchFromApi(filmId);

        assertTrue(film.isPresent());
        assertEquals(filmId, film.get().getKinopoiskId());
        verify(filmRepository, times(1)).findById(filmId);
        verify(kinopoiskApiV22Client, times(0)).getFilmById(anyInt());
    }

    @Test
    void findFilmByIdOrFetchFromApi_NotFoundInDatabase() {
        int filmId = 1;
        Film mockFilm = new Film();
        mockFilm.setKinopoiskId(filmId); // Установите ID для mockFilm

        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());
        when(kinopoiskApiV22Client.getFilmById(filmId)).thenReturn(mockFilm);

        Optional<Film> film = filmService.findFilmByIdOrFetchFromApi(filmId);

        assertTrue(film.isPresent());
        assertEquals(filmId, film.get().getKinopoiskId());
        verify(filmRepository, times(1)).findById(filmId);
        verify(kinopoiskApiV22Client, times(1)).getFilmById(filmId);
        verify(filmRepository, times(1)).save(any(Film.class));
    }

    @Test
    void findFilmByIdOrFetchFromApi_ApiError() {
        int filmId = 1;
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());
        when(kinopoiskApiV22Client.getFilmById(filmId)).thenThrow(new RuntimeException("API error"));
        Optional<Film> film = filmService.findFilmByIdOrFetchFromApi(filmId);
        assertFalse(film.isPresent());
        verify(filmRepository, times(1)).findById(filmId);
        verify(kinopoiskApiV22Client, times(1)).getFilmById(filmId);
        verify(filmRepository, times(0)).save(any(Film.class));
    }





    @Test
    void getRandomFilms_EmptyList() {
        when(filmRepository.random20()).thenReturn(Collections.emptyList());
        List<Film> films = filmService.getRandomFilms();
        assertTrue(films.isEmpty());
        verify(filmRepository, times(1)).random20();
    }

    @Test
    void findFilmByIdOrFetchFromApi_SaveFilm() {
        int filmId = 1;
        Film mockFilm = new Film();
        mockFilm.setKinopoiskId(filmId);

        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());
        when(kinopoiskApiV22Client.getFilmById(filmId)).thenReturn(mockFilm);

        filmService.findFilmByIdOrFetchFromApi(filmId);

        verify(filmRepository, times(1)).save(mockFilm);
    }

    @Test
    void findFilmByIdOrFetchFromApi_ApiNetworkError() {
        int filmId = 1;
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());
        when(kinopoiskApiV22Client.getFilmById(filmId)).thenThrow(new RuntimeException("Network error"));

        Optional<Film> film = filmService.findFilmByIdOrFetchFromApi(filmId);

        assertFalse(film.isPresent());
        verify(filmRepository, times(1)).findById(filmId);
        verify(kinopoiskApiV22Client, times(1)).getFilmById(filmId);
        verify(filmRepository, times(0)).save(any(Film.class));
    }

    @Test
    void findFilmByIdOrFetchFromApi_ApiAuthorizationError() {
        int filmId = 1;
        when(filmRepository.findById(filmId)).thenReturn(Optional.empty());
        when(kinopoiskApiV22Client.getFilmById(filmId)).thenThrow(new RuntimeException("Authorization error"));

        Optional<Film> film = filmService.findFilmByIdOrFetchFromApi(filmId);

        assertFalse(film.isPresent());
        verify(filmRepository, times(1)).findById(filmId);
        verify(kinopoiskApiV22Client, times(1)).getFilmById(filmId);
        verify(filmRepository, times(0)).save(any(Film.class));
    }
}