package LuckyStrike;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
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

    public void setTextchoice4(){
        choice.setText("4");
    }

    public void setTextchoice5(){
        choice.setText("5");
    }

    public void setTextchoice6(){
        choice.setText("6");
    }

    public void setTextchoice7(){
        choice.setText("7");
    }

    public void setTextchoice8(){
        choice.setText("8");
    }

    public void setTextchoice9(){
        choice.setText("9");
    }

    public void setTextchoice10(){
        choice.setText("10");
    }

    public void setTextchoice0(){
        choice.setText("0");
    }


}
