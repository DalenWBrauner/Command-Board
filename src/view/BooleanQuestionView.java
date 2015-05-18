package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BooleanQuestionView {

    public static boolean getAnswer(String question) {

        final boolean[] result = new boolean[] {false};

        final Stage questionStage = new Stage();
        questionStage.initModality(Modality.WINDOW_MODAL);

//        questionStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent e) {
//                result = 0;
//            }
//         });

        Label questionLabel = new Label(question);
        questionLabel.setAlignment(Pos.BASELINE_CENTER);

        Button yesBtn = new Button("Yes");
        yesBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                result[0] = true;
                questionStage.close();
            }
        });

        Button noBtn = new Button("No");
        noBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                questionStage.close();
            }
        });

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(40.0);
        hBox.getChildren().addAll(yesBtn, noBtn);

        VBox vBox = new VBox();
        vBox.setSpacing(40.0);
        vBox.getChildren().addAll(questionLabel, hBox);

        questionStage.setScene(new Scene(vBox));
        questionStage.showAndWait();

        return result[0];
    }
}
