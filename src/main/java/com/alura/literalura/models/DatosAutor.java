package com.alura.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombres,
        @JsonAlias("birth_year") int fechaCumple,
        @JsonAlias("death_year") int fechaMuerte
) {

}
