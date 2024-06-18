package org.example.filmopoisk.сontroller;

import org.example.filmopoisk.entity.Film;
import org.example.filmopoisk.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable int id) {
        Optional<Film> film = filmService.findFilmByIdOrFetchFromApi(id);
        if (film.isPresent()) {
            return ResponseEntity.ok(film.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/random")
    public ResponseEntity<List<Film>> getRandomFilms() {
        List<Film> films = filmService.getRandomFilms();
        if (!films.isEmpty()) {
            return ResponseEntity.ok(films);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
