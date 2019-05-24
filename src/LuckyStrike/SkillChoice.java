package LuckyStrike;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SkillChoice {

    @FXML
    Button first;
    @FXML
    Button two;
    @FXML
    Button three;
    @FXML
    Button four;
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

    //change buntton function
    public void setButtonName(String one, String two,String three, String four){

        this.first.setText(one);
        this.two.setText(two);
        this.three.setText(three);
        this.four.setText(four);

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
