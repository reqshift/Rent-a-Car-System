package com.staj.rentacar;

import com.staj.rentacar.model.Vehicle;
import com.staj.rentacar.persistence.JsonDataManager;
import com.staj.rentacar.service.RentalService;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //JsonDataManager ve RentalService bağlantı için maine tanıtıldı
        JsonDataManager jsonDataManager = new JsonDataManager();
        //load vehicles kullanılarak return edilen vehicles, vehicles adında tutuldu
        List<Vehicle> vehicles = jsonDataManager.loadVehicles();
        RentalService rentalService = new RentalService(vehicles);

       // aşama 5 consol menüsü gelecek

        // Program kapanmadan önce güncel araç listesi JSON'a yazılır.
        jsonDataManager.saveVehicles(rentalService.getParking());
        System.out.println("\n========== JSON KAYDEDİLDİ ==========");

    }
}