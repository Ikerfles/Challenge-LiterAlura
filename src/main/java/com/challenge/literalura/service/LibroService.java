package com.challenge.literalura.service;

import com.challenge.literalura.model.DatosLibro;
import com.challenge.literalura.repository.DatosAutorRepository;
import com.challenge.literalura.repository.DatosLibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    @Autowired
    private DatosLibroRepository datosLibroRepository;

    @Autowired
    private DatosAutorRepository datosAutorRepository;

    public void saveLibro(DatosLibro datosLibro) {
        datosLibroRepository.save(datosLibro);
    }
}

