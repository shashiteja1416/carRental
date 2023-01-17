package com.carRental.Car.service;

import com.carRental.Car.model.Car;
import com.carRental.Car.model.Response;

public interface CarService {

    Response listCars();

    Response addCar(Car car);

    void deleteCar(Long carId);

    Response availableCars();

    void updateCar(Car car);

    Response getCarDetailsById(Long id);

    Car getCarDetailById(Long id);

    void editCarAvailable(Car car);
}
