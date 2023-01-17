package com.carRental.service.Impl;

import com.carRental.model.Booking;
import com.carRental.model.Response;
import com.carRental.repositories.BookingRepository;
import com.carRental.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository br;

    @Override
    public Response getAllBookings() {
        Map<String, List<Booking>> bookings = new HashMap<>();
        Response response = null;
        try {
            List<Booking> bookingsList = br.listBookings();
            bookings.put("Bookings", bookingsList);
            response = new Response(HttpStatus.OK,
                    "bookings retrieved successfully", bookings);
            if (bookingsList.isEmpty()) {
                response = new Response(HttpStatus.OK,
                        "no bookings found", bookings);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public Response getBookingDetailsById(Long id) {
        Map<String, List<Booking>> bookings = new HashMap<>();
        Response response = null;
        try {
            if (br.getBookingDetailsById(id).isEmpty()){
                response = new Response(HttpStatus.OK,
                        "booking not found", bookings);
            }else{
                bookings.put("bookings", br.getBookingDetailsById(id));
                response = new Response(HttpStatus.OK,
                        "bookings are retrieved successfully", bookings);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public Booking getBookingById(Long id){
        Optional<Booking> booking = br.findById(id);
        return booking.orElse(null);
    }

    @Override
    public Response createBooking(Booking b) {
    try{
        if(b.getDrop().compareTo(b.getPickUp()) > 0) {
            b.setBookingTime(new Date());
            br.save(b);
            return new Response(HttpStatus.OK, "Booking has been done successfully",null);
        }else{
            return new Response(HttpStatus.CONFLICT, "drop date should be greater than pickup date",null);
        }
    } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response startBooking(Long id) {
        try {
            Booking b = getBookingById(id);
            if (b != null && !b.isActive()) {
                b.setActive(true);
                b.setStartTime(new Date());
                br.save(b);
                Map<String, Booking> bookingMap = new HashMap<>();
                bookingMap.put("booking", b);
                return new Response(HttpStatus.OK, "booking has been started successfully", bookingMap);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  new Response(HttpStatus.EXPECTATION_FAILED, "booking is not started or already started",null);
    }

    @Override
    public Response completeBooking(Long id) {
        try {
            Booking b = getBookingById(id);
            if (b != null && b.isActive()) {
                b.setActive(false);
                b.setCompleteTime(new Date());
                br.save(b);
                Map<String, Booking> bookingMap = new HashMap<>();
                bookingMap.put("booking", b);
                return new Response(HttpStatus.OK, "booking has been completed", bookingMap);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return  new Response(HttpStatus.EXPECTATION_FAILED, "booking was not completed",null);
    }


    @Override
    public Response deleteBooking(Long id) {
        try{
            Booking b = getBookingById(id);
            if(b!=null){
                br.delete(b);
                return new Response(HttpStatus.OK, "booking has been deleted", null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new Response(HttpStatus.EXPECTATION_FAILED, "booking has not been deleted", null);
    }

    @Override
    public Response cancelBooking(Long id) {
        try{
            Booking b = getBookingById(id);
            if(b!=null && !b.isActive()){
                b.setCancel(true);
                br.save(b);
                return new Response(HttpStatus.OK, "booking has been cancelled", null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  new Response(HttpStatus.EXPECTATION_FAILED, "booking has not been cancelled", null);
    }

    @Override
    public Response rescheduleBooking(Booking b) {
        try{
            if(b.getDrop().compareTo(b.getPickUp()) > 0) {
                Booking sourceBooking = getBookingById(b.getBookingId());
                sourceBooking.setBookingTime(new Date());
                sourceBooking.setPickUp(b.getPickUp());
                sourceBooking.setDrop(b.getDrop());
                br.save(sourceBooking);
                return new Response(HttpStatus.OK, "Booking has been rescheduled successfully",null);
            }else{
                return new Response(HttpStatus.CONFLICT, "drop date should be greater than pickup date",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
