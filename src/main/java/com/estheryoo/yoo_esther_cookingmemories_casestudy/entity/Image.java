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
    @Column(name = "file", columnDefinition = "BLOB")
    private byte[] uploaded;

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
