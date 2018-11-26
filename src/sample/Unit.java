package sample;

import java.util.ArrayList;

public class Unit {
    private int unitNumber;
    private ArrayList<Double> electricityBill;
    private ArrayList<Double> waterBill;
    private ArrayList<Double> rentBill;
    private double balance;

    private double totalRent;
    private double totalElec;
    private double totalWater;

    public Unit(int unitNumber) {
        this.unitNumber = unitNumber;
        this.rentBill = new ArrayList<>();
        this.electricityBill = new ArrayList<>();
        this.waterBill = new ArrayList<>();

        this.balance = 0;

        this.totalRent = 0;
        this.totalElec = 0;
        this.totalWater = 0;

        for(Double x : rentBill) this.totalRent += x;
        for(Double x : electricityBill) this.totalRent += x;
        for(Double x : waterBill) this.totalRent += x;

    }

     int getUnitNumber() { return unitNumber; }

     double getBalance() { return balance;}

     void setBalance(double balance) {this.balance = balance;}
     void addToBalance(double balance) {this.balance += balance;}

    ArrayList<Double> getElectricityBill() { return electricityBill; }

    public void addToElectricityBill(double electricityBill) {
        this.electricityBill.add(electricityBill);
        this.totalElec+=electricityBill;
    }

     ArrayList<Double> getWaterBill() { return waterBill; }

    public void addToWaterBill(double waterBill) {
        this.waterBill.add(waterBill);
        this.totalWater+=waterBill;
    }

     ArrayList<Double> getRentBill() { return rentBill; }

    public void addToRentBill(double rentBill) {
        this.rentBill.add(rentBill);
        this.totalRent+=rentBill;
    }

    public double getTotalRent() {
        return totalRent;
    }

    public double getTotalElec() {
        return totalElec;
    }

    public double getTotalWater() {
        return totalWater;
    }

    public void updateTotal(){
        this.totalRent=0;
        this.totalWater=0;
        this.totalElec=0;
        for(Double x : rentBill) this.totalRent+=x;
        for(Double x : electricityBill) this.totalElec+=x;
        for(Double x : waterBill) this.totalWater+=x;

    }

}
