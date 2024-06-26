//package org.example.filmopoisk.client;
//
//import org.example.filmopoisk.entity.Film;
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@FeignClient(name = "kinopoisk-api", url = "https://kinopoiskapiunofficial.tech/api/v2.2")
//public interface KinopoiskApiClient {
//
//    @GetMapping("/films/{id}")
//    Film getFilmById(@PathVariable("id") int id);
//}