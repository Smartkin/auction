package com.badcompany.auction.controllers;

import com.badcompany.auction.entities.Image;
import com.badcompany.auction.payload.response.MessageResponse;
import com.badcompany.auction.repositories.ImageRepository;
import com.badcompany.auction.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;

import java.io.IOException;

@RestController
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    FileService fileService;

    @GetMapping(value = "/api/images", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getImageHandler(@RequestParam(name = "id")Long imageId) throws IOException {
        Image image = imageRepository.getOne(imageId);
        if (imageId == 1 || imageId == 2) {
            return ResponseEntity.badRequest().body(new MessageResponse("Такого изображения нет"));
        }
        Resource resource = fileService.load(image.getFileName());

        return ResponseEntity.ok(resource);
    }
}
