package com.alura.literalura.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCompleto;
    private int fechaCumple;
    private int fechaMuerte;

    @ManyToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<com.alura.literalura.models.Libro> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombreCompleto = datosAutor.nombres();
        this.fechaCumple = Integer.valueOf(datosAutor.fechaCumple());
        this.fechaMuerte = Integer.valueOf(datosAutor.fechaMuerte());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getFechaCumple() {
        return fechaCumple;
    }

    public void setFechaCumple(int fechaCumple) {
        this.fechaCumple = fechaCumple;
    }

    public int getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(int fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString(){
        String listaLibros = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining(","));

        return "***********--AUTOR--************\n"+
                "Nombre: " + nombreCompleto + "\n" +
                "Año nacimiento: " + fechaCumple + "\n" +
                "Año muerte: " + fechaMuerte + "\n" +
                "Libros: " + listaLibros;
    }
}
