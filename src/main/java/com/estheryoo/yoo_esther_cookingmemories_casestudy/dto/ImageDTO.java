package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import lombok.*;
import java.util.Arrays;


/*
Data Transfer Object for Image Information
linked= holds who it belongs to: book, page, step, or user
type= jpeg, png (what type of file)
relationId = hold the id of the linked : book.id, page.id, step.id, or user.id
*/

@Getter
@Setter
@NoArgsConstructor

public class ImageDTO {
    private Long id;
    private byte[] uploaded;
    private String linked;
    private String type;
    private Long relationId;

    @Override
    public String toString() {
        return "ImageDTO{" +
                "id=" + id +
                ", uploaded=" + Arrays.toString(uploaded) +
                ", linked='" + linked + '\'' +
                ", type='" + type + '\'' +
                ", relationId=" + relationId +
                '}';
    }
}
