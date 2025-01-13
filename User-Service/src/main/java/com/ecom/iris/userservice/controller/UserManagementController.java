package com.ecom.iris.userservice.controller;


import com.ecom.iris.userservice.request.UserRequest;
import com.ecom.iris.userservice.request.UserUpdateRequestDto;
import com.ecom.iris.userservice.response.UserResponse;
import com.ecom.iris.userservice.service.UsersService;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "User Management",
        description = "APIs for managing user operations")
public class UserManagementController {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);

    @Autowired
    private  UsersService usersService;

    @Operation(summary = "Get user by username", description = "Fetch a user by their unique username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> fetchUserByUserName(@NotNull   @PathVariable("username") String username){
        logger.info("username  {} : ",username);
        UserResponse userResponse= usersService.getUserByUsername(username);
        logger.info("Fetchuser By Username : {} : ",userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Operation(summary = "Get user by userId", description = "Fetch a user by their unique userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/userId/{userId}")
    public ResponseEntity<UserResponse> fetchUserByUserId(@NotNull  @PathVariable("userId") Long userId){
        logger.info("username  {} : ",userId);
        UserResponse userResponse= usersService.getUserById(userId);
        logger.info("Fetchuser By Username : {} : ",userResponse);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
    @Operation(summary = "Update user by userId", description = "Update a user by their unique userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/userId/{userId}")
    public ResponseEntity<UserResponse> updateUserByUserId(@NotNull  @PathVariable("userId") Long userId, @Valid @RequestBody UserUpdateRequestDto userRequest){
        logger.info("userId  {} : ",userId);
        logger.info("update user request {}",userRequest);
        UserResponse userResponse= usersService.updateUserByUserId(userId,userRequest);
        logger.info("updated user with  userId : {} : ",userId);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Operation(summary = "Update user by username", description = "Update a user by their unique username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("username/{username}")
    public ResponseEntity<UserResponse> updateUserByUserName(@NotNull @PathVariable("username") String username, @Valid @RequestBody UserUpdateRequestDto userRequest){
        logger.info("username  {} : ",username);
        logger.info("update user request {}",userRequest);
        UserResponse userResponse= usersService.updateUserByUserName(username,userRequest);
        logger.info("updated user with  Username : {} : ",username);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @Operation(summary = "Delete user by username", description = "Delete a user by their unique username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("username/{username}")
    public ResponseEntity<UserResponse> deleteUserByUserName(@NotNull @PathVariable("username") String username){
        logger.info("username  {} : ",username);
        UserResponse userResponse= usersService.deleteuserByUserName(username);
        logger.info("deleted user with  Username : {} : ",username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userResponse);
    }
    @Operation(summary = "Delete user by userId", description = "Delete a user by their unique userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("userId/{userId}")
    public ResponseEntity<UserResponse> deleteUserByUserId(@NotNull @PathVariable("userId") Long userId){
        logger.info("userId  {} : ",userId);
        UserResponse userResponse= usersService.deleteUserByUserId(userId);
        logger.info("deleted user with  userId : {} : ",userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userResponse);
    }


    @GetMapping("/test")
    public String  testApi(){
        logger.info("Test is working fine");
        return "test";
    }
}
