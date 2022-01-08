package com.it.academy.whitecollar.services;

import com.it.academy.whitecollar.dtos.PictureDTO;
import com.it.academy.whitecollar.dtos.ShopDTO;
import com.it.academy.whitecollar.repositories.PictureRepository;
import com.it.academy.whitecollar.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private PictureRepository pictureRepository;

    @Override
    public ShopDTO createShop(ShopDTO shopDTO) {
        return new ShopDTO(this.shopRepository.save(ShopDTO.toShop(shopDTO)));
    }

    @Override
    public List<ShopDTO> getAllShops() {
        return ShopDTO.getShopsDTO(this.shopRepository.findAll());
    }

    @Override
    public PictureDTO createPicture(Long id, PictureDTO pictureDTO) {
        if (this.canCreatePicture(id)) {
            return new PictureDTO(this.pictureRepository.save(PictureDTO.toPicture(pictureDTO, this.shopRepository.getById(id))));
        }
        return null;
    }

    private boolean canCreatePicture(Long id) {
        if (this.shopRepository.existsById(id)) {
            int capacity = this.shopRepository.getById(id).getCapacity();
            int picturesLength = this.shopRepository.getById(id).getPictures().size();
            return picturesLength < capacity;
        }
        return false;
    }


    @Override
    public List<PictureDTO> getAllPictures(Long id) {
        if (shopRepository.existsById(id)) {
            return PictureDTO.getPicturesDTO(this.pictureRepository.getPicturesByShop(this.shopRepository.getById(id)));
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteAllPictures(Long id) {
        if (this.shopRepository.existsById(id)) {
            this.pictureRepository.deletePicturesByShop(this.shopRepository.getById(id));
        }
    }



}
