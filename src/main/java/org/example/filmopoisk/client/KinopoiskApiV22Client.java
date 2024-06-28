// KinopoiskApiClient.java
package org.example.filmopoisk.client;

import org.example.filmopoisk.config.FeignConfig;
import org.example.filmopoisk.entity.Film;
import org.example.filmopoisk.entity.FilmCollectionResponse;
import org.example.filmopoisk.entity.FilmSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kinopoisk22", url = "https://kinopoiskapiunofficial.tech/api/v2.2", configuration = FeignConfig.class)
public interface KinopoiskApiV22Client {

    @GetMapping("/films/{id}")
    Film getFilmById(@PathVariable("id") int id);

    @GetMapping("/films/collections?type=TOP_POPULAR_ALL")
    FilmCollectionResponse getTopPopularFilms(@RequestParam("page") int page);
}