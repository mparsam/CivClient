package View;

import enums.ParameterKeys;
import enums.RequestActions;
import enums.Responses.Response;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static View.Menu.MenuType.PROFILE_MENU;

public class NickChangeMenu extends Menu {
    private static Pane root;
    private static VBox menuBox;
    private static Line line;
    private static Alert alert;
    private static TextField nicknameField;
    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("C h a n g e   N i c k n a m e ", NickChangeMenu::changeNickName),
            new Pair<String, Runnable>("B a c k", () -> changeMenu(PROFILE_MENU))
    );
    private static Parent createContent() {
        addBackground(root, "Background_A");
        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;
        line = addLine(lineX, lineY, 160, root);
        addFields();
        addMenu(lineX + 5, lineY + 5, menuBox, menuData, root);
        startAnimation(line, menuBox);
        return root;
    }

    private static void addFields() {
        VBox vBox = new VBox(10);
        Label username = new Label("new nickname");
        username.setTextFill(Color.WHITE);
        username.setFont(Font.font(14));
        nicknameField = new TextField();
        nicknameField.setPromptText("Enter a new nickname");
        vBox.getChildren().addAll(username, nicknameField, new Text());
        menuBox.getChildren().addAll(vBox);
    }


    public static void show(Stage stage) throws Exception {
        root = new Pane();
        alert = initAlert("invalid input");
        menuBox = new VBox(-5);
        createContent();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(LoginMenu.class.getClassLoader().getResource("css/MenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    //////////////////////////////////

    private static void changeNickName() {
//        Response.ProfileMenu response = UserController.changeNickname(nicknameField.getText());
        HashMap<String, String> params = new HashMap<>();
        params.put(ParameterKeys.NICKNAME.code, nicknameField.getText());
        Response.ProfileMenu response = (Response.ProfileMenu) Network.getResponseObjOf(RequestActions.CHANGE_NICKNAME.code, params);
        if (!response.equals(Response.ProfileMenu.SUCCESSFUL_NICKNAME_CHANGE)) {
            showAlert(alert, response.getString());
        } else {
            // TODO: 6/3/2022 some sort of confirmation
            changeMenu(PROFILE_MENU);
        }
    }
}
