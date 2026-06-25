package com.staj.rentacar.exception;

//Exception sınıflarının işi, iş kuralını temsil etmektir. İş mantığını yazmak değildir.
public class InsufficientDrivingAgeException extends RuntimeException { //runtimeExceptiondan miras alıyor.

    public InsufficientDrivingAgeException(String message) { //bu classın consturctoru bir mesaj alıyor hata için
        super(message);//RuntimeException'un constructorunu çağırıyor
    }
}
