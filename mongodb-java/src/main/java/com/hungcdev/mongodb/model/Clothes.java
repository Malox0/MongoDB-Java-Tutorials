package com.hungcdev.mongodb.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Clothes {
    private Long id;
    private String firstname;
    private String lastname;
    private String colour;
    private String size;
    private Children madeBy;
    private int addSocialCredit;
}
