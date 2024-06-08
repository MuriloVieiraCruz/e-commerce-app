package com.murilo.eccomerce.service;

import com.murilo.eccomerce.dto.CustomerRequest;
import com.murilo.eccomerce.dto.CustomerResponse;
import com.murilo.eccomerce.entity.Customer;
import com.murilo.eccomerce.exception.CustomerNotFoundException;
import com.murilo.eccomerce.mapper.CustomerMapper;
import com.murilo.eccomerce.mapper.GenericMapper;
import com.murilo.eccomerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(Objects.requireNonNull(GenericMapper.map(request, Customer.class)));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(format("Cannot update customer:: No customer found with the provided ID::  %s", request.id())));
        mergeCustomer(customer, request);
        customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstname())) {
            customer.setFirstname(request.firstname());
        }
        if (StringUtils.isNotBlank(request.lastname())) {
            customer.setLastname(request.lastname());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(customerMapper::fromCustomer)
                .toList();
    }

    public Boolean existsById(String customerId) {
        return customerRepository.findById(customerId).isPresent();
    }

    public CustomerResponse findCustomerById(String customerId) {
        return customerRepository
                .findById(customerId)
                .map(customerMapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(format("No customer found with the provided ID::  %s", customerId)));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }
}
