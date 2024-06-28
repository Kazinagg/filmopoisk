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

@FeignClient(name = "kinopoisk21", url = "https://kinopoiskapiunofficial.tech/api/v2.1", configuration = FeignConfig.class)
public interface KinopoiskApiV21Client {

    @GetMapping("/films/search-by-keyword")
    FilmSearchResponse searchFilmsByKeyword(@RequestParam("keyword") String keyword, @RequestParam("page") int page);
}