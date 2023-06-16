package com.example.medicalshop.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDTO {
    private String name;
    private String province;
    private String city;
    private String address;
    private Long zipcode;
    private String number;
}
