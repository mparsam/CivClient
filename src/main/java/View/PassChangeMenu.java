package View;

import enums.ParameterKeys;
import enums.RequestActions;
import enums.Responses.Response;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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

public class PassChangeMenu extends Menu{
    private static Pane root;
    private static VBox menuBox;
    private static Line line;
    private static Alert alert;
    private static PasswordField oldPasswordField;
    private static PasswordField newPasswordField;
    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("C h a n g e   P a s s w o r d", PassChangeMenu::changePassword),
            new Pair<String, Runnable>("B a c k", () -> Menu.changeMenu(PROFILE_MENU))
    );
    private static Parent createContent() {
        addBackground(root, "Background_A");
        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;
        line = addLine(lineX, lineY, 260, root);
        addFields();
        addMenu(lineX + 5, lineY + 5, menuBox, menuData, root);
        startAnimation(line, menuBox);
        return root;
    }

    private static void addFields() {
        VBox vBox = new VBox(10);
        Label oldPassword = new Label("old password");
        Label newPassword = new Label("new password");
        oldPassword.setTextFill(Color.WHITE);
        newPassword.setTextFill(Color.WHITE);
        oldPassword.setFont(Font.font(14));
        newPassword.setFont(Font.font(14));
        oldPasswordField = new PasswordField();
        newPasswordField = new PasswordField();
        vBox.getChildren().addAll(oldPassword, oldPasswordField, newPassword, newPasswordField, new Text());
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

    //////////////

    private static void changePassword() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ParameterKeys.OLD_PASSWORD.code, oldPasswordField.getText());
        params.put(ParameterKeys.NEW_PASSWORD.code, newPasswordField.getText());
        Response.ProfileMenu response = (Response.ProfileMenu) Network.getResponseObjOf(RequestActions.CHANGE_PASSWORD.code, params);
//        Response.ProfileMenu response = UserController.changePassword(oldPasswordField.getText(), newPasswordField.getText());
        if (!response.equals(Response.ProfileMenu.SUCCESSFUL_PASSWORD_CHANGE)) {
            showAlert(alert, response.getString());
        } else {
            changeMenu(PROFILE_MENU);
        }
    }
}
