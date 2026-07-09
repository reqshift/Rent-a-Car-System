package com.staj.rentacar.persistence;
import com.google.gson.*;
import com.staj.rentacar.enums.VehicleStatus;
import com.staj.rentacar.enums.VehicleType;
import com.staj.rentacar.model.Car;
import com.staj.rentacar.model.Motorcycle;
import com.staj.rentacar.model.Vehicle;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.List;


public class JsonDataManager {
    private static final String FILE_PATH = "data/vehicles.json"; // vehicles.json'un pathi değişse bile kodda tek tek değiştirmeye gerek olmayacak
    public List<Vehicle> loadVehicles(){ //jsonda sadece tek araç yok bu yüzden return type vehicle listesidir


        List<Vehicle> vehicles = new ArrayList<>();
       try(FileReader reader = new FileReader(FILE_PATH)) {// JSON dosyasını okumak için FileReader oluşturulur.
           Gson gson = new GsonBuilder().setPrettyPrinting().create(); //veri okunmaz, gson kullanılarak JSON java nesneleri yapılacak

           JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
           //doğrudan List<vehicle> okunamaz çünkü Vehicle abstract'tır(new Vehicle olmaz) java objelerine dönüştürülür
           //Gson, Reader'daki veriyi okuyacak ve JsonArray olarak döndürecek.

           if(jsonArray == null){
               return vehicles; // liste zaten boş
           }

           //listede elemanlar varsa:
           for (JsonElement element : jsonArray) { //jsonElement içinde jsonObjectler var

               Vehicle vehicle;
               JsonObject object = element.getAsJsonObject(); //elementler gsonObjecttir biliyoruz

               VehicleType type = VehicleType.valueOf(object.get("type").getAsString()); //objecktlerin type alanını alıp vehicleType enumuna çeviriyoruz
               String plate = object.get("plate").getAsString();
               String brand = object.get("brand").getAsString();
               String model = object.get("model").getAsString();
               double dailyRentalPrice = object.get("dailyRentalPrice").getAsDouble();
               double currentKm = object.get("currentKm").getAsDouble();
               String vehicleClass = object.get("vehicleClass").getAsString();
               double extraKmPrice = object.get("extraKmPrice").getAsDouble();
               int currentRentalDays = object.get("currentRentalDays").getAsInt();

               if (type == VehicleType.CAR) {
                   String gearType = object.get("gearType").getAsString();

                   vehicle = new Car(
                           type,
                           plate,
                           brand,
                           model,
                           dailyRentalPrice,
                           currentKm,
                           vehicleClass,
                           gearType,
                           extraKmPrice,
                           currentRentalDays
                   );

               } else if (type == VehicleType.MOTORCYCLE) {
                   int engineCc = object.get("engineCc").getAsInt();

                   vehicle = new Motorcycle(

                           type,
                           plate,
                           brand,
                           model,
                           dailyRentalPrice,
                           currentKm,
                           vehicleClass,
                           engineCc,
                           extraKmPrice,
                           currentRentalDays

                   );

               } else {
                   continue;  //Tanınmayan type varsa bu kaydı atla.
               }

               VehicleStatus status = VehicleStatus.valueOf(object.get("status").getAsString());
               vehicle.setStatus(status);
               vehicles.add(vehicle);
           }


       } catch (IOException e){
           throw new RuntimeException("Failed to load vehicles from JSON file.", e);
       }
        return vehicles;
    }

    public void saveVehicles(List<Vehicle> vehicles) {

        try (FileWriter writer = new FileWriter(FILE_PATH)){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(vehicles, writer); //Bu vehicles listesini JSON formatına dönüştür ve sonucu writer kullanarak dosyaya yaz.

        } catch (IOException e){
            throw new RuntimeException("Failed to save vehicles to JSON file.", e);
        }
        //writer.close() otomatik yapıldı try dan sonra
    }

}