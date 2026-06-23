package com.staj.rentacar;

import com.staj.rentacar.enums.VehicleStatus;
import com.staj.rentacar.enums.VehicleType;
import com.staj.rentacar.model.Car;
import com.staj.rentacar.model.Motorcycle;
import com.staj.rentacar.model.Vehicle;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   🔍 AŞAMA 0, AŞAMA 1 VE AŞAMA 2 SİSTEMİK DENETİM PANELİ   ");
        System.out.println("==================================================");

        // -------------------------------------------------------------------------
        // DENETİM 1: Maven ve Gson Kütüphane Bağımlılık Kontrolü (Aşama 0)
        // -------------------------------------------------------------------------
        System.out.print("[TEST 1] Maven & Gson Kütüphane Kontrolü: ");
        try {
            com.google.gson.Gson gson = new com.google.gson.Gson();
            System.out.println("✓ BAŞARILI (Gson Kütüphanesi Aktif)");
        } catch (NoClassDefFoundError | Exception e) {
            System.out.println("✗ HATALI!");
            System.out.println("   👉 ÇÖZÜM: pom.xml dosyanızda Gson bağımlılığı eksik veya Maven projeyi yenileyemedi.");
            System.out.println("   Hata Detayı: " + e.getMessage());
            System.out.println("--------------------------------------------------");
        }

        // -------------------------------------------------------------------------
        // DENETİM 1B: pom.xml Gerçek XML Yapısı ve Format Kontrolü (Aşama 0 - Kesin Çözüm)
        // -------------------------------------------------------------------------
        System.out.print("[TEST 1B] pom.xml Dosya ve Format Kontrolü: ");
        try {
            java.io.File pomDosyasi = new java.io.File("pom.xml");

            if (!pomDosyasi.exists()) {
                System.out.println("✗ HATALI!");
                System.out.println("   👉 ÇÖZÜM: Proje kök dizininde pom.xml dosyası bulunamadı!");
            } else {
                javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
                javax.xml.parsers.DocumentBuilder builder = factory.newDocumentBuilder();
                builder.parse(pomDosyasi);
                System.out.println("✓ BAŞARILI (pom.xml formatı kurumsal standartlara uygun)");
            }
        } catch (org.xml.sax.SAXParseException e) {
            System.out.println("✗ HATALI (XML Format İhlali)!");
            System.out.println("   👉 ÇÖZÜM: pom.xml dosyanızın yapısı bozulmuş!");
            System.out.println("      Hatalı Satır: " + e.getLineNumber() + ", Sütun: " + e.getColumnNumber());
            System.out.println("      Hata Nedeni: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ OKUMA HATASI!");
            System.out.println("   Dosya doğrulanırken bir hata oluştu: " + e.getMessage());
        }

        // -------------------------------------------------------------------------
        // DENETİM 2: Car Sınıfı ve Kalıtım (Inheritance) Kontrolü (Aşama 1)
        // -------------------------------------------------------------------------
        System.out.print("[TEST 2] Car Nesnesi ve Encapsulation Kontrolü: ");
        Car testCar = null;
        try {
            testCar = new Car(VehicleType.CAR, "06ABC123", "Ford", "Focus", 2500.0, 120000.0, "Hatchback", "automatic");
            if (testCar.getPlate().equals("06ABC123") && testCar.getGearType().equals("automatic")) {
                System.out.println("✓ BAŞARILI (Car modeli sorunsuz)");
            } else {
                System.out.println("✗ HATALI VERİ UYUMSUZLUĞU!");
            }
        } catch (Exception e) {
            System.out.println("✗ HATALI!");
            e.printStackTrace();
        }

        // -------------------------------------------------------------------------
        // DENETİM 3: Motorcycle Sınıfı Kontrolü (Aşama 1)
        // -------------------------------------------------------------------------
        System.out.print("[TEST 3] Motorcycle Nesnesi Kontrolü: ");
        Motorcycle testMotor = null;
        try {
            testMotor = new Motorcycle(VehicleType.MOTORCYCLE, "34XYZ987", "Yamaha", "R25", 1800.0, 25000.0, "Sportbike", 250);
            System.out.println("✓ BAŞARILI (Motorcycle modeli sorunsuz)");
        } catch (Exception e) {
            System.out.println("✗ HATALI!");
            e.printStackTrace();
        }

        // -------------------------------------------------------------------------
        // DENETİM 4: Polimorfizm ve Yaş Sınırı Kuralları Kontrolü (Aşama 1)
        // -------------------------------------------------------------------------
        System.out.print("[TEST 4] Polimorfik İş Kuralları (getMinAgeLimit): ");
        try {
            if (testCar != null && testMotor != null) {
                Vehicle polyCar = testCar;
                Vehicle polyMotor = testMotor;
                if (polyCar.getMinAgeLimit() == 21 && polyMotor.getMinAgeLimit() == 18) {
                    System.out.println("✓ BAŞARILI (Yaş limitleri alt sınıflarda doğru ezilmiş)");
                } else {
                    System.out.println("✗ HATALI SONUÇ!");
                }
            }
        } catch (Exception e) {
            System.out.println("✗ HATALI!");
        }

        // -------------------------------------------------------------------------
        // DENETİM 5: Feedback Kontrolü (calculateRentalPrice Konumu)
        // -------------------------------------------------------------------------
        System.out.print("[TEST 5] Feedback Kontrolü (calculateRentalPrice Konumu): ");
        try {
            if (testCar != null) {
                if (testCar.calculateRentalPrice(4) == (testCar.getDailyRentalPrice() * 4)) {
                    System.out.println("✓ BAŞARILI (Fiyat hesaplama formülü doğru çalışıyor)");
                } else {
                    System.out.println("✗ HESAPLAMA HATASI!");
                }
            }
        } catch (Exception e) {
            System.out.println("✗ HATALI!");
        }

        // -------------------------------------------------------------------------
        // DENETİM 6: Durum Yönetimi (Enum Kontrolü)
        // -------------------------------------------------------------------------
        System.out.print("[TEST 6] Araç Durum Yönetimi (VehicleStatus): ");
        try {
            if (testCar != null && testCar.getStatus() == VehicleStatus.AVAILABLE) {
                testCar.setStatus(VehicleStatus.RENTED);
                if (testCar.getStatus() == VehicleStatus.RENTED) {
                    System.out.println("✓ BAŞARILI (Enum durum yönetimi aktif)");
                } else {
                    System.out.println("✗ HATALI");
                }
            }
        } catch (Exception e) {
            System.out.println("✗ HATALI!");
        }

        // -------------------------------------------------------------------------
        // DENETİM 7: RentalService Araç Ekleme ve Mükerrer Plaka Kontrolü (Aşama 2)
        // -------------------------------------------------------------------------
        System.out.println("\n[TEST 7] Servis Katmanı & Benzersiz Plaka Kontrolü:");
        try {
            com.staj.rentacar.service.RentalService rentalService = new com.staj.rentacar.service.RentalService();

            // İlk ekleme denemesi (Başarılı true dönmeli)
            boolean ilkEkleme = rentalService.addVehicle(testCar);
            if (ilkEkleme) {
                System.out.println("Vehicle add to parking successfully, added plate: " + testCar.getPlate());
            } else {
                System.out.println("✗ HATA: İlk araç eklenemedi!");
            }

            // Aynı aracı tekrar ekleme denemesi (Başarısız false dönmeli)
            boolean ikinciEkleme = rentalService.addVehicle(testCar);
            if (!ikinciEkleme) {
                System.out.println("[ERROR]: " + testCar.getPlate() + " brand already in the system");
                System.out.println("✓ BAŞARILI (Ekleme servis tarafından başarıyla reddedildi)");
            } else {
                System.out.println("✗ HATALI: Sistem aynı plakayı iki kez kabul etti!");
            }
        } catch (Exception e) {
            System.out.println("✗ SİSTEMİK HATA: " + e.getMessage());
        }

        // -------------------------------------------------------------------------
        // DENETİM 8: RentalService Plakadan Araç Bulma Kontrolü (Aşama 2)
        // -------------------------------------------------------------------------
        System.out.println("\n[TEST 8] Servis Katmanı & Plakadan Araç Bulma (findVehicle):");
        try {
            com.staj.rentacar.service.RentalService rentalService = new com.staj.rentacar.service.RentalService();
            rentalService.addVehicle(testCar); // Otoparka ekledik

            // Plakayı aratıyoruz
            Vehicle bulunan = rentalService.findVehicle("06ABC123");

            if (bulunan != null) {
                System.out.println("[INFO]: Searching vehicle is found");
                System.out.println("✓ BAŞARILI (Aranan plaka otoparkta nokta atışı bulundu!)");
            } else {
                System.out.println("✗ HATALI (Araç listede olduğu halde bulunamadı veya null döndü)");
            }
        } catch (Exception e) {
            System.out.println("✗ SİSTEMİK HATA: " + e.getMessage());
        }

        System.out.println("\n==================================================");
        System.out.println("       🔍 DENETİM RAPORU SÜRECİ BİTTİ             ");
        System.out.println("==================================================");
    }
}