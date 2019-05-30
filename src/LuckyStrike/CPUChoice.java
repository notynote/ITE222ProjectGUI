package LuckyStrike;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class CPUChoice {

    @FXML
    static Button first;
    @FXML
    static Button two;
    @FXML
    static Button three;
    @FXML
    static Button four;
    @FXML
    static Button five;
    @FXML
    static Button six;
    @FXML
    static Button seven;
    @FXML
    static Button eight;
    @FXML
    static Button nine;
    @FXML
    static Button ten;
    @FXML
    static Button random;
    @FXML
    Pane p;

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
