package com.staj.rentacar.exception;

public class VehicleNotInRentException extends RentalException {
    public VehicleNotInRentException(String message) {
        super(message);
    }
}
