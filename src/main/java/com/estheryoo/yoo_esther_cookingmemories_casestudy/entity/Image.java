package com.estheryoo.yoo_esther_cookingmemories_casestudy.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Arrays;

/*
Image Table has 1:1 relationships with
user, recipe book, recipe pages, and recipe steps

The variable uploaded are for the bytes.
The variable linked is to tell who the image is linked to,
The variable type stores the image type: jpeg, png etc.
 */


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
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] uploaded;

    private String linked;

    private String type;

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
