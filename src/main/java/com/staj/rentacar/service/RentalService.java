package com.staj.rentacar.service;
import com.staj.rentacar.model.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class RentalService {
    //araç ekleme (Aynı plaka ile sadece bir araç eklenir)
    // araç bulma (plakadan bulunabilir)
    // araç kiralama (Araç kiradaysa zaten tekrar kiralanamamalı, müsteri yası belirtilenden küçükse de kiralanmaz)
    // araç teslim alma methodları bulundurulacak (kiralama gün sayısı geçerli olacak)
    // (Amaç mainin yükünü azaltmak)

    /* Dijital otoparkımızı oluşturduk 'parking' ile. Tüm arabaları ve motorları bu listenin içine atacağız.
     'Vehicle' yazdık çünkü polymorphism sayesinde bu liste hem Car hem de Motorcycle nesnelerini ortaklaşa kabul edebilir. */
    private final List<Vehicle> parking = new ArrayList<>();

    //araç ekleme methodu
    public void addVehicle(Vehicle newVehicle){

        //Otoparktaki mevcut araçları tek tek geziyoruz
        for(Vehicle existingVehicle : parking){

            //Eğer var olan aracın plakası yeni gelen aracın plakasıyla aynıysa
            if(existingVehicle.getPlate().equals(newVehicle.getPlate())){
                System.out.println("[ERROR]: " + newVehicle.getPlate() + "brand already in the system");
                return;
            }
        }

        // Java'da bir listenin içine eleman eklemek için .add() komutu kullanılıyor.
        parking.add(newVehicle);
        System.out.println("Vehicle add to parking successfully, added plate: " + newVehicle.getPlate());
    }

    //araç bulma methodu (plakaya göre arama)
    public Vehicle findVehicle(String plate) {

        // Otoparktaki her bir aracı 'existingVehicle' adıyla tek tek geziyoruz
        for (Vehicle existingVehicle : parking) {

            //Elimizdeki mevcut aracın plakası, aranan plakaya eşit mi?
            if (existingVehicle.getPlate().equals(plate)) {
                System.out.println("[INFO]: Searching vehicle is found");
                return existingVehicle;
            }
        }

        return null; // eğer plaka bulunmadıysa null döner
    }

    public void rentingVehicle(){
        //içi doldurulacak 23.06 da
    }

}
