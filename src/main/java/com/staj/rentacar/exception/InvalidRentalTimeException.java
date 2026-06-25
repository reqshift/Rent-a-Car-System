package com.staj.rentacar.exception;

public class InvalidRentalTimeException extends RuntimeException {
    public InvalidRentalTimeException(String message) {
        super(message);
    }
}
