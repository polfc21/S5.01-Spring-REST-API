package com.it.academy.whitecollar.services;

import com.it.academy.whitecollar.builders.PictureBuilder;
import com.it.academy.whitecollar.builders.ShopBuilder;
import com.it.academy.whitecollar.dtos.PictureDTO;
import com.it.academy.whitecollar.dtos.ShopDTO;
import com.it.academy.whitecollar.entities.Picture;
import com.it.academy.whitecollar.entities.Shop;
import com.it.academy.whitecollar.repositories.PictureRepository;
import com.it.academy.whitecollar.repositories.ShopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShopServiceImplTest {

    @InjectMocks
    private ShopServiceImpl sut;
    @Mock
    private ShopRepository shopRepository;
    @Mock
    private PictureRepository pictureRepository;
    private ShopBuilder shopBuilder;
    private PictureBuilder pictureBuilder;

    @BeforeEach
    public void setUp(){
        this.shopBuilder = new ShopBuilder();
        this.pictureBuilder = new PictureBuilder();
    }

    @Test
    public void testGivenShopDTOWhenCreateShopThenReturnShopDTO(){
        Shop shop = this.shopBuilder.validShop().build();
        ShopDTO shopDTO = new ShopDTO(shop);

        when(this.shopRepository.save(shop)).thenReturn(shop);

        assertThat(this.sut.createShop(shopDTO), is(shopDTO));
    }

    @Test
    public void testWhenGetAllShopsThenReturnShopsDTO(){
        Shop shop = this.shopBuilder.validShop().build();
        List<Shop> shops = List.of(shop);
        List<ShopDTO> shopsDTO = List.of(new ShopDTO(shop));

        when(this.shopRepository.findAll()).thenReturn(shops);

        assertThat(this.sut.getAllShops(), is(shopsDTO));
    }

    @Test
    public void testGivenNotPresentShopIdWhenCreatePictureThenReturnNull(){
        long notPresentId = 1L;
        Picture picture = this.pictureBuilder.invalidPicture().build();
        PictureDTO pictureDTO = new PictureDTO(picture);

        when(this.shopRepository.existsById(notPresentId)).thenReturn(false);

        Assertions.assertNull(this.sut.createPicture(notPresentId,pictureDTO));
    }

    @Test
    public void testGivenPresentShopIdAndFullCapacityWhenCreatePictureThenReturnNull(){
        long presentId = 1L;
        Shop shop = this.shopBuilder.validShop().capacity(0).build();
        shop.setPictures(List.of());
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        PictureDTO pictureDTO = new PictureDTO(picture);

        when(this.shopRepository.existsById(presentId)).thenReturn(true);
        when(this.shopRepository.getById(presentId)).thenReturn(shop);

        Assertions.assertNull(this.sut.createPicture(presentId,pictureDTO));
    }

    @Test
    public void testGivenPresentShopIdAndNotFullCapacityWhenCreatePictureThenReturnPictureDTO(){
        long presentId = 1L;
        Shop shop = this.shopBuilder.validShop().capacity(1).build();
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        PictureDTO pictureDTO = new PictureDTO(picture);
        shop.setPictures(List.of());

        when(this.shopRepository.existsById(presentId)).thenReturn(true);
        when(this.shopRepository.getById(presentId)).thenReturn(shop);
        when(this.pictureRepository.save(picture)).thenReturn(picture);

        assertThat(this.sut.createPicture(presentId,pictureDTO), is(pictureDTO));
    }

    @Test
    public void testGivenPresentShopIdWhenGetAllPicturesThenReturnPicturesDTO(){
        long presentId = 1L;
        Shop shop = this.shopBuilder.validShop().build();
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        List<Picture> pictures = List.of(picture);
        List<PictureDTO> picturesDTO = List.of(new PictureDTO(picture));

        when(this.shopRepository.existsById(presentId)).thenReturn(true);
        when(this.shopRepository.getById(presentId)).thenReturn(shop);
        when(this.pictureRepository.getPicturesByShop(shop)).thenReturn(pictures);

        assertThat(this.sut.getAllPictures(presentId), is(picturesDTO));
    }

    @Test
    public void testGivenNotPresentShopIdWhenGetAllPicturesThenReturnNull(){
        long notPresentId = 1L;
        Shop shop = this.shopBuilder.invalidShop().build();

        when(this.shopRepository.existsById(notPresentId)).thenReturn(false);

        verify(this.pictureRepository, times(0)).getPicturesByShop(shop);
        verify(this.shopRepository, times(0)).getById(notPresentId);
        Assertions.assertNull(this.sut.getAllPictures(notPresentId));
    }

    @Test
    public void testGivenPresentIdWhenDeleteAllPicturesThenDeletePicturesByShop(){
        long presentId = 1L;
        Shop shop = this.shopBuilder.validShop().build();

        when(this.shopRepository.existsById(presentId)).thenReturn(true);
        when(this.shopRepository.getById(presentId)).thenReturn(shop);
        this.sut.deleteAllPictures(presentId);

        verify(this.pictureRepository).deletePicturesByShop(shop);
    }

    @Test
    public void testGivenNotPresentIdWhenDeleteAllPicturesThenNeverDeletePicturesByShop(){
        long notPresentId = 1L;
        Shop shop = this.shopBuilder.validShop().build();

        when(this.shopRepository.existsById(notPresentId)).thenReturn(false);
        this.sut.deleteAllPictures(notPresentId);

        verify(this.pictureRepository, times(0)).deletePicturesByShop(shop);
        verify(this.shopRepository, times(0)).getById(notPresentId);
    }
}
