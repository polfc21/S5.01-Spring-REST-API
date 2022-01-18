package com.it.academy.whitecollar.repositories;

import com.it.academy.whitecollar.builders.PictureBuilder;
import com.it.academy.whitecollar.builders.ShopBuilder;
import com.it.academy.whitecollar.entities.Picture;
import com.it.academy.whitecollar.entities.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
class PictureRepositoryTest {

    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private TestEntityManager testEntityManager;

    private ShopBuilder shopBuilder;
    private PictureBuilder pictureBuilder;

    @BeforeEach
    public void setUp() {
        this.shopBuilder = new ShopBuilder();
        this.pictureBuilder = new PictureBuilder();
    }

    @Test
    public void testGivenValidShopWithPictureWhenGetPicturesThenContainsPicture() {
        Shop shop = this.shopBuilder.validShop().build();
        this.testEntityManager.persist(shop);
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        this.testEntityManager.persist(picture);

        List<Picture> pictures = this.pictureRepository.getPicturesByShop(shop);

        assertThat(pictures, is(List.of(picture)));
    }

    @Test
    public void testGivenValidShopWithoutPictureWhenGetPicturesThenIsEmpty() {
        Shop shopEmpty = this.shopBuilder.validShop().build();
        this.testEntityManager.persist(shopEmpty);

        List<Picture> pictures = this.pictureRepository.getPicturesByShop(shopEmpty);

        assertThat(pictures, is(List.of()));
    }

    @Test
    public void testGivenValidShopWithPictureWhenDeletePicturesByShopThenIsEmpty() {
        Shop shop = this.shopBuilder.validShop().build();
        this.testEntityManager.persist(shop);
        Picture picture = this.pictureBuilder.validPicture(shop).build();
        this.testEntityManager.persist(picture);

        this.pictureRepository.deletePicturesByShop(shop);

        assertThat(this.pictureRepository.getPicturesByShop(shop), is(List.of()));
    }



}