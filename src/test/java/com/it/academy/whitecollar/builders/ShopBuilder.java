package com.it.academy.whitecollar.builders;

import com.it.academy.whitecollar.entities.Picture;
import com.it.academy.whitecollar.entities.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopBuilder {

    private List<Picture> pictures;
    private String name;
    private int capacity;

    public ShopBuilder() {
        this.pictures = new ArrayList<>();
    }

    public ShopBuilder pictures(List<Picture> pictures) {
        this.pictures = pictures;
        return this;
    }

    public ShopBuilder capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public ShopBuilder validShop() {
        this.name = "nameShop";
        this.capacity = 10;
        return this;
    }

    public ShopBuilder invalidShop() {
        return this;
    }

    public Shop build() {
        return new Shop(this.name, this.capacity);
    }


}
