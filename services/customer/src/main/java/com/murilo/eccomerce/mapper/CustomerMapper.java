package com.murilo.eccomerce.mapper;

import com.murilo.eccomerce.dto.CustomerRequest;
import com.murilo.eccomerce.dto.CustomerResponse;
import com.murilo.eccomerce.entity.Customer;

public class CustomerMapper {

    public CustomerResponse fromCustomer(Customer customer) {
        return new CustomerResponse(customer.getFirstname(), customer.getLastname(), customer.getEmail(), customer.getAddress());
    }

    public Customer toCustomer(CustomerRequest request) {
        return Customer.builder()
                .id(request.id())
                .firstname(request.firstname())
                .lastname(request.lastname())
                .email(request.email())
                .address(request.address())
                .build();
    }
}
