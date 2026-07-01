package com.staj.rentacar.menu;
import com.staj.rentacar.dto.RentalResult;
import com.staj.rentacar.enums.VehicleType;
import com.staj.rentacar.model.Car;
import com.staj.rentacar.model.Motorcycle;
import com.staj.rentacar.model.Vehicle;
import com.staj.rentacar.persistence.JsonDataManager;
import com.staj.rentacar.service.RentalService;

import java.util.List;
import java.util.Scanner;

public class ConsoleManager {
 //constructor injection yapıları:
    private final RentalService rentalService;
    private final JsonDataManager jsonDataManager;

    //ConsoleManager sınıfının kullanıcıdan veri okuyacak tek bir Scanner nesnesi var.
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleManager(RentalService rentalService, JsonDataManager jsonDataManager){ //constructor
        this.rentalService = rentalService;
        this.jsonDataManager = jsonDataManager;
    }

    public void start(){
        boolean running = true;
        //main - consoleManager.start() - welcome
        System.out.println("Welcome to Rent a Car System");

        while (running) {

            showMenu(); // menu println ile gelir

            int choice = scanner.nextInt(); //kullanıcıdan seçini almak için kullanılır

            running = handleMenuChoice(choice); //switch case işlemlerini kapsar
        }
        System.out.println("Saving data...");
        // Program kapanmadan önce güncel araç listesi JSON'a yazılır.
        // Program güvenli şekilde kapatılır.
        jsonDataManager.saveVehicles(rentalService.getParking());
        System.out.println("System closed");

    }
    public void showMenu(){
        System.out.println("Please choose an option: \n");
        System.out.println("1- List all vehicles");
        System.out.println("2- Register a new vehicle");
        System.out.println("3- Rent a vehicle");
        System.out.println("4- Return a vehicle");
        System.out.println("5- Save and exit");
        System.out.println("\nEnter your choice: ");

    }

    public boolean handleMenuChoice(int choice){

        switch(choice) { //seçim alınır

            case 1:
                 listVehicles();
                break;
            case 2:
                addVehicle();
                break;
            case 3:
                rentVehicle();
                break;
            case 4:
                returnVehicle();
                break;
            case 5:
                return false;//running false döner, while false döner, döngüden çıkıp saveVehicles çalışır.
            default://caselerin hiçbirine girilmediyse default çalışır, yine return true gider
                System.out.println("Invalid choice. Please try again.");//Geçersiz eleman girişlerini engellemek için
                break;
        }
        return true;

    }
    public void listVehicles(){
        List<Vehicle> vehicles = rentalService.getParking();
        if(vehicles.isEmpty()){
            System.out.println("No vehicles found.");
            return;
        }
        for(Vehicle vehicle : vehicles){
            System.out.println("========== VEHICLE LIST ==========");
            System.out.println("----------------------------------------");
            System.out.println("Plate           : " + vehicle.getPlate());
            System.out.println("Brand           : " + vehicle.getBrand());
            System.out.println("Model           : " + vehicle.getModel());
            System.out.println("Type            : " + vehicle.getType());
            System.out.println("Vehicle Class   : " + vehicle.getVehicleClass());
            System.out.println("Daily Price     : " + vehicle.getDailyRentalPrice());
            System.out.println("Current Km      : " + vehicle.getCurrentKm());
            System.out.println("Status          : " + vehicle.getStatus());
            System.out.println("Extra Km Price  : " + vehicle.getExtraKmPrice());

            if(vehicle instanceof Car){
                Car car = (Car) vehicle; //Vehicle referansını Car referansına çeviriyoruz "downcasting"
                System.out.println("Gear type  : " + car.getGearType());
            } else  if (vehicle instanceof Motorcycle){ // olası yeni araç eklenme ihtimaline karşı motorcycle da kontrol ediliyor
                Motorcycle motorcycle = (Motorcycle) vehicle;
                System.out.println("Engine cc  : " + motorcycle.getEngineCc());
            }
            System.out.println("----------------------------------------\n");

        }
    }
    //methodlar doldurulacak, rastgele ayarladım tamamlayıp düzelteceğim
    public boolean addVehicle(){
        return true;
    }
    public RentalResult rentVehicle(){
        return null;
    }
    public boolean returnVehicle(){
        return true;
    }



}
