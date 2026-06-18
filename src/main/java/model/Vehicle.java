package model;
import enums.VehicleStatus;
import enums.VehicleType;

//Vehicle soyut bir sınıftır (abstract) çünkü ondan türetilme yapılamaz, onu miras alanlardan (Car, motorcycle) yeni obje oluşturulur.
public abstract class Vehicle {
    private VehicleType type;
    private String plate;
    private String brand;
    private String model;
    private double dailyRentalPrice;
    private double currentKm;
    private String vehicleClass;
    private VehicleStatus status;


    public Vehicle(VehicleType type, String plate, String brand, String model, double dailyRentalPrice, double currentKm, String vehicleClass){ //vehicle'ın constructoru
        this.type = type;
        this.plate = plate;
        this.brand = brand;
        this.model = model;
        this.dailyRentalPrice = dailyRentalPrice;
        this.currentKm = currentKm;
        this.vehicleClass = vehicleClass;
        //ilk başta alınan tüm araçların kiralamaya uygun olduğunu belirtmek için status parametre olarak dışarıdan alınmadı, available'ye atandı.
        this.status = VehicleStatus.AVAILABLE;
    }

//set ve get methodları, kodun erişimine private değişkenlerin açılması için

    //(Araç tipi): Müşteri yaşı, araç türü için belirlenen minimum yaştan küçükse..." kuralı için tipi okumamızı sağlayacak.
    public VehicleType getType() {
        return type;
    }
    public void setType(VehicleType type) {
        this.type = type;
    }

    //(Plaka): Benzersiz anahtarımız olduğu için dökümandaki "Plakaya ait araç var mı?" kontrolünde kullanılacak.
    public String getPlate() {
        return plate;
    }
    public void setPlate(String plate) {
        this.plate = plate;
    }

    //(brand ve model): Listeleme yaparken sadece ekrana bastırılmak için kullanılacaklar.
    public String getBrand(){
        return brand;
    }
    public String getModel(){
        return model;
    }

    //(Günlük Ücret): Dökümandaki "Kiralama bedeli gün sayısına ve aracın günlük kira bedeline göre hesaplanmalıdır" kuralı için önemli.
    public double getDailyRentalPrice() {
        return dailyRentalPrice;
    }
    public void setDailyRentalPrice(double dailyRentalPrice){
        this.dailyRentalPrice = dailyRentalPrice;
    }

    //(Anlık kilometre): Aracın geldiği andaki km değerini almak ve bunu daha sonra teslim anında km sınırını geçip geçmediğini kontrol etmek için kullanılacak.
    public double getCurrentKm(){
        return currentKm;
    }
    public void setCurrentKm(double currentKm){
        this.currentKm = currentKm;
    }

    //(Araç türü): SET methodu: JSON'dan veriyi ilk kez Java'ya yüklerken ve sisteme yeni araç eklerken o değişkenin içini doldurmak için şarttır.
    //GET metodu: Filtreleme yaparken, arama yaparken ve ekrana aracın türünü yazdırmak/okumak için şarttır.
    public String getVehicleClass(){
        return vehicleClass;
    }
    public void setVehicleClass(String vehicleClass){
        this.vehicleClass = vehicleClass;
    }

    //Kirada olan araç tekrar kiralanamaz" kuralını işletirken aracın durumunu kontrol etmek için kullanılır.
    public VehicleStatus getStatus() {
        return status;
    }
    public void setStatus(VehicleStatus status) {
        this.status = status;
    }

    /*Age ve prive metodları neden soyut?: Vehicle sınıfı tek başına bu soruya cevap veremez çünkü alt classlar için bu değişiyor.
    Dolayısıyla sabit bir sayı yazılamadığından bu metodları abstract bırakıyoruz. Car sınıfı bunu return 21;, Motorcycle sınıfı ise return 18; diye override ederek dolduracak.*/

    //Aracın kullanım yaş kısıtlaması için
    public abstract int getMinAgeLimit();

    //Her aracın kiralama fiyatının hesaplanması için
    public abstract double calculateRentalPrice(int days);
}
