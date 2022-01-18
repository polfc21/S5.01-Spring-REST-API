package com.it.academy.whitecollar.controllers;

import com.it.academy.whitecollar.dtos.PictureDTO;
import com.it.academy.whitecollar.dtos.ShopDTO;
import com.it.academy.whitecollar.services.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping
    public ResponseEntity<ShopDTO> createShop(@Valid @RequestBody ShopDTO shopDTO) {
        try {
            return new ResponseEntity<>(this.shopService.createShop(shopDTO), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<ShopDTO>> getAllShops() {
        List<ShopDTO> shopsDTO = this.shopService.getAllShops();
        if (shopsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(shopsDTO, HttpStatus.OK);
    }

    @PostMapping("/{id}/pictures")
    public ResponseEntity<PictureDTO> createPicture(@PathVariable Long id,
                                                    @Valid @RequestBody PictureDTO pictureDTO) {
        try {
            PictureDTO created = this.shopService.createPicture(id, pictureDTO);
            if (created != null) {
                return new ResponseEntity<>(created, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}/pictures")
    public ResponseEntity<List<PictureDTO>> getAllPicturesByIdShop(@PathVariable Long id) {
        List<PictureDTO> picturesDTO = this.shopService.getAllPictures(id);
        if (picturesDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (picturesDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(picturesDTO, HttpStatus.OK);
    }

    @DeleteMapping("{id}/pictures")
    public ResponseEntity<HttpStatus> deleteAllPicturesByIdShop(@PathVariable Long id) {
        try {
            this.shopService.deleteAllPictures(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
