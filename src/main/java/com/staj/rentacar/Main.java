package com.staj.rentacar;

import com.staj.rentacar.dto.RentalResult;
import com.staj.rentacar.enums.VehicleType;
import com.staj.rentacar.exception.*;
import com.staj.rentacar.model.Car;
import com.staj.rentacar.model.Motorcycle;
import com.staj.rentacar.service.RentalService;

public class Main {
    public static void main(String[] args) {

        RentalService rentalService = new RentalService();

        // Araçları sisteme ekle
        rentalService.addVehicle(
                /*try {
            rentalService.addVehicle(...);
            rentalService.addVehicle(...);
        }
        catch (RentalException e) {
            System.out.println(e.getMessage());
        } ileride scanner için addVehicle adına değer alınca ona da RentalEXception ile birlikte try catch yazacağım */
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
        RentalResult result = null;
        try {
            result = rentalService.rentVehicle("06ABC123", 25, 3);
            System.out.println("Vehicle rented successfully.");
            System.out.println("Total rental price: " + result.getBasePrice());
        } catch (RentalException e) {
            System.out.println(e.getMessage());
        }


        //teslim alma methodları
        if (result != null) {
            try {
                boolean returned = rentalService.returnVehicle(result, 250700);

                if (returned) {
                    System.out.println("Vehicle returned successfully.");
                    System.out.println("Extra kilometer fee: " + result.getExtraKmFee());
                    System.out.println("Total price: " + result.getTotalLastPrice());
                }

            } catch (RentalException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}