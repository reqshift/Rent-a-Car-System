package com.staj.rentacar;

import com.staj.rentacar.menu.ConsoleManager;
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

        ConsoleManager consoleManager = new ConsoleManager(rentalService, jsonDataManager);
        consoleManager.start();

    }
}