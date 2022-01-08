package com.it.academy.whitecollar.services;

import com.it.academy.whitecollar.dtos.PictureDTO;
import com.it.academy.whitecollar.dtos.ShopDTO;

import java.util.List;

public interface ShopService {

    ShopDTO createShop(ShopDTO shopDTO);

    List<ShopDTO> getAllShops();

    PictureDTO createPicture(Long id, PictureDTO pictureDTO);

    List<PictureDTO> getAllPictures(Long id);

    void deleteAllPictures(Long id);

}
