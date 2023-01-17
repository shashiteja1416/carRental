package com.carRental.Car.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name="tbl_cars")
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "exception_seq_generator2")
    @SequenceGenerator(name = "exception_seq_generator2", sequenceName = "exception_seq2", allocationSize=1)
    @Column(name = "Id",nullable = false, length = 20)
    private Long Id;

    @Column(nullable = false, length = 20)
    private String Name;

    @Column(nullable = false, length = 20)
    private Long Type;

    @Column(nullable = false, length = 20)
    private String Seats;

    @Column(nullable = false, length = 20)
    private Long Mileage;

    @Column(nullable = false, length = 20)
    private String Color;

    @Column(nullable = false, length = 20)
    private String Fuel;

    @Override
    public String toString() {
        return "Car{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Type=" + Type +
                ", Seats='" + Seats + '\'' +
                ", Mileage=" + Mileage +
                ", Color='" + Color + '\'' +
                ", Fuel='" + Fuel + '\'' +
                ", Rent=" + Rent +
                ", Transmission='" + Transmission + '\'' +
                ", Available=" + Available +
                ", Image='" + Image + '\'' +
                '}';
    }

    @Column(nullable = false, length = 20)
    private Long Rent;

    @Column(nullable = false, length = 20)
    private String Transmission;

    @Column(nullable = false, length = 20)
    private Boolean Available;

    @Column(nullable = false, length = 100)
    private String Image;

}
