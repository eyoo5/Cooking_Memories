package com.estheryoo.yoo_esther_cookingmemories_casestudy.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] uploaded;

    private String linked;

    @OneToOne(mappedBy = "image")
    private User user;

    @OneToOne(mappedBy = "image")
    private Recipe_Book book;

    @OneToOne(mappedBy = "image")
    private Recipe_Page page;

    @OneToOne(mappedBy = "image")
    private Recipe_Step step;


    public Image(byte[] uploaded) {
        this.uploaded = uploaded;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", uploaded=" + Arrays.toString(uploaded) +
                ", step=" + step +
                '}';
    }
}
