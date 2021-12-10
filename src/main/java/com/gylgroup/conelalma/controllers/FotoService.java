package com.gylgroup.conelalma.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.gylgroup.conelalma.exception.ExceptionService;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FotoService {

    private String directory = ".//src//main//resources//static//uploads//";

    public String saveFile(MultipartFile archivo) throws ExceptionService {

        try {

            String nombreFoto = randomString() + archivo.getOriginalFilename();
            Path rutaFoto = Paths.get(directory, nombreFoto).toAbsolutePath();
            Files.copy(archivo.getInputStream(), rutaFoto,
                    StandardCopyOption.REPLACE_EXISTING);

            return nombreFoto;
        } catch (IOException e) {
            throw new ExceptionService("ERROR AL GUARDAR FOTO");
        }

    }

    public String randomString() {
        return RandomStringUtils.randomAlphanumeric(4).toUpperCase();
    }

}