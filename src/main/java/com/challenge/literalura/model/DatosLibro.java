package com.challenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "datos_libro")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosLibro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("title") String titulo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "datos_libro_id")
    @JsonAlias("authors") List<DatosAutor> autor;

    @JsonAlias("languages") List<String> idiomas;

    @JsonAlias("download_count") Double decargas;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<DatosAutor> getAutor() {
        return autor;
    }

    public void setAutor(List<DatosAutor> autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getDecargas() {
        return decargas;
    }

    public void setDecargas(Double decargas) {
        this.decargas = decargas;
    }

    @Override
    public String toString() {
        return "DatosLibro{" +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", idiomas=" + idiomas +
                ", decargas=" + decargas +
                '}';
    }
}

