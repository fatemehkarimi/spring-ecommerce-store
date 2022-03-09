package com.example.ecommerce.service;

import com.example.ecommerce.web.dto.UserSignupDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.example.ecommerce.dao.UserDao;
import com.example.ecommerce.model.User;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    UserServiceImpl(UserDao userDao) {
        super();
        this.userDao = userDao;
    }

    @Override
    public User save(UserSignupDto signupDto) {
        UUID id = UUID.randomUUID();
        
        User user = new User(
            id,
            signupDto.getFirstName(),
            signupDto.getLastName(),
            signupDto.getPhoneNumber(),
            signupDto.getEmail(),
            signupDto.getPassword()
            );
        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // User user = userDao.
        return null;
    }
}
