package com.estheryoo.yoo_esther_cookingmemories_casestudy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor

public class ImageDTO {
    private Long id;
    private byte[] uploaded;
    // Describes what it is linked to: step, page, book.
    private String linked;
}
