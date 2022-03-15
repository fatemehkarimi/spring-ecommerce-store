package com.example.ecommerce.service;

import java.io.IOException;
import java.io.PrintWriter;

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

    @Override
    public Address update(long id, AddressDto addressDto) {
        Address address = addressDao.getById(id);

        String city = addressDto.getCity() == null || addressDto.getCity().isEmpty()
            ? address.getCity()
            : addressDto.getCity();

        String streetAddress = addressDto.getStreetAddress() == null
            || addressDto.getStreetAddress().isEmpty()
            ? address.getStreetAddress()
            : addressDto.getStreetAddress();

        String postalCode = addressDto.getPostalCode() == null
            || addressDto.getPostalCode().isEmpty()
            ? address.getPostalCod()
            : addressDto.getPostalCode();

            try {
                PrintWriter p = new PrintWriter("out.txt", "UTF-8");
                p.println(id);
                p.println(city);
                p.println(streetAddress);
                p.println(postalCode);
                p.close();
            } catch(IOException e) {}

        Address newAddress = new Address(
            id,
            city,
            streetAddress,
            postalCode,
            addressDto.getUser()
        );

        return addressDao.save(newAddress);
    }
}
