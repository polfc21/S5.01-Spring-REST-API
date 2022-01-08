package com.it.academy.whitecollar.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long id;

    @NonNull
    private String author;

    @NonNull
    private String name;

    @NonNull
    private double price;

    @Column(name = "entry_date")
    private LocalDate entryDate;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    public Picture(String author, String name, double price, Shop shop) {
        this.author = author;
        this.name = name;
        this.price = price;
        this.shop = shop;
        this.entryDate = LocalDate.now();
    }

}
