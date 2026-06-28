package com.staj.rentacar.exception;

public class VehicleAlreadyRentedException extends RentalException {
    public VehicleAlreadyRentedException(String message) {
        super(message);
    }
}
