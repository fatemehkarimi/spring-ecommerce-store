package com.example.ecommerce.service;

import com.example.ecommerce.dao.AddressDao;
import com.example.ecommerce.model.Address;
import com.example.ecommerce.web.dto.AddressDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressDao addressDao;

    @Override
    public Address save(AddressDto addressDto) {
        Address address = new Address(
            addressDto.getCity(),
            addressDto.getStreetAddress(),
            addressDto.getPostalCode(),
            addressDto.getUser()
        );
        return addressDao.save(address);
    }

    // @Override
    // public Address update(AddressDto addressDto) {

    // }
}
