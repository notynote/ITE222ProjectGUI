package LuckyStrike;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**+
 * Controller for windows popup that allows user to input the desired name
 */
public class CharnameInput {

    //GUI Variable
    @FXML
    private Button confirm;
    @FXML
    TextField input;

    //Confirm Method
    public void Backtomenu(){
        // get a handle to the stage
        Stage stage = (Stage) confirm.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
