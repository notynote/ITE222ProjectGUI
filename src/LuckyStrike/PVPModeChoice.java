package LuckyStrike;

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

    public void setTextchoice1(){
        choice.setText("1");
    }

    public void setTextchoice2(){
        choice.setText("2");
    }

    public void setTextchoice3(){
        choice.setText("3");
    }


}
