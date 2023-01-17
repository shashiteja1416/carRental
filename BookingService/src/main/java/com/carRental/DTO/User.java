package com.carRental.DTO;

import lombok.Data;

@Data
public class User {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String contact;

    private String address;

    private String role;

    private Boolean block;
}
