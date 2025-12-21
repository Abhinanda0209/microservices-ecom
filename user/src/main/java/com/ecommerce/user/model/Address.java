package com.ecommerce.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Address {

    private String id;
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
