package org.example.filmopoisk.repository;

import org.example.filmopoisk.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    // Поиск фильмов по части имени на русском языке, без учета регистра
    List<Film> findByNameRuContainingIgnoreCase(String nameRu);

    // Получение случайного списка фильмов, ограниченного количеством 'quantity'
    @Query(value = "SELECT * FROM films ORDER BY RANDOM() LIMIT :quantity", nativeQuery = true)
    List<Film> random(@Param("quantity") int quantity);
}

