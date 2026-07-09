package com.staj.rentacar.model;
import com.staj.rentacar.enums.VehicleType;

public class Motorcycle extends Vehicle {
    private final int engineCc;

    public Motorcycle(VehicleType type, String plate, String brand, String model, double dailyRentalPrice, double currentKm, String vehicleClass, int engineCc, double extraKmPrice, int currentRentalDays) { //motorcycle constructoru
        super(type, plate, brand, model, dailyRentalPrice, currentKm, vehicleClass, extraKmPrice, currentRentalDays);
        this.engineCc = engineCc;
    }

    //get methodu engineCc için(Daha sonra değişmeyeceği için set kullanılmadı)
    public int getEngineCc() {
        return engineCc;
    }

    @Override
    public int getMinAgeLimit() {
        return 18;
    }
}

