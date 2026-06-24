package com.staj.rentacar.service;
import com.staj.rentacar.dto.RentalResult;
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
    public boolean addVehicle(Vehicle newVehicle) {
        // Araç veya plaka girilmediyse direkt reddedilecek
        if (newVehicle == null || newVehicle.getPlate() == null || newVehicle.getPlate().trim().isEmpty()) {
            return false;
        }

        // findVehicle null dönmüyorsa, bu araç parkingdedir, plaka vardır.
        // newVheicle.getPlate() findVehicle içine parametre olarak gönderilerek var olan plaka ve yeni eklenecek aracın plaka eşitliğini kontrol eder.
        if (findVehicle(newVehicle.getPlate()) != null) {
            return false; // Kontrol sonucu plakalar eşitse false döner.
        }

        // Plakalar eşit değil, araç ve plaka null değil yani eklenecek yeni araç
        parking.add(newVehicle);
        return true;
    }

    //araç bulma methodu (plakaya göre arama)
    public Vehicle findVehicle(String plate) {

        //eğer aranan plaka yoksa döngüde aranmaz false döner, methoddan çıkılır
        if (plate == null || plate.trim().isEmpty()){
            return null;
        }

        // Otoparktaki her bir aracı 'existingVehicle' adıyla tek tek geziyoruz
        for (Vehicle existingVehicle : parking) {

            //Elimizdeki mevcut aracın plakası, aranan plakaya eşit mi?
            if (existingVehicle.getPlate().equalsIgnoreCase(plate)) { //büyük, küçük harfden dolayı eşitleme sıkıntısı kaldırıldı(equalsIgnoreCase ile)
                return existingVehicle;//bulunan plaka döndürülür
            }
        }
        return null; // eğer parking tarandı ve plaka bulunmadıysa null döner
    }

    public RentalResult rentVehicle(String plate, int customerAge, int rentalDays){

        //Aracı bulup foundVehicle değişkenine atadık
        Vehicle foundVehicle = findVehicle(plate);

        //Aracın otoparka kayıtlı olup olmadığıı kontrol eder.
        if (foundVehicle == null) {
              return null;
        }

        //Araç zaten kirada ise kiralanamaz
        if (foundVehicle.getStatus() == VehicleStatus.RENTED) {
              return null;
        }

        //Müsterinin yaşı kiralamaya uygun değilse kiralayamaz
        if (customerAge < foundVehicle.getMinAgeLimit()) {
             return null;
        }

        if (rentalDays <= 0) {
             return null;
        }

        foundVehicle.setStatus(VehicleStatus.RENTED);
        double basePrice = foundVehicle.calculateRentalPrice((rentalDays)); //taban aşım olmadığı zamandaki ücretin hesaplanması için kullanıldı.
        return new RentalResult(plate, rentalDays, basePrice);
    }

    public boolean returnVehicle(RentalResult result, double endKm){

        //sonuç null ise, kiralama yapılamayacağı gibi araç teslim de alınamaz
        if(result == null){
            return false;
        }

        Vehicle returningVehicle = findVehicle(result.getPlate());

        //eğer otobark arandığında araba bulunamamışsa bu araba bizim değildir
        if(returningVehicle == null){
            return false;
        }

        //araba zaten kiralanmış durumdaysa kiralanamaz
        if(returningVehicle.getStatus() != VehicleStatus.RENTED){
            return false;
        }

        //eğer aracın ilk km si son km sinden küçükse hata verir.
        if(endKm < returningVehicle.getCurrentKm()){
            return false;
        }

        //gidilen km, en son teslim edildiği km den ilk başta kiralandığı km'yi çıkararak bulunuyor
        double drivenKm = endKm - returningVehicle.getCurrentKm();

        double totalKmLimit = 500.0;

        // gidilen km kiralanırken belirtilen limitten fazlaysa extra aşım hesaplanarak aşım ücretiyle çarpılıp fiyata eklenecek
        if(drivenKm > totalKmLimit) {
            double extraKm = drivenKm - totalKmLimit;
            double extraFee = extraKm * returningVehicle.getExtraKmPrice();
            result.addExtraKmFee(extraFee); // Method çağırılarak cezalı km başı tutarı faturaya yansıtılacak.
        }

        returningVehicle.setCurrentKm(endKm);
        returningVehicle.setStatus(VehicleStatus.AVAILABLE);
        return true;
    }
}

