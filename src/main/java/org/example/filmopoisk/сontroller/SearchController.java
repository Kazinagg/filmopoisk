package org.example.filmopoisk.сontroller;

import org.example.filmopoisk.entity.Film;
import org.example.filmopoisk.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/by-keyword")
    public List<Film> searchByKeyword(@RequestParam String keyword) {
        return searchService.searchFilmsByKeyword(keyword);
    }
}