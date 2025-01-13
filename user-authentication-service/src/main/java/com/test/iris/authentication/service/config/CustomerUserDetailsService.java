package com.test.iris.authentication.service.config;

//import com.test.iris.authentication.service.entity.UserCredentials;
import com.test.iris.authentication.service.entity.Users;
//import com.test.iris.authentication.service.repository.UserCredentialRepository;
import com.test.iris.authentication.service.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomerUserDetailsService implements UserDetailsService {

    /*@Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> credential = repository.findByName(username);
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }*/

    /* @Autowired
    private UserCredentialRepository repository;*/


    @Autowired
    private UsersRepository repository;
   /* @Autowired
    private ModelMapper modelMapper;*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> credential = repository.findByUsername(username);
        //UserCredentials userCredentials = modelMapper.map(credential.get(), UserCredentials.class);
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
    }
}
