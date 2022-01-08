package com.it.academy.whitecollar.dtos;

import com.it.academy.whitecollar.entities.Shop;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ShopDTO {

    private Long id;

    @NonNull
    @NotBlank(message = "Name's shop should not be blank")
    private String name;

    @Positive(message = "Capacity should be positive")
    private int capacity;

    private List<PictureDTO> pictures;

    public ShopDTO(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.capacity = shop.getCapacity();
        this.pictures = PictureDTO.getPicturesDTO(shop.getPictures());
    }

    public ShopDTO(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public static List<ShopDTO> getShopsDTO(List<Shop> shops) {
        return shops.stream().map(ShopDTO::new).collect(Collectors.toList());
    }

    public static Shop toShop(ShopDTO shopDTO) {
        return new Shop(shopDTO.getName(), shopDTO.getCapacity());
    }


}
