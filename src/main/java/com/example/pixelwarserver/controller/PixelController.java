package com.example.pixelwarserver.controller;

import com.example.pixelwarserver.model.Pixel;
import com.example.pixelwarserver.repository.PixelRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;


@Log4j2
@Controller
public class PixelController {

    @Autowired
    private PixelRepository pixelRepository;

    /**
     * Met à jour un pixel enregistré dans la base de données.
     *
     * @param pixel Le pixel à mettre à jour.
     * @return Le pixel mis à jour.
     */
    public Pixel updatePixel(Pixel pixel) {
        try {
            Pixel savedPixel = pixelRepository.save(pixel);
            System.out.println("ID du pixel sauvegardé : " + savedPixel.getId());
            System.out.println("Couleur du pixel sauvegardé : " + savedPixel.getColor());
            System.out.println("Coordonnée X du pixel sauvegardé : " + savedPixel.getX());
            System.out.println("Coordonnée Y du pixel sauvegardé : " + savedPixel.getY());
            return pixel;
        } catch (Exception e) {
            log.warn(e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Récupère tous les pixels enregistrés dans la base de données.
     *
     * @return Une liste de tous les pixels.
     */
    public List<Pixel> getAllPixels() {
        try {
            return pixelRepository.findAll();
        } catch (Exception e) {
            log.warn(e);
            e.printStackTrace();
            throw e;
        }
    }
}