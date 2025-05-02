package com.example.project.dto;

import org.springframework.http.HttpStatus;

public record ResponseDto(String message, HttpStatus httpStatus) {}
