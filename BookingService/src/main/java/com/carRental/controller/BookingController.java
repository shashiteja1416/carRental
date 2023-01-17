package com.carRental.controller;

import com.carRental.DTO.Car;
import com.carRental.DTO.User;
import com.carRental.model.Booking;
import com.carRental.model.Response;
import com.carRental.service.BookingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/booking")
@Api(tags = { "user-controller" })
@SwaggerDefinition(tags = { @Tag(name = "user-controller", description = "Users controller") })
public class BookingController {

    @Autowired
    private BookingService service;

    @Autowired
    private RestTemplate rt;


    @ApiOperation(value = "get all bookings")
    @GetMapping("/allBookings")
    public Response getBookings() {
        return this.service.getAllBookings();
    }

    @ApiOperation(value = "get booking by Id")
    @GetMapping("/bookingId/{Id}")
    public Response getBookingDetailsById(@PathVariable Long Id){
        return this.service.getBookingDetailsById(Id);
    }

    @ApiOperation(value="create a booking")
    @PostMapping("/createBooking")
    public Response createBooking(@RequestBody Booking b) throws JsonProcessingException {
        Response carResp = rt.getForObject("http://localhost:5201/car/availableCars", Response.class);
        assert carResp!= null;
        boolean checkCar= false;
        final ObjectMapper objectMapper = new ObjectMapper();
        String respData = objectMapper.writeValueAsString(carResp.getData().get("cars"));
        Car[] cars = objectMapper.readValue(respData, Car[].class);
        for(Car sourceCar: cars){
            if (sourceCar.getId().equals(b.getCarId())) {
                checkCar = true;
                break;
            }
        }
        Response userResp = rt.getForObject("http://localhost:5202/user/ListUsers", Response.class);
        assert userResp!= null;
        boolean checkUser= false;
        String userData = objectMapper.writeValueAsString(userResp.getData().get("users"));
        User[] users = objectMapper.readValue(userData, User[].class);
        for(User sourceUser: users){
            if (sourceUser.getUserId().equals(b.getUserId()) && !sourceUser.getBlock()){
                checkUser = true;
                break;
            }
        }
        if(checkCar && checkUser){
            return  this.service.createBooking(b);
        }else if(!checkCar){
            return new Response(HttpStatus.BAD_REQUEST," car is not available, please choose other car",null);
        }else{
            return new Response(HttpStatus.BAD_REQUEST,"not a valid user, please try again",null);
        }
    }

    @ApiOperation(value = "complete a booking")
    @PutMapping("/completeBooking/{id}")
    public Response completeBooking(@PathVariable Long id){
        return this.service.completeBooking(id);
    }

    @ApiOperation(value = "start a booking")
    @PutMapping("/startBooking/{id}")
    public Response startBooking(@PathVariable Long id) throws JsonProcessingException {

        Booking b = service.getBookingById(id);
        final ObjectMapper objectMapper = new ObjectMapper();
        Response carResp = rt.getForObject("http://localhost:5201/car/carId/"+b.getCarId(), Response.class);
        assert carResp != null;
        String respData = objectMapper.writeValueAsString(carResp.getData().get("cars"));
        Car[] cars = objectMapper.readValue(respData, Car[].class);
        System.out.println(cars[0]);
        if(cars[0].getAvailable()) {
            HttpEntity<String> requestEntry = new HttpEntity<>(id.toString(), null);
            ResponseEntity<Response> result = rt.postForEntity("http://localhost:5201/car/editCarAvailable", requestEntry, Response.class);
            System.out.println(result.getBody());
            if(result.getStatusCode().value() == 200) {
                return this.service.startBooking(id);
            }else{
                return new Response(HttpStatus.BAD_REQUEST,"please check car availability",null);
            }
        }else{
            Map<String,Car> carsMap= new HashMap<>();
            carsMap.put("car",cars[0]);
            return new Response(HttpStatus.BAD_REQUEST,"booking cannot be started",carsMap);
        }
    }

    @ApiOperation(value = "delete a booking")
    @PutMapping("/deleteBooking/{id}")
    public Response deleteBooking(@PathVariable Long id){
        Booking b = service.getBookingById(id);
        if(!b.isActive()) {
            return this.service.deleteBooking(id);
        }else{
            Map<String,Booking> bookingMap= new HashMap<>();
            bookingMap.put("booking",b);
            return new Response(HttpStatus.EXPECTATION_FAILED,"Ongoing booking cannot be deleted",bookingMap);
        }
    }

    @ApiOperation(value="cancel a booking")
    @PutMapping("/cancelBooking/{id}")
    public Response cancelBooking(@PathVariable Long id){
        Booking b = service.getBookingById(id);
        if(!b.isActive()) {
            return this.service.cancelBooking(id);
        }else{
            Map<String,Booking> bookingMap= new HashMap<>();
            bookingMap.put("booking",b);
            return new Response(HttpStatus.EXPECTATION_FAILED,"Ongoing booking cannot be cancelled",bookingMap);
        }
    }

    @ApiOperation(value="Reschedule a booking")
    @PutMapping("/rescheduleBooking")
    public Response rescheduleBooking(@RequestBody Booking b){
        return this.service.rescheduleBooking(b);
    }


}
