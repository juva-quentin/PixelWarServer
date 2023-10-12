package com.example.pixelwarserver.controller;

import com.example.pixelwarserver.model.Pixel;
import com.example.pixelwarserver.repository.PixelRepository;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
@Log4j2
@Controller
public class PixelController {

    @Autowired
    private PixelRepository pixelRepository;


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