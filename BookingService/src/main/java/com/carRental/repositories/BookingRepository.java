package com.carRental.repositories;

import com.carRental.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Query(value="select * from tbl_booking",nativeQuery = true)
    List<Booking> listBookings();

    @Query(value="select * from tbl_booking where bookingId = :bookingId",nativeQuery = true)
    List<Booking> getBookingDetailsById(@Param("bookingId") Long bookingId);

}
