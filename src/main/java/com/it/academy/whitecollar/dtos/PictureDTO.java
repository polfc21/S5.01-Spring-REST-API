package com.it.academy.whitecollar.dtos;

import com.it.academy.whitecollar.entities.Picture;
import com.it.academy.whitecollar.entities.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureDTO {

    private Long id;

    private String author;

    @NonNull
    @NotBlank(message = "Picture's name should not be blank")
    private String name;

    @NonNull
    @Positive(message = "Price's picture should be positive")
    private double price;

    private LocalDate entryDate;

    public PictureDTO(Picture picture) {
        this.id = picture.getId();
        this.author = picture.getAuthor();
        this.name = picture.getName();
        this.price = picture.getPrice();
        this.entryDate = picture.getEntryDate();
    }

    public static List<PictureDTO> getPicturesDTO(List<Picture> pictures) {
        if (pictures != null) {
            return pictures.stream().map(PictureDTO::new).collect(Collectors.toList());
        }
        return null;
    }

    public static Picture toPicture(PictureDTO pictureDTO, Shop shop) {
        if (pictureDTO.getAuthor() == null) {
            pictureDTO.setAuthor("Anònim");
        }
        return new Picture(pictureDTO.getAuthor(), pictureDTO.getName(), pictureDTO.getPrice(), shop);
    }


}
