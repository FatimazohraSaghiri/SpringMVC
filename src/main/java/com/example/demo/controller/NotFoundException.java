package com.example.demo.controller;

public class NotFoundException extends RuntimeException {
    NotFoundException(String exp) {
        super(exp);
    }
}
