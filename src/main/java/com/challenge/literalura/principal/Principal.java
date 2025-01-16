package com.challenge.literalura.principal;

import com.challenge.literalura.model.DatosLibro;
import com.challenge.literalura.model.Libros;
import com.challenge.literalura.repository.DatosLibroRepository;
import com.challenge.literalura.service.ConsumoAPI;
import com.challenge.literalura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos();
    private DatosLibroRepository repository;
    private List<DatosLibro> libros;
    private String menu = """
                    
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
    private String idiomas = """
                    es - español
                    en - ingles
                    fr - frances
                    pt - portugues
                    """;


    public Principal(DatosLibroRepository repository) {
        this.repository = repository;
        this.libros = repository.findAll();
    }


    public void muestraMenu() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println(menu);
            System.out.print("Seleccione una opcion: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        mostrarLibrosRegistrados();
                        break;
                    case 3:
                        mostrarAutoresRegistrados();
                        break;
                    case 4:
                        autoresVivosEnDeterminadoAño();
                        break;
                    case 5:
                        mostrarLibrosPorIdioma();
                        break;
                    case 0:
                        System.out.println("Finalizando...");
                        break;
                    default:
                        System.out.println("Opcion invalida. Intente nuevamente.");
                        break;
                }
            } else {
                System.out.println("Por favor, ingrese una opcion valida.");
                scanner.next();
            }
        }
    }


    private void buscarLibroPorTitulo() {
        System.out.println("Escriba el nombre del libro que desea buscar");
        var tituloLibro = scanner.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = convierteDatos.obtenerDatos(json, Libros.class);
        Optional<DatosLibro> libroBuscado = datosBusqueda.getResultados().stream()
                .filter(l -> l.getTitulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if (libroBuscado.isPresent()) {
            var libroEncontrado = libroBuscado.get();
            Optional<DatosLibro> libroExistente = repository.findByTitulo(libroEncontrado.getTitulo());

            if (libroExistente.isEmpty()) {
                repository.save(libroEncontrado);
                System.out.println("Libro encontrado. Guardando en la base de datos...");
                System.out.println("-----Libro-----");
                System.out.printf("Titulo: %s%n", libroEncontrado.getTitulo());
                libroEncontrado.getAutor().forEach(a ->
                        System.out.printf("Autor: %s%n", a.getNombre())
                );
                System.out.printf("Idioma: %s%n", String.join(", ", libroEncontrado.getIdiomas()));
                System.out.printf("Numero de descargas: %.0f%n", libroEncontrado.getDecargas());
            } else {
                System.out.println("El libro ya existe en la base de datos.");
            }
        } else {
            System.out.println("Libro no encontrado.");
        }
    }


    private void mostrarLibrosRegistrados() {
        libros = repository.findAll();
        libros.forEach(libro -> {
            System.out.println("-----Libro-----");
            System.out.printf("Titulo: %s%n", libro.getTitulo());
            libro.getAutor().forEach(a ->
                    System.out.printf("Autor: %s%n", a.getNombre())
            );
            System.out.printf("Idioma: %s%n", String.join(", ", libro.getIdiomas()));
            System.out.printf("Numero de descargas: %.0f%n", libro.getDecargas());
            System.out.println("---------------\n");
        });
    }


    private void mostrarAutoresRegistrados() {
        List<DatosLibro> librosRegistrados = repository.findAll();
        if (librosRegistrados.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        librosRegistrados.stream()
                .flatMap(libro -> libro.getAutor().stream())
                .distinct()
                .forEach(autor -> {
                    System.out.println("---------------");
                    System.out.printf("Autor: %s%n", autor.getNombre());
                    System.out.printf("Fecha de nacimiento: %s%n", autor.getFechaDeNacimiento());
                    System.out.printf("Fecha de fallecimiento: %s%n", autor.getFechaDeFallecimiento());
                    List<String> librosDelAutor = librosRegistrados.stream()
                            .filter(libro -> libro.getAutor().contains(autor))
                            .map(DatosLibro::getTitulo)
                            .toList();
                    System.out.printf("Libros: %s%n", librosDelAutor.isEmpty() ? "Ninguno" : librosDelAutor);
                    System.out.println("---------------\n");
                });
    }


    private void autoresVivosEnDeterminadoAño() {
        System.out.println("Ingrese el año:");
        int year = scanner.nextInt();
        scanner.nextLine();
        List<DatosLibro> librosRegistrados = repository.findAll();
        var autoresVivos = librosRegistrados.stream()
                .flatMap(libro -> libro.getAutor().stream())
                .filter(autor -> {
                    int birthYear = autor.getFechaDeNacimiento() != null ? Integer.parseInt(autor.getFechaDeNacimiento()) : Integer.MIN_VALUE;
                    int deathYear = autor.getFechaDeFallecimiento() != null ? Integer.parseInt(autor.getFechaDeFallecimiento()) : Integer.MAX_VALUE;
                    return year >= birthYear && year <= deathYear;
                })
                .distinct()
                .toList();
        if (autoresVivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año especificado.");
        } else {
            autoresVivos.forEach(autor -> {
                var libros = librosRegistrados.stream()
                        .filter(libro -> libro.getAutor().contains(autor))
                        .map(DatosLibro::getTitulo)
                        .toList();
                System.out.printf("""
                ---------------
                Autor: %s
                Fecha de nacimiento: %s
                Fecha de fallecimiento: %s
                Libros: %s
                ---------------
                
                """,
                        autor.getNombre(),
                        autor.getFechaDeNacimiento() != null ? autor.getFechaDeNacimiento() : "N/A",
                        autor.getFechaDeFallecimiento() != null ? autor.getFechaDeFallecimiento() : "N/A",
                        libros.isEmpty() ? "Ninguno" : libros);
            });
        }
    }


    private void mostrarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar los libros:");
        System.out.println(String.join(", ", idiomas));
        String idiomaBuscado = scanner.nextLine().trim();
        libros = repository.findAll();
        var librosPorIdioma = libros.stream()
                .filter(libro -> libro.getIdiomas().contains(idiomaBuscado))
                .toList();
        if (librosPorIdioma.isEmpty()) {
            System.out.printf("No se encontraron libros en el idioma: %s%n", idiomaBuscado);
        } else {
            librosPorIdioma.forEach(libro -> {
                System.out.println("-----Libro-----");
                System.out.printf("Titulo: %s%n", libro.getTitulo());
                libro.getAutor().forEach(autor ->
                        System.out.printf("Autor: %s%n", autor.getNombre())
                );
                System.out.printf("Idioma: %s%n", String.join(", ", libro.getIdiomas()));
                System.out.printf("Numero de descargas: %.0f%n", libro.getDecargas());
                System.out.println("---------------\n");
            });
        }
    }
}
