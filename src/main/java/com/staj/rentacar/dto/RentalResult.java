package com.staj.rentacar.dto;

public class RentalResult {
    private final String plate;
    private final int rentalDays;
    private final double basePrice;
    private double extraKmFee;
    private double totalLastPrice;


    public RentalResult(String plate, int rentalDays, double basePrice) {
        this.plate = plate;
        this.rentalDays = rentalDays;
        this.basePrice = basePrice;
        extraKmFee = 0.0;
        totalLastPrice = basePrice;
    }

    public String getPlate() {
        return plate;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getExtraKmFee() {
        return extraKmFee;
    }

    public double getTotalLastPrice() {
        return totalLastPrice;
    }

    public void addExtraKmFee(double fee) {
        this.extraKmFee = fee; //Dışarıdan gelen fee değerini, faturanın extraKmFee alanına kaydet.
        totalLastPrice = this.basePrice + extraKmFee; //en son toplamı aşım miktarı ve günlük kmden gelen kiralama parasıyla topladık.
    }
}