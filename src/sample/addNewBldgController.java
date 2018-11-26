package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class addNewBldgController implements Initializable {

    @FXML public TextField newBldgName;
    @FXML public Button addNewAcceptBtn;
    @FXML public Button addNewCancelBtn;
    @FXML public Label addNewLabel;

    //THIS IS WHERE MAINCONTROLLER IS ASSIGNED
    private MainController controller;

    public void addNewAccept() {
        if(controller.isBldgAdd){
            String x = newBldgName.getText().trim();
            if(x.length()<1){
                System.out.println("No entry for Building name detected");
            } else {
                controller.buildingList.add(new Building(x));
                controller.bldgCbUpdate();

                controller.buildingCB.getSelectionModel().select(x);

                //sets add building boolean to true
                controller.isBldgAdd = true;
                //Closes stage/window
                Stage s = (Stage) addNewAcceptBtn.getScene().getWindow();
                s.close();
            }

        } else {
            try {
                int y = Integer.parseInt(newBldgName.getText().trim());
                if(y<0){
                    System.out.println("Enter valid Integer for Unit");
                } else {
                    //gets selected Building int
                    int selInt = controller.selectedBuildingInt;
                    //gets Building from list using int
                    Building selBldg = controller.buildingList.get(selInt);

                    Unit newUnit = new Unit(y);
                    selBldg.addUnit(newUnit);


                    controller.unitCBUpdate();


                    int selUnit = selBldg.getUnitList().indexOf(newUnit);
                    controller.unitCB.getSelectionModel().select(selUnit);

                    //sets add building boolean to true
                    controller.isBldgAdd = true;
                    //Closes stage/window
                    Stage s = (Stage) addNewAcceptBtn.getScene().getWindow();
                    s.close();

                }
            } catch (Exception e){
                System.out.println("Enter valid Integer for Unit");
            }

        }



    }

    public void addNewCancel() {
        controller.isBldgAdd = true;
        Stage s = (Stage) addNewCancelBtn.getScene().getWindow();
        s.close();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //called from MAINCONTROLLER after getting ADDNEW- Controller
    void injectController(MainController mc) {
        controller = mc;
    }
}
