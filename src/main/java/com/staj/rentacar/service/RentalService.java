package com.staj.rentacar.service;
import com.staj.rentacar.dto.RentalResult;
import com.staj.rentacar.enums.VehicleStatus;
import com.staj.rentacar.exception.*;
import com.staj.rentacar.model.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class RentalService {

    private final List<Vehicle> parking;

    /* Dijital otoparkımızı oluşturduk 'parking' ile. Tüm arabaları ve motorları bu listenin içine atacağız.
     'Vehicle' yazdık çünkü polymorphism sayesinde bu liste hem Car hem de Motorcycle nesnelerini ortaklaşa kabul edebilir. */

    public RentalService(List<Vehicle> parking){ //mainden gelen vehiclesi almak için parkinge eşitleyen constructor
        if(parking == null){
            throw new IllegalArgumentException("Parking must not be null.");
        }
        this.parking = parking;
    }

    private static final double KM_LIMIT = 500.0; //double totalKmLimit = 500.0; den değiştirildi çünkü static o nesneden 1 tane oluşturulmasına izin veriyor, aynı main methodunda kullanılan static gibi

    //araç ekleme methodu
    public boolean addVehicle(Vehicle newVehicle) {

        if (newVehicle == null || newVehicle.getPlate() == null || newVehicle.getPlate().trim().isEmpty()) { // Araç veya plaka girilmediyse direkt reddedilecek
            throw new InvalidPlateException("Vehicle and its plate must be provided (ex:06ABC123");
        }

        for(Vehicle existingVehicle : parking){
            if(existingVehicle.getPlate().equalsIgnoreCase(newVehicle.getPlate())) {
               throw new DuplicatePlateException("A vehicle with this plate already exists.");
            }
        }

        //findVehicle(newVehicle.getPlate()) != null silindi ve eski parkingde arama plaka eşitleme yönetmi addVehicleye yeniden ekledi, findVehicle kaldırıldı
        //Bunun sebebi findVehicle' nin araç bulunamayınca exception atmasıdır, addVehiclede exception atılmamalı, asıl araç bulunmazsa plaka kontrolü yapılıp eklenmeli

        // Plakalar eşit değil, araç ve plaka null değil yani eklenecek yeni araç
        parking.add(newVehicle);
        return true;
    }

    //araç bulma methodu (plakaya göre arama)
    public Vehicle findVehicle(String plate) {

        //eğer aranan plaka yoksa döngüde aranmaz false döner, methoddan çıkılır
        if (plate == null || plate.trim().isEmpty()){
            throw new InvalidPlateException("Plate must be entered correctly (ex: 06ABC123)"); // plaka değeri geçersiz girildiyse null yazılır, plaka bulunamadı exceptionu kullanılamaz,geçersiz değer exceptionu kullanılır

        }

        // Otoparktaki her bir aracı 'existingVehicle' adıyla tek tek geziyoruz
        for (Vehicle existingVehicle : parking) {

            //Elimizdeki mevcut aracın plakası, aranan plakaya eşit mi?
            if (existingVehicle.getPlate().equalsIgnoreCase(plate)) { //büyük, küçük harfden dolayı eşitleme sıkıntısı kaldırıldı(equalsIgnoreCase ile)
                return existingVehicle;//bulunan plaka döndürülür
            }
        }
    throw new VehicleNotFoundException("No vehicle found with the given plate."); //otopark taranıp araç bulunamazsa exception yazarız araç bulunmadı diye

    }

    public RentalResult rentVehicle(String plate, int customerAge, int rentalDays){

        //Aracı bulup foundVehicle değişkenine atadık
        Vehicle foundVehicle = findVehicle(plate);

        //foundVehice==null 'u sildim çünkü findVehicle(plate); ataması yapılınca zaten plaka bulunamazsa VehicleNotFoundException çalışır ve null değer geçmemiş olur kiralamaya

        //Araç zaten kirada ise kiralanamaz
        if (foundVehicle.getStatus() == VehicleStatus.RENTED) {
              throw new VehicleAlreadyRentedException("Vehicle already rented, you cannot rent");
        }

        //Müsterinin yaşı kiralamaya uygun değilse kiralayamaz
        if (customerAge < foundVehicle.getMinAgeLimit()) {
              throw new InsufficientDrivingAgeException("Customer's age isn't enough to rent vehicle");
        }

        if (rentalDays <= 0) {
             throw new InvalidRentalTimeException("Rental days must be larger than 0 for renting");
        }

        foundVehicle.setStatus(VehicleStatus.RENTED);
        double basePrice = foundVehicle.calculateRentalPrice((rentalDays)); //taban aşım olmadığı zamandaki ücretin hesaplanması için kullanıldı.
        return new RentalResult(plate, rentalDays, basePrice);
    }

    public boolean returnVehicle(RentalResult result, double endKm){

        //sonuç null ise, kiralama yapılamayacağı gibi araç teslim de alınamaz
        if(result == null){
            throw new InvalidRentalResultException("Rental result is required to return a vehicle");//exception atılamaz çünkü bu araç bulunamadı hatası değildir, rentalResult sonucunda result null dönerse result yoktur.
        }

        Vehicle returningVehicle = findVehicle(result.getPlate());

        ///returningVehicle==null silindi çükü findVehicle zatn eğer plate bulunamadıysa null durumunda exception atıyor

        //arabakiralanmış durumda değilse geri alınamaz
        if(returningVehicle.getStatus() != VehicleStatus.RENTED){
            throw new VehicleNotInRentException("Vehicle is not currently rented.");
        }

        //eğer aracın ilk km si son km sinden küçükse hata verir.
        if(endKm < returningVehicle.getCurrentKm()){
            throw new InvalidEndKilometerException("End kilometer cannot be less than current kilometer.");
        }

        //gidilen km, en son teslim edildiği km den ilk başta kiralandığı km'yi çıkararak bulunuyor
        double drivenKm = endKm - returningVehicle.getCurrentKm();

        // gidilen km kiralanırken belirtilen limitten fazlaysa extra aşım hesaplanarak aşım ücretiyle çarpılıp fiyata eklenecek
        if(drivenKm > KM_LIMIT ) {
            double extraKm = drivenKm - KM_LIMIT;
            double extraFee = extraKm * returningVehicle.getExtraKmPrice();
            result.addExtraKmFee(extraFee); // Method çağırılarak cezalı km başı tutarı faturaya yansıtılacak.
        }

        returningVehicle.setCurrentKm(endKm);
        returningVehicle.setStatus(VehicleStatus.AVAILABLE);
        return true;
    }

    public List<Vehicle> getParking() { //güncel parking listesine erişmek için
        return parking;
    }

}

