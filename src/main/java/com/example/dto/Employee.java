package com.example.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class Employee implements Serializable {
    long id;
    String name;
}
