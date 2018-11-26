package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML Button newUnitBtn;
    @FXML ChoiceBox<String> buildingCB;
    @FXML ChoiceBox<Integer> unitCB;

    @FXML Label bldgNameLabel;
    @FXML TextField totalRentTF;
    @FXML TextField totalElecTF;
    @FXML TextField totalWaterTF;

    @FXML AnchorPane unitPane;
    @FXML Label unitDetailLabel;
    @FXML Label unitBalLbl;
    @FXML TabPane billTP;

    int selectedBuildingInt;
    private int selectedUnitInt;

    Building selectedBuilding;
    Unit selectedUnit;

    /**
     * Boolean set for addNew
     * True = Adding new BUILDING
     * False = Adding new UNIT
     * */
    boolean isBldgAdd = true;

    ArrayList<Building> buildingList = new ArrayList<>();
    private addNewBldgController addController;

    public void addBuilding() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addNewBldg.fxml"));
        Parent p = loader.load();
        Scene sc = new Scene(p);

        addController = loader.getController();
        addController.injectController(this);
        addController.addNewLabel.setText("Enter name for New Building");

        Stage st = new Stage();
        st.setScene(sc);
        st.show();
    }

    public void addUnit() throws IOException {
        //shares same window with addnewbuilding
        isBldgAdd = false;
        addBuilding();
        addController.addNewLabel.setText("Enter Int number for New Unit");
    }

    public void bldgDelete(){
        buildingList.remove(selectedBuilding);
        selectedBuilding=null;
        bldgCbUpdate();
        unitCBUpdate();
        unitPane.setVisible(false);
        unitPaneUpdate();
        bldgStatsUpdate();
    }

    public void unitDelete(){
        selectedBuilding.getUnitList().remove(selectedUnit);
        selectedUnit=null;
        unitCBUpdate();
        unitPane.setVisible(false);
        bldgStatsUpdate();

        rentListView.getItems().clear();
        elecListView.getItems().clear();
        waterListView.getItems().clear();
    }

    /**
     * Updates. Do not set directly to user action/input!
     * */

    //Call method to update ChoiceBox for Buildings
    void bldgCbUpdate() {
            buildingCB.getItems().clear();
            for(Building x : buildingList){
                buildingCB.getItems().add(x.getName());
            }
    }

    //Call method to update ChoiceBox for Units of Building
    void unitCBUpdate(){
        try{
            unitCB.getItems().clear();
            for(Unit x : buildingList.get(selectedBuildingInt).getUnitList()){
                unitCB.getItems().add(x.getUnitNumber());
            }
        } catch (Exception ignored){ }
    }

    //Call method to update building stats
    void bldgStatsUpdate(){
        try{
            bldgNameLabel.setText(selectedBuilding.getName());

            totalRentTF.setText(String.valueOf(selectedBuilding.getTotalRent()));
            totalElecTF.setText(String.valueOf(selectedBuilding.getTotalElectricity()));
            totalWaterTF.setText(String.valueOf(selectedBuilding.getTotalWater()));

        } catch (Exception e) {
            totalRentTF.setText(String.valueOf(0));
            totalElecTF.setText(String.valueOf(0));
            totalWaterTF.setText(String.valueOf(0));
        }

    }

    //Call method to update unit stats pane
    void unitPaneUpdate(){
        try {
            unitDetailLabel.setText(String.valueOf(selectedUnit.getUnitNumber()));
            unitBalLbl.setText(String.valueOf(selectedUnit.getBalance()));
        } catch (Exception e) {
            unitPane.setVisible(false);
        }
    }

    /**
     * Unit Pane Elements
     *
     * balSetSwitch
     * 1 = SETTING bal (Default)
     * 2 = ADDING TO bal
     * 3 = ADDING TO bill
     * */
    int balSetSwitch = 1;
    int billTPswitch = 0;

    public void setUnitBal() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("BalScene.fxml"));
        Parent p = loader.load();
        Scene sc = new Scene(p);

        BalController balController = loader.getController();
        balController.injectController(this);

        if(balSetSwitch==1) balController.addsetLabel.setText("Setting Balance");
        else if(balSetSwitch==2) balController.addsetLabel.setText("Adding to Current Balance");
        else if(balSetSwitch==3){
            switch (billTPswitch){
                case 0: balController.addsetLabel.setText("Add to "+selectedUnit.getUnitNumber()+" Rent"); break;
                case 1: balController.addsetLabel.setText("Add to "+selectedUnit.getUnitNumber()+" Electricity"); break;
                case 2: balController.addsetLabel.setText("Add to "+selectedUnit.getUnitNumber()+" Water"); break;
            }
        } else System.out.println("Invalid switch number");

        Stage st = new Stage();
        st.setScene(sc);
        st.setResizable(false);
        st.initStyle(StageStyle.UNDECORATED);
        st.initModality(Modality.APPLICATION_MODAL);
        st.show();

    }

    public void addToUnitBal() throws IOException{
        balSetSwitch = 2;
        setUnitBal();
    }

    public void addtoSpecBill() throws IOException {
        balSetSwitch = 3;
        setUnitBal();
    }

    @FXML ListView<String> rentListView;
    @FXML ListView<String> elecListView;
    @FXML ListView<String> waterListView;

    void billListUpdate(){
        rentListView.getItems().clear();
        elecListView.getItems().clear();
        waterListView.getItems().clear();
        int a = 0, b =0, c = 0;
        for(Double x : selectedUnit.getRentBill()){

            rentListView.getItems().add("("+a+") "+x);
            ++a;
        }
        for(Double x : selectedUnit.getElectricityBill()){
            elecListView.getItems().add("("+b+") "+x);
            ++b;
        }
        for(Double x : selectedUnit.getWaterBill()){
            waterListView.getItems().add("("+c+") "+x);
            ++c;
        }
    }

    private int setBillSelectInt(){
        switch (billTPswitch){
            case 0:
                return rentListView.getSelectionModel().getSelectedIndex();
            case 1:
                return elecListView.getSelectionModel().getSelectedIndex();
            case 2:
                return waterListView.getSelectionModel().getSelectedIndex();
        }
        return -1;
    }

    public void billDelete() {
        int billSelectInt = setBillSelectInt();
        try {
            switch (billTPswitch){
                case 0:
                    selectedUnit.getRentBill().remove(billSelectInt);
                    break;
                case 1:
                    selectedUnit.getElectricityBill().remove(billSelectInt);
                    break;
                case 2:
                    selectedUnit.getWaterBill().remove(billSelectInt);
                    break;
            }
            selectedBuilding.update();
            bldgStatsUpdate();
            billListUpdate();

        } catch (Exception e) {
            System.out.println("No bill selection detected");
        }

    }

    public void billPay() {
        int billSelectInt = setBillSelectInt();
        if (billSelectInt < 0) {
            System.out.println("No bill selection detected");
        } else {

            double fee = 0;
            switch (billTPswitch) {
                case 0:
                    fee = selectedUnit.getRentBill().get(billSelectInt);
                    break;
                case 1:
                    fee = selectedUnit.getElectricityBill().get(billSelectInt);
                    break;
                case 2:
                    fee = selectedUnit.getWaterBill().get(billSelectInt);
                    break;
            }
            if (fee <= selectedUnit.getBalance()) {
                selectedUnit.setBalance(selectedUnit.getBalance() - fee);
                billDelete();
            } else {
                System.out.println("Balance is not enough");
            }
            selectedBuilding.update();
            bldgStatsUpdate();
            unitPaneUpdate();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        newUnitBtn.setDisable(true);
        unitPane.setVisible(false);
        billTP.getSelectionModel().select(0);

        buildingCB.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedBuildingInt = (int) newValue;

                    try {
                        selectedBuilding = buildingList.get(selectedBuildingInt);
                    } catch (Exception ignored){}

                    bldgStatsUpdate();

                    unitCBUpdate();
                    unitCB.getSelectionModel().select(0);
                    if(selectedBuildingInt >=0) newUnitBtn.setDisable(false);
                    else newUnitBtn.setDisable(true);
                }
        );
        unitCB.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedUnitInt = (int) newValue;

                    try {
                        selectedUnit = selectedBuilding.getUnitList().get(selectedUnitInt);
                    } catch (Exception ignored) {}

                    unitPane.setVisible(true);
                    unitPaneUpdate();
                }
        );

        billTP.getSelectionModel().select(0);
        billTP.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> billTPswitch = (int) newValue
        );

        //TEST CASE
        Building testbldg = new Building("test");
        //Unit testunit = new Unit(123);
        testbldg.addUnit(new Unit(101));
        Unit testunit = testbldg.getUnitList().get(0);
        buildingList.add(testbldg);
        bldgCbUpdate();
        unitCBUpdate();

        selectedBuilding = testbldg;
        selectedUnit = testunit;

        buildingCB.getSelectionModel().select(0);
    }

}
