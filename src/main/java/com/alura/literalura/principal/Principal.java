package com.alura.literalura.principal;

import com.alura.literalura.models.Autor;
import com.alura.literalura.models.DatosLibro;
import com.alura.literalura.models.InformacionCompleta;
import com.alura.literalura.models.Libro;
import com.alura.literalura.repository.AutoresRepository;
import com.alura.literalura.repository.LibrosRepository;
import com.alura.literalura.services.ConsumoApi;
import com.alura.literalura.services.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConvierteDatos convertidor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    private Scanner scanner = new Scanner(System.in);
    private LibrosRepository librosRepository;
    private AutoresRepository autoresRepository;
    private List<Libro> libros;
    private List<Autor> autores;

    public Principal(LibrosRepository librosRepository, AutoresRepository autoresRepository){
        this.librosRepository = librosRepository;
        this.autoresRepository = autoresRepository;
    }

    public void mostrarMenu(){
        int muestra = 9;

        while(muestra != 0){

            var menu = """
                    1. Buscar libro por titulo
                    2. Mostrar libros registrados
                    3. Mostrar autores registrados
                    4.  Mostrar autores vivos de un año
                    5. Mostrar libros por idioma
                    6. Buscar autor por nombres
                    7. Mostrar los 5 libros más descargados
                    
                    """;

            System.out.println(menu);
            muestra = scanner.nextInt();
            scanner.nextLine();

            switch (muestra){

                case 1:
                    buscaLibroPorTitulo();
                    break;
                case 2:
                    muestraLibrosBuscados();
                    break;
                case 3:
                    muestraAutores();
                    break;
                case 4:
                    buscaAutorePorAnio();
                    break;
                case 5:
                    muestraLibrosPorIdioma();
                    break;
                case 6:
                    buscaAutorPorNombre();
                    break;
                case 7:
                    top5LibrosDescargados();
                    break;
                case 0:
                    System.out.println("Aplicación Finalizada");
                    break;
                default:
                    System.out.println("Opción invalida");


            }
        }

    }

    private DatosLibro getDatosLibro(){
        System.out.println("Ingrese nombre del libro a buscar: ");
        String tituloLibro = scanner.nextLine();
        var json = consumoApi.obtieneDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        System.out.println(json);
        InformacionCompleta informacionCompleta = convertidor.getDatos(json, InformacionCompleta.class);
        if (informacionCompleta.libros().isEmpty()) {
            throw new RuntimeException("No se encontraron libros con ese nombre.");
        }
        return informacionCompleta.libros().get(0);
    }

    public void buscaLibroPorTitulo(){
        DatosLibro datosLibro = getDatosLibro();

        Libro libroExiste = librosRepository.findByTituloIgnoreCase(datosLibro.titulo());

        if(libroExiste  != null){
            System.out.println("No se puede registar el libro, ya existe");
        }else if(datosLibro != null){
            List<Autor> listaAutor = datosLibro.autores().stream()
                    .map(datosAutor -> {
                        return autoresRepository.findByNombreCompleto(datosAutor.nombres())
                                .orElseGet(() -> autoresRepository.save(new Autor(datosAutor)));
                    }).collect(Collectors.toList());

            Libro libroNuevo = new Libro(datosLibro, listaAutor);

            libroNuevo.setAutores(listaAutor);
            librosRepository.save(libroNuevo);
            System.out.println(libroNuevo);
        }else{
            System.out.println("Libro no encontrado");
        }
    }


    private void muestraLibrosBuscados() {
        libros = librosRepository.findAllWithAutores();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void muestraAutores() {
        autores = autoresRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombreCompleto))
                .forEach(System.out::println);
    }

    private void buscaAutorePorAnio() {
        System.out.println("Ingrese un año para buscar autores");
        int busquedaPeriodo = scanner.nextInt();
        autores = autoresRepository.autorForYear(busquedaPeriodo);
        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores para ese año");
        } else {
            System.out.println(" --********* Autores vivos en ese año *********--\n");
            autores.forEach(System.out::println);
        }


    }

    public void muestraLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar libros:\n" +
                "es - Español\n" +
                "en - Inglés\n" +
                "fr - Francés\n" +
                "pt - Portugués");

        Scanner scanner = new Scanner(System.in);
        String idioma = scanner.nextLine().trim().toLowerCase();


        List<String> idiomasValidos = Arrays.asList("es", "en", "fr", "pt");

        if (!idiomasValidos.contains(idioma)) {
            System.out.println("Idioma invalido.");
            return;
        }

        List<Libro> libros = librosRepository.findByLenguajesContains(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en '" + idioma + "'.");
        } else {
            System.out.println("Libros en  '" + idioma + "':");
            libros.forEach(libro -> {
                System.out.println(libro);
            });
        }
    }


    public void buscaAutorPorNombre(){
        System.out.println("Ingrese el nombre del autor");
        String nombreAutor = scanner.nextLine().toLowerCase();
        autores = autoresRepository.findAll();
        Optional<Autor> autorOpcion = autores.stream()
                .filter(a-> a.getNombreCompleto().contains(nombreAutor.toLowerCase()))
                .findFirst();
        if (autorOpcion.isPresent()){
            System.out.println("Autor encontrado");
            System.out.println(autorOpcion);
        } else{
            System.out.println("Autor no encontrado");
        }
    }

    public void top5LibrosDescargados(){
        libros = librosRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getNumeroDeDescargas).reversed())
                .limit(5)
                .forEach(System.out::println);
    }
}
