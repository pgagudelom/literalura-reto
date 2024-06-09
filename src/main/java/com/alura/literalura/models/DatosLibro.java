package com.alura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> lenguajes,
        @JsonAlias("download_count") Double numeroDeDescargas
        ) {

    public String getPrimerLenguaje(){
        return lenguajes != null && !lenguajes.isEmpty() ? lenguajes.get(0) : null;
    }
}
