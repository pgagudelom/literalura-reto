package com.alura.literalura.repository;

import com.alura.literalura.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutoresRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombreCompleto(String nombre);
    @Query("SELECT a FROM Autor a WHERE :year >= a.fechaCumple AND (:year <= a.fechaMuerte OR a.fechaMuerte IS NULL)")
    List<Autor> autorForYear(int year);
}
