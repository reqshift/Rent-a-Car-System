package com.staj.rentacar.exception;

public class VehicleNotInRentException extends RuntimeException {
    public VehicleNotInRentException(String message) {
        super(message);
    }
}
