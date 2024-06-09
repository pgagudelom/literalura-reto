package com.alura.literalura.repository;

import com.alura.literalura.models.Libro;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibrosRepository extends JpaRepository<Libro, Long> {

    @EntityGraph(attributePaths = "autores")
    @Query("SELECT b FROM Libro b")
    List<Libro> findAllWithAutores();

    Libro findByTituloIgnoreCase(String titulo);

    List<Libro> findByLenguajesContains(String lenguaje);
}
