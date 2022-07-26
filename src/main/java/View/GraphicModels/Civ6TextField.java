package View.GraphicModels;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class Civ6TextField extends Pane {
    TextField textField;

    public Civ6TextField(String name) {
        textField = new TextField();
//        if (textField.isFocused()){
//            textField
//        }
//        textField.setStyle("" +
//                "    -fx-background-color: rgba(26,47,115,0.5) ;\n" +
//                "    -fx-background-insets: 0, 0 0 1 0 ;\n" +
//                "    -fx-background-radius: 7 ;" +
//                "-fx-border-radius: 4;" +
//                "-fx-border-width: 1;" +
//                "-fx-min-width: 150;" +
//                "-fx-border-color: #ffffff;" +
//                "-fx-text-fill: #ffffff;" +
//                "");
        getChildren().addAll(textField);
    }
}
