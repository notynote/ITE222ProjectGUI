package LuckyStrike;

//contain frequency use method in GUI

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIHelper {

    public static String getSkillchoice(String one, String two, String three, String four){

        try {
            FXMLLoader ld = new FXMLLoader();
            Pane root = ld.load(GUIHelper.class.getResource("SkillChoice.fxml").openStream());
            SkillChoice choose = ld.getController();

            Scene scene = new Scene(root);
            Stage nStage = new Stage();
            nStage.setTitle("Choose");
            nStage.setScene(scene);

            //Set buttun text
            choose.setButtonName(one,two,three,four);

            //p.setDisable(true);
            nStage.showAndWait();
            //p.setDisable(false);

            String result = choose.choice.getText();
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
