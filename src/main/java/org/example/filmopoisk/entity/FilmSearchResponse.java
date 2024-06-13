package org.example.filmopoisk.entity;

import lombok.Data;
import java.util.List;

@Data
public class FilmSearchResponse {

    private String keyword;
    private int pagesCount;
    private int searchFilmsCountResult;
    private List<FilmItem> films;

    @Data
    public static class FilmItem {
        private int filmId;
        private String nameRu;
        private String nameEn;
        private int year;
        private String type;
        private String description;
        private String filmLength;
        private List<Country> countries;
        private List<Genre> genres;
        private String rating;
        private int ratingVoteCount;
        private String posterUrl;
        private String posterUrlPreview;

        @Data
        public static class Country {
            private String country;
        }

        @Data
        public static class Genre {
            private String genre;
        }
    }
}
