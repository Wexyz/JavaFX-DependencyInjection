package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BalController {
    @FXML TextField balanceTF;
    @FXML Button confirmBtn;
    @FXML Button cancelBtn;
    @FXML Label addsetLabel;
    private MainController mainController;

    //Return true if input is valid double
    private double balanceEntry(){
        try{
            return Double.parseDouble(balanceTF.getText());

        } catch (Exception e) {
            return -1;
        }

    }

    @FXML
    void confirm(){
        double balance = balanceEntry();
        if(balance<0) System.out.println("Invalid Entry");
        else {
            switch (mainController.balSetSwitch){
                //SETTING BALANCE
                case 1: mainController.selectedUnit.setBalance(balance);
                        mainController.unitPaneUpdate();
                        break;
                //ADDING TO BALANCE
                case 2: mainController.selectedUnit.addToBalance(balance);
                        mainController.unitPaneUpdate();
                        mainController.balSetSwitch = 1;
                        break;
                //ADDING TO SPECIFIC BILL
                case 3:
                        switch (mainController.billTPswitch){
                            case 0: mainController.selectedUnit.addToRentBill(balance); break;
                            case 1: mainController.selectedUnit.addToElectricityBill(balance); break;
                            case 2: mainController.selectedUnit.addToWaterBill(balance); break;
                        }
                        mainController.billListUpdate();
                        mainController.selectedBuilding.update();
                        mainController.bldgStatsUpdate();

                        break;
            }

            mainController.bldgStatsUpdate();
            //Closes window
            cancel();
        }

    }

    @FXML
    void cancel(){
        Stage s = (Stage) cancelBtn.getScene().getWindow();
        s.hide();
        mainController.balSetSwitch =1;
    }

    void injectController(MainController mc) {
        mainController = mc;
    }


}
