package com.staj.rentacar.exception;

public class InvalidRentalResultException extends RentalException { //result==null için onu return false yerine hata teşkil ettiği için runtime exception ile kullanacağım
    public InvalidRentalResultException(String message) {
        super(message);
    }
}
