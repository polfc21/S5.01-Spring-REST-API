package com.it.academy.whitecollar.builders;

import com.it.academy.whitecollar.entities.Picture;
import com.it.academy.whitecollar.entities.Shop;

import java.time.LocalDate;

public class PictureBuilder {

    private String author;
    private String name;
    private double price;
    private Shop shop;

    public PictureBuilder() {

    }

    public PictureBuilder validPicture(Shop shop) {
        this.author = "author";
        this.name = "pictureName";
        this.price = 45.3;
        this.shop = shop;
        return this;
    }

    public PictureBuilder invalidPicture() {
        return this;
    }

    public Picture build() {
        return new Picture(this.author, this.name, this.price, this.shop);
    }
}
