/*
package com.test.iris.authentication.service.service;

import com.test.iris.authentication.service.entity.Users;
import com.test.iris.authentication.service.repository.UsersRepository;
import com.test.iris.authentication.service.request.UserRequest;
import com.test.iris.authentication.service.response.Data;
import com.test.iris.authentication.service.response.UserResponse;
import com.test.iris.authentication.service.util.JwtService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserManagementService {

    private static final Logger logger = LoggerFactory.getLogger(UserManagementService.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse getUserByUsername(String username) {
        Optional<Users> user = usersRepository.findByUsername(username);
        if(!user.isPresent()) {
            return UserResponse.builder()
                    .status("error")
                    .message("User not found").data(null)
                    .build();
        }
        Data data = modelMapper.map(user, Data.class);
        UserResponse userResponse = UserResponse.builder()
                .status("success")
                .message("User fetched successfully")
                .data(modelMapper.map(user, Data.class))
                .build();
        return userResponse;
    }

    public UserResponse getUserById(Long id) {
        Users user = usersRepository.findById(id).orElse(null);
        if(user == null) {
            return UserResponse.builder()
                    .status("error")
                    .message("User not found").data(null)
                    .build();
        }
        return UserResponse.builder()
                .status("success")
                .message("User fetched successfully")
                .data(modelMapper.map(user, Data.class))
                .build();
    }

    */
/* public ResponseEntity<?> updateUser(Users user) {
         Users updatedUser = usersRepository.save(user);
         return ResponseEntity.ok(updatedUser);
     }*//*

    public ResponseEntity<?> deleteUser(Long id) {
        usersRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
    public UserResponse deleteuserByUserName(String username) {
        Optional<Users> user = usersRepository.findByUsername(username);
        if(user.isPresent()) {
            return UserResponse.builder()
                    .status("error")
                    .message("User not found").data(null)
                    .build();
        }
        usersRepository.delete(user.get());
        return UserResponse.builder()
                .status("success")
                .message("User deleted successfully")
                .data(modelMapper.map(user, Data.class))
                .build();
    }

    public UserResponse updateUserByUserName(String username, UserRequest userRequest) {
        Optional<Users> userExist = usersRepository.findByUsername(username);
        Users updateUser =null;
        if(userExist.isPresent()){
            updateUser = userExist.get();
            updateUser.setUserId(userExist.get().getUserId());
            updateUser.setUsername(userRequest.getUsername());
            updateUser.setEmail(userRequest.getEmail());
            updateUser.setFirstName(userRequest.getFirstName());
            updateUser.setLastName(userRequest.getLastName());
            updateUser.setPhoneNumber(userRequest.getPhoneNumber());
            updateUser.setAddress(userRequest.getAddress());
            updateUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            usersRepository.save(updateUser);
        }
        if(userExist == null || !(userExist.isPresent())) {
            return UserResponse.builder()
                    .status("error")
                    .message("User not found").data(null)
                    .build();
        }

        UserResponse userResponse = UserResponse.builder()
                .status("success")
                .message("User updated successfully")
                .data(modelMapper.map(updateUser, Data.class))
                .build();
        return userResponse;
    }
}
*/
