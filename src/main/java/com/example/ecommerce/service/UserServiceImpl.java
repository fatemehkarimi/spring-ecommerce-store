package com.example.ecommerce.service;

import java.util.UUID;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.User;
import com.example.ecommerce.web.dto.UserSignupDto;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    // UserServiceImpl(UserDao userDao) {
    //     super();
    //     this.userDao = userDao;
    // }

    @Override
    public User save(UserSignupDto signupDto) {
        UUID id = UUID.randomUUID();
        
        User user = new User(
            id,
            signupDto.getFirstName(),
            signupDto.getLastName(),
            signupDto.getPhoneNumber(),
            signupDto.getEmail(),
            passwordEncoder.encode(signupDto.getPassword())
            );
        return userDao.save(user);
    }

    @Override
    public User update(UserSignupDto updatedUser) {
        User user = userDao.findByEmail(updatedUser.getEmail());

        String firstName = 
            updatedUser.getFirstName() == null || updatedUser.getFirstName().isEmpty()
            ? user.getFirstName()
            : updatedUser.getFirstName();

        String lastName =
            updatedUser.getLastName() == null || updatedUser.getLastName().isEmpty()
            ? user.getLastName()
            : updatedUser.getLastName();

        String phoneNumber =
            updatedUser.getPhoneNumber() == null || updatedUser.getPhoneNumber().isEmpty()
            ? user.getPhoneNumber()
            : updatedUser.getPhoneNumber();
        
        String password = 
            updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()
            ? user.getPassword()
            : passwordEncoder.encode(updatedUser.getPassword());

        User savedUser = new User(
            user.getId(),
            firstName,
            lastName,
            phoneNumber,
            user.getEmail(),
            password
        );

        return userDao.save(savedUser);
    }

    @Override
    public UserDetails loadUserByUsername(String userName)
        throws UsernameNotFoundException
    {
        User user = userDao.findByEmail(userName);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid email or password");
        }

        return 
            new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new HashSet<GrantedAuthority>() );
    }
}
