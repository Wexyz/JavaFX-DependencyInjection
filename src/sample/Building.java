package sample;

import java.util.ArrayList;

public class Building {
    private String name;
    private double totalRent;
    private double totalElectricity;
    private double totalWater;
    private ArrayList<Unit> unitList;

    Building(String name) {
        this.name = name;
        this.unitList = new ArrayList<>();

        this.totalRent = 0;
        this.totalElectricity = 0;
        this.totalWater = 0;
    }

     void addUnit(Unit unit){
        this.unitList.add(unit);
    }

     String getName() {
        return name;
    }

     double getTotalRent() {
        return totalRent;
    }

     double getTotalElectricity() {
        return totalElectricity;
    }

     double getTotalWater() {
        return totalWater;
    }

    ArrayList<Unit> getUnitList() {
        return unitList;
    }

    void update(){
        this.totalRent=0;
        this.totalElectricity=0;
        this.totalWater=0;
        for(Unit x : this.unitList){
            x.updateTotal();
            this.totalRent+= x.getTotalRent();
            this.totalElectricity += x.getTotalElec();
            this.totalWater += x.getTotalWater();
        }
    }
}

