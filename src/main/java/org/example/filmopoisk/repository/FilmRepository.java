package org.example.filmopoisk.repository;

import org.example.filmopoisk.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {
    List<Film> findByNameRuContainingIgnoreCase(String nameRu);
}
