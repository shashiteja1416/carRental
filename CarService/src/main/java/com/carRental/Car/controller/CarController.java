package com.carRental.Car.controller;

import com.carRental.Car.model.Car;
import com.carRental.Car.model.Response;
import com.carRental.Car.repositories.CarRepository;
import com.carRental.Car.service.CarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/car")
@Api(tags = { "car-controller"})
@SwaggerDefinition(tags = { @Tag(name = "car-controller", description = "Car controller") })
public class CarController {

    @Autowired
    private CarService service;

    @Autowired
    private CarRepository cr;

    @ApiOperation(value = "get all cars")
    @GetMapping("/listCars")
    public Response listCars(){
        return this.service.listCars();
    }

    @ApiOperation(value = "get car by Id")
    @GetMapping("/carId/{Id}")
    public Response getUserDetailById(@PathVariable Long Id){
        return this.service.getCarDetailsById(Id);
    }


    @ApiOperation(value = "add a car")
    @PostMapping("/addCar")
    public Response addCar(@RequestBody Car car){
        return this.service.addCar(car);
    }

    @ApiOperation(value = "delete a car")
    @DeleteMapping("/deleteCar/{carId}")
    public Response deleteCar(@PathVariable Long carId) {
        Optional<Car> cars = cr.findById(carId);
        try {
            if (cars.isPresent()) {
                this.service.deleteCar(carId);
                Map<String, List<Car>> carsMap = new HashMap<>();
                List<Car> carsList = cr.listCars();
                carsMap.put("cars", carsList);
                return new Response(HttpStatus.OK, "car deleted successfully from inventory", carsMap);
            } else {
                return new Response(HttpStatus.NOT_FOUND, "car doesn't exist", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @ApiOperation(value="check for available cars")
    @GetMapping("/availableCars")
    public Response availableCars(){
        return this.service.availableCars();
    }

    @ApiOperation(value= "update car details")
    @PutMapping("/updateCar")
    public Response updateCar(@RequestBody Car car){
        Car dbcar = service.getCarDetailById(car.getId());
        if(dbcar != null){
            service.updateCar(car);
            Map<String, Car> mp = new HashMap<>();
            mp.put("data",car);
            return new Response(HttpStatus.OK,"car updated successfully",mp);
        }
        return  new Response(HttpStatus.NOT_FOUND,"car details not found",null);
    }

    @ApiOperation(value="edit car availability")
    @PostMapping("/editCarAvailable")
    public Response editCarAvailability(@RequestBody String id){
        Car dbcar = service.getCarDetailById(Long.parseLong(id));
        if(dbcar != null) {
            service.editCarAvailable(dbcar);
            Map<String, Car> mp = new HashMap<>();
            mp.put("data",dbcar);
            return new Response(HttpStatus.OK,"car availability updated",mp);

        }
        return  new Response(HttpStatus.NOT_FOUND,"car details not found",null);
    }

    @ApiOperation(value="upgrade Car")
    @PutMapping("/upgradeCar")
    public Response upgradeCar(@RequestBody Car car){
        return  null;
    }
}