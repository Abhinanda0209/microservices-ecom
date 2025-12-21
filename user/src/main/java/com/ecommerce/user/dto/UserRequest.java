package com.ecommerce.user.dto;

//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class UserRequest {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

//    @OneToOne
//    @JoinColumn(name = "address_id")
    private AddressDTO address;
}
