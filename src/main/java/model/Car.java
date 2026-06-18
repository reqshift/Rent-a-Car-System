package model;
import enums.VehicleType;

//inheritance kullanrak Vehicle'deki methodlar ve variableler miras alındı (Abstract methodlar override edilmek zorunda)
public class Car extends Vehicle {
    private String gearType;

    public Car(VehicleType type, String plate, String brand, String model, double dailyRentalPrice, double currentKm, String vehicleClass, String gearType){ //car class'ı constructoru
        super(type, plate, brand, model, dailyRentalPrice, currentKm, vehicleClass);
        this.gearType = gearType;
    }

    //gearType için get ve set methodları
    public String getGearType(){
        return gearType;
    }
    public void setGearType(String gearType) {
        this.gearType = gearType;
    }

    //override edilen methodlar
    @Override //abstract üst sınıftaki yaş methodu burada yeniden yapılandırılıyor (Polymorphism)
    public int getMinAgeLimit(){
        return 21;
    }

    @Override //toplam kiralama bedeli hesaplanması için günlük kira ücreti(jsonda) ile kiralanan "gün" sayısı çarpıldı
    public double calculateRentalPrice(int days){
        return getDailyRentalPrice() * days;
    }
}
