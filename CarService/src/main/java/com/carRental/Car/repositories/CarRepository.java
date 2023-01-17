package com.carRental.Car.repositories;

import com.carRental.Car.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {

    @Query(value = "select * from tbl_cars",nativeQuery = true)
    public List<Car> listCars();

    @Query(value= "select * from tbl_cars where available = TRUE",nativeQuery = true)
    public List<Car> availableCars();

    @Query(value= "select * from tbl_cars where id = :Id",nativeQuery = true)
    public List<Car> getCarDetailsById(@Param("Id") Long Id);
}
