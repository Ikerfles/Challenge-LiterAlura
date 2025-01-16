package com.challenge.literalura.repository;

import com.challenge.literalura.model.DatosLibro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatosLibroRepository extends JpaRepository<DatosLibro, Long> {
    Optional<DatosLibro> findByTitulo(String titulo);
}

