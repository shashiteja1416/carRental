package com.carRental.DTO;

import lombok.Data;

@Data
public class Car {

        private String Name;

        private Long Id;

        private Long Type;

        private String Color;

        private Boolean Available;

        private String Fuel;

        private String Seats;

        private String Transmission;

        private Long Mileage;

        private Long Rent;

        private String Image;

}
