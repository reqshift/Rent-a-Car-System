package com.staj.rentacar.model;
import com.staj.rentacar.enums.VehicleType;

//inheritance kullanrak Vehicle'deki methodlar ve variableler miras alındı (Abstract methodlar override edilmek zorunda)
public class Car extends Vehicle {
    private final String gearType;

    public Car(VehicleType type, String plate, String brand, String model, double dailyRentalPrice, double currentKm, String vehicleClass, String gearType){ //car class'ı constructoru
        super(type, plate, brand, model, dailyRentalPrice, currentKm, vehicleClass);
        this.gearType = gearType;
    }

    //gearType için get methodu(Değiştirilmesine gerek yok bu yüzden set kullanılmadı
    public String getGearType(){
        return gearType;
    }

    //override edilen methodlar
    @Override //abstract üst sınıftaki yaş methodu burada yeniden yapılandırılıyor (Polymorphism)
    public int getMinAgeLimit(){
        return 21;
    }

}
