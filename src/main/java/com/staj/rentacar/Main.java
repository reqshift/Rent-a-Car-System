package com.staj.rentacar;

import com.staj.rentacar.dto.RentalResult;
import com.staj.rentacar.enums.VehicleType;
import com.staj.rentacar.model.Car;
import com.staj.rentacar.model.Motorcycle;
import com.staj.rentacar.service.RentalService;

public class Main {
    public static void main(String[] args) {

        RentalService rentalService = new RentalService();

        // Araçları sisteme ekle
        rentalService.addVehicle(
                new Car(
                        VehicleType.CAR,
                        "06ABC123",
                        "Ford",
                        "Focus",
                        1500,
                        250000,
                        "Hatchback",
                        "Automatic",
                        10
                )
        );

        rentalService.addVehicle(
                new Motorcycle(
                        VehicleType.MOTORCYCLE,
                        "06DEF321",
                        "Yamaha",
                        "R25",
                        1200,
                        40000,
                        "Sportbike",
                        250,
                        3
                )
        );

        // Kiralama testi
        RentalResult result =
                rentalService.rentVehicle("06ABC123", 25, 3);

        if (result != null) {
            System.out.println("Araç başarıyla kiralandı.");
            System.out.println("Toplam kira bedeli: "
                    + result.getBasePrice());

            // Teslim alma testi
            boolean returned =
                    rentalService.returnVehicle(result, 250700);

            if (returned) {
                System.out.println("Araç teslim alındı.");
                System.out.println("Ek km ücreti: "
                        + result.getExtraKmFee());
                System.out.println("Toplam ücret: "
                        + result.getTotalLastPrice());
            }
        }
    }
}