package LuckyStrike;

import GameCode.Character;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**+
 * A controller for character creator windows
 * this windows allow user to create a test character
 */
public class CharCreator extends Battle{


    //GUI Variable
    @FXML
    private TextArea display;
//    @FXML
//    private Button CreateTestChar;
    @FXML
    private Button BackToMainMenu;
    @FXML
    private Pane p;
    @FXML
    private ImageView chartestimg;

    /**+
     * Method to create a test character using the inputted name
     */
    public void TestChar() {

        display.clear();
        GameCode.Player testchar = makePlayer(p);
        display.appendText("====================\n" + testchar + "\n====================");

        //set char image
        int testcharclass = Battle.getSetimage(testchar.getCharclass());
        Battle.setClassimg(testcharclass, chartestimg);

    }

    //Back to Main Menu Method
    public void Backtomenu(){
        // get a handle to the stage
        Stage stage = (Stage) BackToMainMenu.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    @Override
    void Annoucer(int winner) {

    }
}
