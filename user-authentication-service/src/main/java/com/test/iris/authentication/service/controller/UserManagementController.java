/*
package com.test.iris.authentication.service.controller;

import com.test.iris.authentication.service.request.UserRequest;
import com.test.iris.authentication.service.response.UserResponse;
import com.test.iris.authentication.service.service.UserManagementService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/users")
public class UserManagementController {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("/username/{username}")
    public ResponseEntity<?> fetchUserByUserName(@PathVariable("username") String username){
        logger.info("username  {} : ",username);
        UserResponse userResponse= userManagementService.getUserByUsername(username);
        logger.info("Fetchuser By Username : {} : ",userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/userId/{userId}")
    public ResponseEntity<?> fetchUserByUserId(@PathVariable("userId") Long userId){
        logger.info("username  {} : ",userId);
        UserResponse userResponse= userManagementService.getUserById(userId);
        logger.info("Fetchuser By Username : {} : ",userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping("username/{username}")
    public ResponseEntity<?> deleteUserByUserName(@PathVariable("username") String username){
        logger.info("username  {} : ",username);
        UserResponse userResponse= userManagementService.deleteuserByUserName(username);
        logger.info("deleted user with  Username : {} : ",username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userResponse);
    }

    @PutMapping("username/{username}")
    public ResponseEntity<?> updateUserByUserName(@PathVariable("username") String username,@Valid @RequestBody UserRequest userRequest){
        logger.info("username  {} : ",username);
        logger.info("update user request {}",userRequest);
        UserResponse userResponse= userManagementService.updateUserByUserName(username,userRequest);
        logger.info("updated user with  Username : {} : ",username);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }


    @GetMapping("/test")
    public String  testApi(){
        logger.info("Test is working fine");
        return "test";
    }
}
*/
