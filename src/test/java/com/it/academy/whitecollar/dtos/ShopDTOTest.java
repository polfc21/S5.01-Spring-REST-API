package com.it.academy.whitecollar.dtos;

import com.it.academy.whitecollar.builders.ShopBuilder;
import com.it.academy.whitecollar.entities.Shop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ShopDTOTest {

    private ShopBuilder shopBuilder;

    @BeforeEach
    public void setUp() {
        this.shopBuilder = new ShopBuilder();
    }

    @Test
    public void testGivenShopsWhenGetShopsDTOThenReturnShopsDTO() {
        Shop shop = this.shopBuilder.validShop().build();
        List<Shop> shops = List.of(shop);
        ShopDTO shopDTO = new ShopDTO(shop);
        List<ShopDTO> shopsDTO = List.of(shopDTO);

        assertThat(ShopDTO.getShopsDTO(shops), is(shopsDTO));
    }

    @Test
    public void testGivenShopDTOWhenToShopThenReturnShop() {
        ShopDTO shopDTO = new ShopDTO("name", 1);
        Shop shop = new Shop("name", 1);

        assertThat(ShopDTO.toShop(shopDTO), is(shop));
    }
}
