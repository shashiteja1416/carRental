package com.carRental.Car.service.Impl;

import com.carRental.Car.model.Car;
import com.carRental.Car.model.Response;
import com.carRental.Car.repositories.CarRepository;
import com.carRental.Car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository cr;

    public Response listCars() {
        Map<String, List<Car>> carsMap = new HashMap<>();
        Response response = null;
        try {
            List<Car> carsList = cr.listCars();
            carsMap.put("cars", carsList);
            response = new Response(HttpStatus.OK,
                    "cars data is retrieved successfully", carsMap);
            if (carsList.isEmpty()) {
                response = new Response(HttpStatus.OK,
                        "no cars found", carsMap);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public Response getCarDetailsById(Long id) {
        Map<String, List<Car>> carsMap = new HashMap<>();
        Response response = null;
        try {
            if (cr.getCarDetailsById(id).isEmpty()){
                response = new Response(HttpStatus.OK,
                        "car not found", carsMap);
            }else{
                carsMap.put("cars", cr.getCarDetailsById(id));
                response = new Response(HttpStatus.OK,
                        "car data is retrieved successfully", carsMap);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public Car getCarDetailById(Long id) {
        Optional<Car> car = cr.findById(id);
        return car.orElse(null);
    }

    @Override
    public void editCarAvailable(Car car) {
        car.setAvailable(!car.getAvailable());
        cr.save(car);
    }

    @Override
    public Response addCar(Car car) {
        List<Car> cars= cr.listCars();
        Optional<Car> sourceCar;
        boolean check= false;
        for(Car c:cars){
            if (c.getName().equals(car.getName()) && cars.size()>0) {
                check = true;
                break;
            }
        }
        Response r= null;
        try {
            if (check) {
                return new Response(HttpStatus.BAD_REQUEST, "car already exists", null);
            } else {
                cr.save(car);
                Map<String, List<Car>> carsMap = new HashMap<>();
                List<Car> carsList = cr.listCars();
                carsMap.put("cars", carsList);
                return new Response(HttpStatus.OK, "car has been successfully added to the inventory", carsMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public void deleteCar(Long carId) {
        Optional<Car> source = cr.findById(carId);
        source.ifPresent(car -> cr.delete(car));
    }

    @Override
    public Response availableCars() {
        Map<String, List<Car>> carsMap = new HashMap<>();
        Response response = null;
        try {
            List<Car> carsList = cr.availableCars();
            carsMap.put("cars", carsList);
            response = new Response(HttpStatus.OK,
                    "available cars data is retrieved successfully", carsMap);
            if (carsList.isEmpty()) {
                response = new Response(HttpStatus.OK,
                        "no available cars found", carsMap);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public void updateCar(Car car) {
        cr.save(car);
    }

}
