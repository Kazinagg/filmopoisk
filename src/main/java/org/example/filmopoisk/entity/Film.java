package org.example.filmopoisk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "movies")
public class Film {
    @Id
    private Integer kinopoiskId;

    @Column(name = "kinopoisk_hd_id")
    private String kinopoiskHdId;

    @Column(name = "imdb_id")
    private String imdbId;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "name_original")
    private String nameOriginal;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "poster_url_preview")
    private String posterUrlPreview;

    @Column(name = "cover_url")
    private String coverUrl;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "reviews_count")
    private Integer reviewsCount;

    @Column(name = "rating_good_review")
    private Float ratingGoodReview;

    @Column(name = "rating_good_review_vote_count")
    private Integer ratingGoodReviewVoteCount;

    @Column(name = "rating_kinopoisk")
    private Float ratingKinopoisk;

    @Column(name = "rating_kinopoisk_vote_count")
    private Integer ratingKinopoiskVoteCount;

    @Column(name = "rating_imdb")
    private Float ratingImdb;

    @Column(name = "rating_imdb_vote_count")
    private Integer ratingImdbVoteCount;

    @Column(name = "rating_film_critics")
    private Float ratingFilmCritics;

    @Column(name = "rating_film_critics_vote_count")
    private Integer ratingFilmCriticsVoteCount;

    @Column(name = "rating_await")
    private Float ratingAwait;

    @Column(name = "rating_await_count")
    private Integer ratingAwaitCount;

    @Column(name = "rating_rf_critics")
    private Float ratingRfCritics;

    @Column(name = "rating_rf_critics_vote_count")
    private Integer ratingRfCriticsVoteCount;

    @Column(name = "web_url")
    private String webUrl;

    @Column(name = "year")
    private Integer year;

    @Column(name = "film_length")
    private Integer filmLength;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "description")
    private String description;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "editor_annotation")
    private String editorAnnotation;

    @Column(name = "is_tickets_available")
    private Boolean isTicketsAvailable;

    @Column(name = "production_status")
    private String productionStatus;

    @Column(name = "type")
    private String type;

    @Column(name = "rating_mpaa")
    private String ratingMpaa;

    @Column(name = "rating_age_limits")
    private String ratingAgeLimits;

    @Column(name = "has_imax")
    private Boolean hasImax;

    @Column(name = "has_3d")
    private Boolean has3d;

    @Column(name = "last_sync")
    private String lastSync;

//    @Transient
//    private List<Country> countries;
//
//    @Transient
//    private List<Genre> genres;

    @ElementCollection
    @CollectionTable(name = "movie_countries", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "country")
    private List<String> countries;

    @ElementCollection
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre")
    private List<String> genres;


    @JsonProperty("countries")
    private void unpackCountries(List<Country> countriesApi) {
        this.countries = countriesApi.stream()
                .map(Country::getCountry)
                .collect(Collectors.toList());
    }

    @JsonProperty("genres")
    private void unpackGenres(List<Genre> genresApi) {
        this.genres = genresApi.stream()
                .map(Genre::getGenre)
                .collect(Collectors.toList());
    }

    @Data
    public static class Country {
        private String country;
    }
    @Data
    public static class Genre {
        private String genre;
    }


    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "serial")
    private Boolean serial;

    @Column(name = "short_film")
    private Boolean shortFilm;

    @Column(name = "completed")
    private Boolean completed;


}

