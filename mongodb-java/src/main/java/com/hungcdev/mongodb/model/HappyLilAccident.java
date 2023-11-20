package com.hungcdev.mongodb.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HappyLilAccident {
    private Children causedBy; // Assuming Children caused the accident
    private String description;
    private boolean doesItHurt;
    private String action;
    private int diffSocialCredit;
}
