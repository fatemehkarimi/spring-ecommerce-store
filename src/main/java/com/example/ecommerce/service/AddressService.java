package com.example.ecommerce.service;

import com.example.ecommerce.model.Address;
import com.example.ecommerce.web.dto.AddressDto;

public interface AddressService {
    public Address save(AddressDto addressDto);
    public Address update(long id, AddressDto addressDto);
}
