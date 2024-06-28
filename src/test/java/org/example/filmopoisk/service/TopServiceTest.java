package org.example.filmopoisk.service;

import org.example.filmopoisk.client.KinopoiskApiV22Client;
import org.example.filmopoisk.entity.FilmCollectionResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopServiceTest {

    @Mock
    private KinopoiskApiV22Client kinopoiskApiV22Client;

    @InjectMocks
    private TopService topService;

    @BeforeEach
    void setUp() {
        // Инициализация моков и других зависимостей перед каждым тестом
    }

    @AfterEach
    void tearDown() {
        // Очистка ресурсов после каждого теста (необязательно)
    }

    @Test
    void fetchTopFilmsFromApi_Success() {
        int page = 1;
        FilmCollectionResponse mockResponse = new FilmCollectionResponse();
        when(kinopoiskApiV22Client.getTopPopularFilms(page)).thenReturn(mockResponse);

        Optional<FilmCollectionResponse> response = topService.fetchTopFilmsFromApi(page);

        assertTrue(response.isPresent());
        assertEquals(mockResponse, response.get());
        verify(kinopoiskApiV22Client, times(1)).getTopPopularFilms(page);
    }

    @Test
    void fetchTopFilmsFromApi_Error() {
        int page = 1;
        when(kinopoiskApiV22Client.getTopPopularFilms(page)).thenThrow(new RuntimeException("API error"));

        Optional<FilmCollectionResponse> response = topService.fetchTopFilmsFromApi(page);

        assertFalse(response.isPresent());
        verify(kinopoiskApiV22Client, times(1)).getTopPopularFilms(page);
    }



    @Test
    void fetchTopFilmsFromApi_EmptyResponse() {
        int page = 1;
        when(kinopoiskApiV22Client.getTopPopularFilms(page)).thenReturn(null);

        Optional<FilmCollectionResponse> response = topService.fetchTopFilmsFromApi(page);

        assertFalse(response.isPresent());
        verify(kinopoiskApiV22Client, times(1)).getTopPopularFilms(page);
    }

    @Test
    void fetchTopFilmsFromApi_NetworkError() {
        int page = 1;
        when(kinopoiskApiV22Client.getTopPopularFilms(page)).thenThrow(new RuntimeException("Network error"));

        Optional<FilmCollectionResponse> response = topService.fetchTopFilmsFromApi(page);

        assertFalse(response.isPresent());
        verify(kinopoiskApiV22Client, times(1)).getTopPopularFilms(page);
    }

    @Test
    void fetchTopFilmsFromApi_AuthorizationError() {
        int page = 1;
        when(kinopoiskApiV22Client.getTopPopularFilms(page)).thenThrow(new RuntimeException("Authorization error"));

        Optional<FilmCollectionResponse> response = topService.fetchTopFilmsFromApi(page);

        assertFalse(response.isPresent());
        verify(kinopoiskApiV22Client, times(1)).getTopPopularFilms(page);
    }


}