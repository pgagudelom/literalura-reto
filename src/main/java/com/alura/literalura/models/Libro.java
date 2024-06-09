package com.alura.literalura.models;


import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;
    private String lenguajes;
    private Double numeroDeDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro, List<Autor> autores){
        this.titulo = datosLibro.titulo();
        this.autores = autores;
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String lenguajes) {
        this.lenguajes = lenguajes;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }


    @Override
    public String toString(){
        String nombresDeAutores = autores.stream()
                .map(Autor::getNombreCompleto)
                .collect(Collectors.joining(","));

        return "***********--LIBRO--************\n"+
                "Titulo: " + titulo + "\n" +
                "Autores: " + nombresDeAutores + "\n" +
                "Idioma: " + lenguajes + "\n" +
                "Numero descargas: " + numeroDeDescargas;
    }
}
