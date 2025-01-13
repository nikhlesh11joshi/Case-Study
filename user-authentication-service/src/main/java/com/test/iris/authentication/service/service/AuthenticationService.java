package com.test.iris.authentication.service.service;

//import com.test.iris.authentication.service.entity.UserCredentials;
import com.test.iris.authentication.service.entity.Users;
//import com.test.iris.authentication.service.repository.UserCredentialRepository;
import com.test.iris.authentication.service.exception.DuplicateUserException;
import com.test.iris.authentication.service.repository.UsersRepository;
import com.test.iris.authentication.service.request.UserRequest;
import com.test.iris.authentication.service.response.Data;
import com.test.iris.authentication.service.response.TokenResponse;
import com.test.iris.authentication.service.response.UserResponse;
import com.test.iris.authentication.service.response.ValidateTokenResponse;
import com.test.iris.authentication.service.util.JwtService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${jwt.expiration}")
    private int JwtExpirationMs;


    public UserResponse saveUser(UserRequest userRequest) {
        /*userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        userCredentialRepository.save(userCredentials);
        return "User saved successfully";*/
        logger.info("UserRequest request recieved  {} : ",userRequest);
        if(usersRepository.existsByEmail(userRequest.getEmail()) || usersRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateUserException("User with email or username already exists");
        }
        Users user = modelMapper.map(userRequest,Users.class);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        Users newUser = usersRepository.save(user);
        Data data = modelMapper.map(newUser, Data.class);
        UserResponse userResponse = new UserResponse("success","User saved successfully",data);
        logger.info("User saved successfully {} : ",userResponse);
        return ResponseEntity.ok(userResponse).getBody();
    }

    public ResponseEntity<TokenResponse> generateToken(String userName){
        logger.info("User Authenticated  generate token for user {} : ",userName);
        TokenResponse tokenResponse = new TokenResponse(jwtService.generateToken(userName), LocalDateTime.now().plusSeconds(JwtExpirationMs));
         if(tokenResponse.getToken()!=null){
             logger.info("Token generated successfully for user {} : ",userName);
             return new ResponseEntity<>(tokenResponse, HttpStatusCode.valueOf(200));
         }   else {
             logger.info("Token not generated for user {} : ",userName);
             //throw new RuntimeException("Token not generated for user or token expired : "+userName);
                return new ResponseEntity<>(tokenResponse, HttpStatusCode.valueOf(404));
         }
    }


    public ResponseEntity<ValidateTokenResponse> validateToken(String token){
        ValidateTokenResponse validateTokenResponse=null;
        logger.info("Token validation recieved  {} : ",jwtService.validateJwtToken(token));
        if(jwtService.validateJwtToken(token)){
            logger.info("Token is valid: encrypted token value"+passwordEncoder.encode(token));
             validateTokenResponse = new ValidateTokenResponse("Success","Token is valid",LocalDateTime.now().plusSeconds(JwtExpirationMs));
            return new ResponseEntity<>(validateTokenResponse, HttpStatusCode.valueOf(200));
        }
            logger.info("Token is invalid: token value"+token);
            validateTokenResponse = new ValidateTokenResponse("Error","Token is not valid ",LocalDateTime.now().plusSeconds(JwtExpirationMs));
            return new ResponseEntity<>(validateTokenResponse, HttpStatusCode.valueOf(404));


    }
}
