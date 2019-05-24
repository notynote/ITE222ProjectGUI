package LuckyStrike;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PVPModeChoice {

    @FXML
    static Button first;
    @FXML
    static Button two;
    @FXML
    static Button three;
    @FXML
    Pane p;
    @FXML
    TextField choice;
    @FXML
    private Button confirm;

    //Confirm Method
    public void Backtomenu(){
        // get a handle to the stage
        Stage stage = (Stage) confirm.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void setTextchoice(ActionEvent event){

        Button dummy = (Button) event.getSource();

        choice.setText(dummy.getId());

        // get a handle to the stage
        Stage stage = (Stage) confirm.getScene().getWindow();
        // do what you have to do
        stage.close();

    }


}
