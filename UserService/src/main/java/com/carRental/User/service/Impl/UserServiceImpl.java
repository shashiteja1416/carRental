package com.carRental.User.service.Impl;


//This service implementation is for user actions

import com.carRental.User.model.Response;
import com.carRental.User.model.UserDetails;
import com.carRental.User.repository.UserRepository;
import com.carRental.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository ur;

    /**
     * Retrieves list of the users
     *
     * @return Response
     */
    @Override
    public Response getUsers() {
        Map<String, List<UserDetails>> usersMap = new HashMap<>();
        Response response;
        try {
            List<UserDetails> usersList = ur.getUsers();
            usersMap.put("users", usersList);
            response = new Response(HttpStatus.OK,
                    "users data is retrieved successfully", usersMap);
            if (usersList.isEmpty()) {
                response = new Response(HttpStatus.OK,
                        "no users found", usersMap);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public Response getUserDetailById(Long Id) {
        Map<String, List<UserDetails>> usersMap = new HashMap<>();
        Response response;
        try {
            if (ur.getUserDetailsById(Id).isEmpty()) {
                response = new Response(HttpStatus.OK,
                        "user not found", usersMap);
            } else {
                usersMap.put("user", ur.getUserDetailsById(Id));
                response = new Response(HttpStatus.OK,
                        "user data is retrieved successfully", usersMap);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return response;
    }

    @Override
    public UserDetails getUserDetailsById(Long Id) {
        Optional<UserDetails> ud1 = ur.findById(Id);
        return ud1.orElse(null);
    }

    @Override
    public void modifyUserProfile(UserDetails ud) {
        ur.save(ud);
    }

    @Override
    public void blockUser(Long Id) {
        Optional<UserDetails> ud1 = ur.findById(Id);
        if (ud1.isPresent()) {
            boolean status = ud1.get().getBlock();
            ud1.get().setBlock(!status);
            ur.save(ud1.get());
        }
    }

    @Override
    public Response createUser(UserDetails ud) {
        try {
            for (UserDetails userCheck : ur.getUsers()) {
                if (userCheck.getEmail().equals(ud.getEmail())) {
                    return new Response(HttpStatus.BAD_REQUEST, "user already exists", null);
                }
            }
            byte[] by = ud.getPassword().getBytes();
            String pw = DigestUtils.md5DigestAsHex(by);
            ud.setPassword(pw);
            ur.save(ud);
            return new Response(HttpStatus.OK,
                    "user is created successfully", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response deleteUser(Long id) {
        try {
            List<UserDetails> ud = ur.getUserDetailsById(id);
            if(ud.isEmpty()){
                return new Response(HttpStatus.NOT_FOUND,"user not found",null);
            }
            ur.delete(ud.get(0));
            return new Response(HttpStatus.OK, "user deleted successfully", null);
        }catch(Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public Response changePassword(String data) {
        try {
            String[] details = data.split(",");
            Long id= Long.parseLong(details[0].trim());
            List<UserDetails> ud = ur.getUserDetailsById(id);

            String oldPass=details[1].trim();
            String newPass= details[2].trim();

            byte[] by = oldPass.getBytes();
            String pw = DigestUtils.md5DigestAsHex(by);

            byte[] by1 = newPass.getBytes();
            String pw1 = DigestUtils.md5DigestAsHex(by1);

            if (ud.isEmpty()) {
                return new Response(HttpStatus.NOT_FOUND,"user not found",null);
            }else if(!ud.get(0).getPassword().equals(pw)){
                return new Response(HttpStatus.BAD_REQUEST,"old password doesn't match",null);
            }else if(ud.get(0).getPassword().equals(pw1)) {
                return new Response(HttpStatus.BAD_REQUEST,"new password cannot be same as old password",null);
            }else{
                ud.get(0).setPassword(pw1);
                ur.save(ud.get(0));
                return new Response(HttpStatus.OK,"password changed successfully",null);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
