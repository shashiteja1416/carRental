package com.carRental.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="tbl_booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "exception_seq_generator1")
    @SequenceGenerator(name = "exception_seq_generator1", sequenceName = "exception_seq1", allocationSize=1)
    @Column(name = "bookingId",nullable = false, length = 20)
    private Long bookingId;

    @Column(nullable = false, length = 20)
    private Long carId;

    @Column(nullable = false, length = 20)
    private Long userId;

    @Column
    private Date bookingTime;

    @Column
    private Date startTime;

    @Column
    private Date completeTime;

    @Column(nullable = false, length = 20)
    private Date pickUp;

    @Column(nullable = false, length = 20)
    private Date drop;

    @Column(nullable = false, length = 20)
    private String pickUpLocation;

    @Column(nullable = false, length = 20)
    private String dropLocation;

    @Column(nullable = false, length = 20)
    private boolean active=false;

    @Column(nullable = false, length = 20)
    private boolean cancel=false;

    @Column(nullable = false, length = 20)
    private boolean checkIn=false;


}
