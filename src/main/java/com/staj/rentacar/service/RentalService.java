package com.staj.rentacar.service;
import com.staj.rentacar.enums.VehicleStatus;
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
    public boolean addVehicle(Vehicle newVehicle){

        //Otoparktaki mevcut araçları tek tek geziyoruz
        for(Vehicle existingVehicle : parking){

            //Eğer var olan aracın plakası yeni gelen aracın plakasıyla aynıysa (hata)
            if(existingVehicle.getPlate().equals(newVehicle.getPlate())) {
                return false;
            }
        }

        // Java'da bir listenin içine eleman eklemek için .add() komutu kullanılıyor.
        parking.add(newVehicle);
        return true; //plaklar aynı değil araç eklenebilir
    }

    //araç bulma methodu (plakaya göre arama)
    public Vehicle findVehicle(String plate) {

        // Otoparktaki her bir aracı 'existingVehicle' adıyla tek tek geziyoruz
        for (Vehicle existingVehicle : parking) {

            //Elimizdeki mevcut aracın plakası, aranan plakaya eşit mi?
            if (existingVehicle.getPlate().equals(plate)) {
                return existingVehicle;
            }
        }
        return null; // eğer plaka bulunmadıysa null döner
    }

    public RentalResult rentVehicle(String plate, int customerAge, int rentalDays){ // rentalResult 'u henüz yazamadım yazacağım 2.aşama tamamlanmış olacak
        for(Vehicle existingVehicle : parking) {

            //Araç kiradaysa zaten kiralanamaz
            if (existingVehicle.getPlate().equals(plate)) {

                if (existingVehicle.getStatus() == VehicleStatus.RENTED) {
                    return null;
                }

                //Müsterinin yaşı kiralamaya uygun değilse kullanamaz
                if (customerAge < existingVehicle.getMinAgeLimit()) {
                    return null;
                }

                if (rentalDays <= 0) {
                    return null;
                }
                existingVehicle.setStatus(VehicleStatus.RENTED);
                double totalPrice = existingVehicle.calculateRentalPrice(rentalDays);
                return new RentalResult(plate, rentalDays, totalPrice);

            }
        }
            return null;
    }

}
