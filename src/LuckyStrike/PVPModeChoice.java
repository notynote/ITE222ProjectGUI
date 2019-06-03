package LuckyStrike;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**+
 * A controller of a pvp mode chosen scene
 * this scene allow player to choose what mode they want to play
 */
public class PVPModeChoice {

    @FXML
    static Button first;
    @FXML
    static Button two;
    @FXML
    Pane p;
    @FXML
    Text choice;

    String sendBack;

    //Confirm Method
    public void Backtomenu(){
        // get a handle to the stage
        Stage stage = (Stage) p.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    public void setTextchoice(ActionEvent event){

        Button dummy = (Button) event.getSource();

        sendBack = dummy.getId();

        Backtomenu();

    }


}
