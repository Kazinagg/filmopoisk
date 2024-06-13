package org.example.filmopoisk.entity;

import lombok.Data;
import java.util.List;

@Data
public class FilmCollectionResponse {

    private int total;
    private int totalPages;
    private List<FilmItem> items;

    @Data
    public static class FilmItem {
        private int kinopoiskId;
        private String nameRu;
        private String nameEn;
        private String nameOriginal;
        private List<Country> countries;
        private List<Genre> genres;
        private Float ratingKinopoisk;
        private Float ratingImdb;
        private int year;
        private String type;
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
