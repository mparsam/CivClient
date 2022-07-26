package View;

import enums.ParameterKeys;
import enums.RequestActions;
import enums.Responses.Response;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import static View.Menu.MenuType.*;

public class RegisterMenu extends Menu{
    private static Pane root;
    private static VBox menuBox;
    private static Line line;
    private static Alert alert;
    private static DialogPane dialogPane;
    private static Stage stage;
    private static TextField usernameField;
    private static TextField nicknameField;
    private static PasswordField passwordField;
    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("R E G I S T E R", () -> {
                register();
            }),
            new Pair<String, Runnable>("G O   T O   L O G I N", () -> {
                Menu.changeMenu(LOGIN_MENU);
            }),
            new Pair<String, Runnable>("E x i t", () -> {
                Menu.changeMenu(EXIT);
            })
    );


    private static Parent createContent() {
        addBackground(root, "Background_B");
        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3;
        line = addLine(lineX, lineY, 350, root);
        addLoginItems();
        addMenu(lineX + 5, lineY + 5, menuBox, menuData, root);

        startAnimation(line, menuBox);
        return root;
    }

    private static void addLoginItems() {
        VBox vBox = new VBox(10);
        Label username = new Label("username:");
        Label password = new Label("password:");
        Label nickname = new Label("nickname:");
        usernameField = new TextField();
        nicknameField = new TextField();
        passwordField = new PasswordField();
        username.setTextFill(Color.WHITE);
        password.setTextFill(Color.WHITE);
        nickname.setTextFill(Color.WHITE);
        username.setFont(Font.font(14));
        password.setFont(Font.font(14));
        nickname.setFont(Font.font(14));
        vBox.getChildren().addAll(username, usernameField, nickname, nicknameField, password, passwordField, new Text());
        menuBox.getChildren().addAll(vBox);

    }

    public static void show(Stage primaryStage) throws Exception {
        stage = primaryStage;
        root = new Pane();
        alert = initAlert("invalid input");
        menuBox = new VBox(-5);
        createContent();
        Pane pane = root;
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.getStylesheets().add(LoginMenu.class.getClassLoader().getResource("css/MenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    //////////////

    private static void register() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ParameterKeys.USERNAME.code, usernameField.getText());
        params.put(ParameterKeys.PASSWORD.code, passwordField.getText());
        params.put(ParameterKeys.NICKNAME.code, nicknameField.getText());

//        Response.LoginMenu response = Response.LoginMenu.values()[Network.getResponseEnumIntOf(RequestActions.REGISTER.code, params)];
        Response.LoginMenu response = (Response.LoginMenu) Network.getResponseObjOf(RequestActions.REGISTER.code, params);
//        Response.LoginMenu response = UserController.register(usernameField.getText(), passwordField.getText(), nicknameField.getText());
        if (response.equals(Response.LoginMenu.USERNAME_EXISTS)) {
            showAlert(alert, response.getString(usernameField.getText()));
        } else if (response.equals(Response.LoginMenu.NICKNAME_EXISTS)) {
            showAlert(alert, response.getString(nicknameField.getText()));
        } else if (!response.equals(Response.LoginMenu.REGISTER_SUCCESSFUL)) {
            showAlert(alert, "Invalid input format");
        } else {
            changeMenu(MAIN_MENU);
        }
    }

}
