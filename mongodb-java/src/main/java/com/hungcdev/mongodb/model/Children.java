package com.hungcdev.mongodb.model;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Children {
    private Long id;
    private String barcodePosition;
    private int socialCredit;
    private LocalDate birthday; //must not be celebrated
    private Children headOfDepartmentChild;
}

