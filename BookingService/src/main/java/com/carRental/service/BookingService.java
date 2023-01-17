package com.carRental.service;

import com.carRental.model.Booking;
import com.carRental.model.Response;

public interface BookingService {


    Response getAllBookings();

    Response getBookingDetailsById(Long id);

    Booking getBookingById(Long id);

    Response createBooking(Booking b);

    Response completeBooking(Long id);

    Response startBooking(Long id);

    Response deleteBooking(Long id);

    Response cancelBooking(Long id);

    Response rescheduleBooking(Booking b);
}
