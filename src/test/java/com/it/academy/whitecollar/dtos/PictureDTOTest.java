package com.it.academy.whitecollar.dtos;

import com.it.academy.whitecollar.builders.PictureBuilder;
import com.it.academy.whitecollar.builders.ShopBuilder;
import com.it.academy.whitecollar.entities.Picture;
import com.it.academy.whitecollar.entities.Shop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PictureDTOTest {

    private PictureBuilder pictureBuilder;
    private ShopBuilder shopBuilder;

    @BeforeEach
    public void setUp() {
        this.pictureBuilder = new PictureBuilder();
        this.shopBuilder = new ShopBuilder();
    }

    @Test
    public void testGivenPresentPicturesWhenGetPicturesDTOThenReturnPicturesDTO() {
        Shop shop = this.shopBuilder.validShop().build();
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        List<Picture> pictures = List.of(picture);
        PictureDTO pictureDTO = new PictureDTO(picture);
        List<PictureDTO> picturesDTO = List.of(pictureDTO);

        assertThat(PictureDTO.getPicturesDTO(pictures), is(picturesDTO));
    }

    @Test
    public void testGivenNotPresentPicturesWhenGetPicturesDTOThenReturnNull() {
        Assertions.assertNull(PictureDTO.getPicturesDTO(null));
    }

    @Test
    public void testGivenPictureDTOWithAuthorAndShopWhenToPictureThenReturnPicture() {
        Shop shop = this.shopBuilder.validShop().build();
        PictureDTO pictureDTO = new PictureDTO(1L,"Author","Name",1D, LocalDate.now());
        Picture picture = new Picture("Author","Name",1D,shop);

        assertThat(PictureDTO.toPicture(pictureDTO, shop), is(picture));
    }

    @Test
    public void testGivenPictureDTOWithoutAuthorAndShopWhenToPictureThenReturnPicture() {
        Shop shop = this.shopBuilder.validShop().build();
        PictureDTO pictureDTO = new PictureDTO(1L,null,"Name",1D, LocalDate.now());
        Picture picture = new Picture("Anònim","Name",1D,shop);

        assertThat(PictureDTO.toPicture(pictureDTO, shop), is(picture));
    }
}
