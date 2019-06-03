package LuckyStrike;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**+
 * Controller for skill choosing withing the battle phases
 * this popup allows user to select their desire skill
 */
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
    Text choice;
    String sendBack;

    //Confirm Method
    public void Backtomenu(){
        // get a handle to the stage
        Stage stage = (Stage) first.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    //change buntton function
    void setButtonName(String one, String two){

        this.first.setText(one);
        this.two.setText(two);
        this.three.setText("Check my Status");
        this.four.setText("Check Enemy Status");

    }

    public void setTextchoice(ActionEvent event){

        Button dummy = (Button) event.getSource();

        sendBack = dummy.getId();

        Backtomenu();

    }

}
