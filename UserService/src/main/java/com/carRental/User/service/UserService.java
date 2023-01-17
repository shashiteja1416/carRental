package com.carRental.User.service;

import com.carRental.User.model.Response;
import com.carRental.User.model.UserDetails;

public interface UserService {

    Response getUsers();

    Response getUserDetailById(Long Id);

    UserDetails getUserDetailsById(Long Id);

    void modifyUserProfile(UserDetails ud);

    void blockUser(Long Id);

    Response createUser(UserDetails ud);

    Response deleteUser(Long id);

    Response changePassword(String details);
}
