package LuckyStrike;

import GameCode.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CharCreator {


    //GUI Variable
    @FXML
    private TextArea display;
    @FXML
    private Button CreateTestChar;
    @FXML
    private Button BackToMainMenu;
    @FXML
    private Pane p;

    //Create test Char Method
    public void TestChar() throws InterruptedException {

        //variable
        String again;

        display.clear();
        GameCode.Character testchar = makeChar();
        display.appendText("====================\n" + testchar + "\n====================");

    }

    //Back to Main Menu Method
    public void Backtomenu(){
        // get a handle to the stage
        Stage stage = (Stage) BackToMainMenu.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    //define Character name method
    public GameCode.Character makeChar(){

        try {
            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(getClass().getResource("CharnameInput.fxml").openStream());
            CharnameInput nameinput = (CharnameInput) ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Character Name Input");
            nStage.setScene(scene);

            p.setDisable(true);
            nStage.showAndWait();
            p.setDisable(false);

            String charname = nameinput.input.getText();
            return new GameCode.Character(charname);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new GameCode.Character("Test");
    }

}
