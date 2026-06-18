package model;
import enums.VehicleType;

public class Motorcycle extends Vehicle {
    private int engineCc;

    public Motorcycle(VehicleType type, String plate, String brand, String model, double dailyRentalPrice, double currentKm, String vehicleClass, int engineCc){ //motorcycle constructoru
        super(type, plate, brand, model, dailyRentalPrice, currentKm, vehicleClass);
        this.engineCc = engineCc;
    }

    //get set methodları engineCc için
    public int getEngineCc(){
        return engineCc;
    }
    public void setEngineCc(int engineCc){
        this.engineCc = engineCc;
    }

    @Override
    public int getMinAgeLimit(){
        return 18;
    }
    /*Cardaki method bodysinden farklı bir hesaplama eklenmeyecek ama sorumluluğu
    Vehicledan almak ve classlara paylaştırmak, ileride eklenecek özelliklerin kolaylığını sağlamak adına override edilecek*/
    @Override
    public double calculateRentalPrice(int days){
        return getDailyRentalPrice() * days;
    }

}
