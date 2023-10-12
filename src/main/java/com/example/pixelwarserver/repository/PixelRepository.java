package com.example.pixelwarserver.repository;

import com.example.pixelwarserver.model.Pixel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PixelRepository extends MongoRepository<Pixel, String> {
}