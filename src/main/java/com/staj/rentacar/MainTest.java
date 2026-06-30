package com.staj.rentacar;
import com.staj.rentacar.enums.VehicleType;
import com.staj.rentacar.exception.RentalException;
import com.staj.rentacar.model.Car;
import com.staj.rentacar.model.Vehicle;
import com.staj.rentacar.persistence.JsonDataManager;
import com.staj.rentacar.service.RentalService;
import com.staj.rentacar.dto.RentalResult;
import java.util.List;

public class MainTest {

    /*
-------------------------------------------------------
MainTest

Bu sınıf RentalService ve JsonDataManager
işlevlerini manuel olarak test etmek amacıyla
oluşturulmuştur.

Her testten önce gerekirse vehicles.json
başlangıç durumuna geri getirilmelidir.

-------------------------------------------------------
*/

    public static void main(String[] args) {

        JsonDataManager jsonDataManager = new JsonDataManager();

        List<Vehicle> vehicles = jsonDataManager.loadVehicles();

        RentalService rentalService = new RentalService(vehicles);

        /*
        Aşağıda json değiştirip already rented uyarısı veriliyor mu diye burada available olan rented hale getirildi.
        try {

            RentalResult result =
                    rentalService.rentVehicle("06ABC123",25,3);

            System.out.println("Vehicle rented successfully.");

            jsonDataManager.saveVehicles(rentalService.getParking());

            System.out.println("JSON saved successfully.");

        } catch (RentalException e){

            System.out.println(e.getMessage());

            //Başka bir test:
            RentalResult result = //Km aşımındaki para ve toplam miktarı hesaplatmak içindi
                rentalService.rentVehicle("06GHJ213",25,2);
            rentalService.returnVehicle(result,12600);

 */
        try { //burada da gerçekten rented oldu mu diye RentalExceptiondan already rented uyarısı basıldı.

            rentalService.rentVehicle("06ABC123",25,3);

            System.out.println("Test failed: vehicle should not have been rented.");

        } catch (RentalException e){

            System.out.println(e.getMessage());

        }

        jsonDataManager.saveVehicles(rentalService.getParking());
    }
}