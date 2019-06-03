package LuckyStrike;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**+
 * A controller of About Scene
 * this scene show the information of the game
 */
public class About {

    //Gui Variable
    @FXML
    private Button close;

    /**+
     * Allow user to close the scene
     */
    public void Close(){
        // get a handle to the stage
        Stage stage = (Stage) close.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}
