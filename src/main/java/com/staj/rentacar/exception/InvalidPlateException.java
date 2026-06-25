package com.staj.rentacar.exception;

public class InvalidPlateException extends RuntimeException { //geçersiz girilen plaka değerleri vs için oluşturuldu
    public InvalidPlateException(String message) {
        super(message);
    }
}
