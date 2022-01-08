package com.it.academy.whitecollar.repositories;

import com.it.academy.whitecollar.entities.Picture;
import com.it.academy.whitecollar.entities.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> getPicturesByShop(Shop shop);
    void deletePicturesByShop(Shop shop);
}
