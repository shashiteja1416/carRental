package com.carRental.User.repository;

import com.carRental.User.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author pteja Repository to query from DB
 */
@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

    @Query(value = "select * from tbl_users",nativeQuery = true)
    public List<UserDetails> getUsers();

    @Query(value= "select * from tbl_users where user_id = :Id",nativeQuery = true)
    public List<UserDetails> getUserDetailsById(@Param("Id") Long Id);

}
