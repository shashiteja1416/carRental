package com.carRental.User.controller;

import com.carRental.User.model.Response;
import com.carRental.User.model.UserDetails;
import com.carRental.User.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = { "user-controller" })
@SwaggerDefinition(tags = { @Tag(name = "user-controller", description = "Users controller") })
public class UserController {

    @Autowired
    private UserService service;

    @ApiOperation(value = "get all users")
    @GetMapping("/ListUsers")
    public Response getUsers() {
        return this.service.getUsers();
    }

    @ApiOperation(value = "get user by Id")
    @GetMapping("/userId/{Id}")
    public Response getUserDetailById(@PathVariable Long Id){
        return this.service.getUserDetailById(Id);
    }

    @ApiOperation(value="create a user")
    @PostMapping("/createUser")
    public Response createUser(@RequestBody UserDetails ud){
        return this.service.createUser(ud);
    }

    @ApiOperation(value="delete a user")
    @DeleteMapping("/deleteUser")
    public Response deleteUser(@RequestBody String id){
        return this.service.deleteUser(Long.parseLong(id));
    }

    @ApiOperation(value="change password")
    @PutMapping("/changePassword")
    public Response changePassword(@RequestBody String details){
        return this.service.changePassword(details);
    }

    @ApiOperation(value = "updating user details")
    @PutMapping("/editProfile")
    public Response modifyProfile(@RequestBody UserDetails ud){
        UserDetails dbUser = service.getUserDetailsById(ud.getUserId());
        if(dbUser != null){
            service.modifyUserProfile(ud);
            Map<String, UserDetails> mp = new HashMap();
            mp.put("data",ud);
            Response r = new Response(HttpStatus.OK,"user updated successfully",mp);
            return  r;
        }
    return  null;
    }

    @ApiOperation(value=" block a user")
    @PutMapping("/blockUser")
    public Response blockUser(@RequestBody String Id){
        UserDetails dbUser = service.getUserDetailsById(Long.parseLong(Id));
        try{
        if(dbUser != null) {
            service.blockUser(Long.parseLong(Id));
            return new Response(HttpStatus.OK,"user block status updated",null);
        }
    } catch(Exception e){
            e.printStackTrace();
        }
        return  null;
    }
}
