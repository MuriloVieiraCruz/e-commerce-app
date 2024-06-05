package com.murilo.eccomerce.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document
public class Customer {

    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Address address;
}
