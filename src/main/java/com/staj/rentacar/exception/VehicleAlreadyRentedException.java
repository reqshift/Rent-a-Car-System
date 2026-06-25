package com.staj.rentacar.exception;

public class VehicleAlreadyRentedException extends RuntimeException {
    public VehicleAlreadyRentedException(String message) {
        super(message);
    }
}
