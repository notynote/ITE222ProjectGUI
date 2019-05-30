package LuckyStrike;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class About {

    //Gui Variable
    @FXML
    private Button close;

    //Confirm Method
    public void Close(){
        // get a handle to the stage
        Stage stage = (Stage) close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
