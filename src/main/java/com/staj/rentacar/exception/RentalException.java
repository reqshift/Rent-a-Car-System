package com.staj.rentacar.exception;

public class RentalException extends RuntimeException { //maindeki exception yapısını toplar
    public RentalException(String message) {
        super(message);
    }
}
