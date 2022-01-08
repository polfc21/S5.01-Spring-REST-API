package com.it.academy.whitecollar.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private int capacity;

    @OneToMany(mappedBy = "shop")
    private List<Picture> pictures;

    public Shop(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

}
