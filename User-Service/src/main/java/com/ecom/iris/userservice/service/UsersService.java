package com.ecom.iris.userservice.service;

import com.ecom.iris.userservice.exception.DuplicateUserException;
import com.ecom.iris.userservice.exception.UserNotFoundException;
import com.ecom.iris.userservice.model.Users;
import com.ecom.iris.userservice.repository.UsersRepository;
import com.ecom.iris.userservice.request.UserRequest;
import com.ecom.iris.userservice.request.UserUpdateRequestDto;
import com.ecom.iris.userservice.response.Data;
import com.ecom.iris.userservice.response.UserResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService  {

    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    private  UsersRepository usersRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    /*@Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }*/

    public UserResponse registerUser(UserRequest userRequest) {
        logger.info("UserRequest request recieved  {} : ",userRequest);
        if(usersRepository.existsByEmail(userRequest.getEmail()) || usersRepository.existsByEmail(userRequest.getUsername())) {
            throw new DuplicateUserException("User with email or username already exists");
        }
        Users user = modelMapper.map(userRequest,Users.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Users newUser = usersRepository.save(user);
        Data data = modelMapper.map(newUser, Data.class);
        UserResponse userResponse = UserResponse.builder()
                .status("success")
                .message("User saved successfully")
                .data(data)
                .build();
        logger.info("User saved successfully {} : ",userResponse);
        return ResponseEntity.ok(userResponse).getBody();
    }

    public UserResponse getUserByUsername(String username) {
        Users user = usersRepository.findByUsername(username);
        if(user == null) {
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

    public UserResponse deleteuserByUserName(String username) {
        Users user = usersRepository.findByUsername(username);
        if(user == null) {
            return UserResponse.builder()
                    .status("error")
                    .message("User not found").data(null)
                    .build();
        }
        usersRepository.delete(user);
        return UserResponse.builder()
                .status("success")
                .message("User deleted successfully")
                .data(modelMapper.map(user, Data.class))
                .build();
    }

    @Transactional
    public UserResponse deleteUserByUserId(Long userId) {
        if(userId!=null){
            logger.info("userId  {} : ",userId);
            Users user = usersRepository.findByUserId(userId).orElse(null);
            if(user == null) {
                return UserResponse.builder()
                        .status("error")
                        .message("User not found").data(null)
                        .build();
            }
            usersRepository.delete(user);
            return UserResponse.builder()
                    .status("success")
                    .message("User deleted successfully")
                    .data(modelMapper.map(user, Data.class))
                    .build();
        }

        throw new UserNotFoundException("User not found with the userid : "+userId);

    }

    public UserResponse updateUserByUserName(String username, UserUpdateRequestDto userRequest) {
        Optional<Users> userExist = Optional.ofNullable(usersRepository.findByUsername(username));
        logger.info("userExists: ",userExist.get().toString());
       // Users updateUser =updateUser = userExist.get();
        if(userExist.isPresent()){
            logger.info("user exists: updating user details");
            if(userRequest.getFirstName()!=null){
                userExist.get().setFirstName(userRequest.getFirstName());
            }
            if(userRequest.getLastName()!=null){
                userExist.get().setLastName(userRequest.getLastName());
            }
            if(userRequest.getPhoneNumber()!=null){
                userExist.get().setPhoneNumber(userRequest.getPhoneNumber());
            }
            if(userRequest.getAddress()!=null){
                userExist.get().setAddress(userRequest.getAddress());
            }
            if(userRequest.getPassword()!=null){
                userExist.get().setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
            if(userRequest.getCity()!=null){
                userExist.get().setCity(userRequest.getCity());
            }
            if(userRequest.getCountry()!=null){
                userExist.get().setCountry(userRequest.getCountry());
            }
            if(userRequest.getPostalCode()!=null){
                userExist.get().setPostalCode(userRequest.getPostalCode());
            }
            if(userRequest.getState()!=null){
                userExist.get().setState(userRequest.getState());
            }
            //updateUser.setUserId(userExist.get().getUserId());
            usersRepository.save( userExist.get());
        }
        if(!userExist.isPresent()) {
            logger.info("user not exists:");
            return UserResponse.builder()
                    .status("error")
                    .message("User not found").data(null)
                    .build();
        }

        UserResponse userResponse = UserResponse.builder()
                .status("success")
                .message("User updated successfully")
                .data(modelMapper.map(userExist.get(), Data.class))
                .build();
        return userResponse;
    }


    public UserResponse updateUserByUserId(Long userId, UserUpdateRequestDto userRequest) {
        Optional<Users> userExist = usersRepository.findByUserId(userId);
        logger.info("userExists: ",userExist.get().toString());
        // Users updateUser =updateUser = userExist.get();
        if(userExist.isPresent()){
            logger.info("user exists: updating user details");
            if(userRequest.getFirstName()!=null){
                userExist.get().setFirstName(userRequest.getFirstName());
            }
            if(userRequest.getLastName()!=null){
                userExist.get().setLastName(userRequest.getLastName());
            }
            if(userRequest.getPhoneNumber()!=null){
                userExist.get().setPhoneNumber(userRequest.getPhoneNumber());
            }
            if(userRequest.getAddress()!=null){
                userExist.get().setAddress(userRequest.getAddress());
            }
            if(userRequest.getPassword()!=null){
                userExist.get().setPassword(passwordEncoder.encode(userRequest.getPassword()));
            }
            if(userRequest.getCity()!=null){
                userExist.get().setCity(userRequest.getCity());
            }
            if(userRequest.getCountry()!=null){
                userExist.get().setCountry(userRequest.getCountry());
            }
            if(userRequest.getPostalCode()!=null){
                userExist.get().setPostalCode(userRequest.getPostalCode());
            }
            if(userRequest.getState()!=null){
                userExist.get().setState(userRequest.getState());
            }
            //updateUser.setUserId(userExist.get().getUserId());
            usersRepository.save( userExist.get());
        }
        if(!userExist.isPresent()) {
            logger.info("user not exists:");
            return UserResponse.builder()
                    .status("error")
                    .message("User not found").data(null)
                    .build();
        }

        UserResponse userResponse = UserResponse.builder()
                .status("success")
                .message("User updated successfully")
                .data(modelMapper.map(userExist.get(), Data.class))
                .build();
        return userResponse;
    }
}
