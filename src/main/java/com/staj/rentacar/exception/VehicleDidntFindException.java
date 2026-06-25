package com.staj.rentacar.exception;

public class VehicleDidntFindException extends RuntimeException {
    public VehicleDidntFindException(String message) {
        super(message);
    }
}
