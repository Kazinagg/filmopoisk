package org.example.filmopoisk.сontroller;

import org.example.filmopoisk.service.FilmService;
import org.example.filmopoisk.entity.FilmCollectionResponse;
import org.example.filmopoisk.service.TopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/top")
public class TopController {
    private final TopService topService;

    @Autowired
    public TopController(TopService topService) {
        this.topService = topService;
    }
    @GetMapping("/{page}")
    public ResponseEntity<FilmCollectionResponse> getTopFilms(@PathVariable int page) {
        Optional<FilmCollectionResponse> topCollection = topService.fetchTopFilmsFromApi(page);
        if (topCollection.isPresent()) {
            return ResponseEntity.ok(topCollection.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
