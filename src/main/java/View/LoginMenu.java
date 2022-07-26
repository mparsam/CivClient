package View;

import Model.Request;
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

public class LoginMenu extends Menu {
    private static Pane root;
    private static VBox menuBox;
    private static Line line;
    private static Alert alert;
    private static DialogPane dialogPane;
    private static TextField usernameField;
    private static PasswordField passwordField;
    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("L o g i n", () -> {
                login();
            }),

            new Pair<String, Runnable>("G O   T O   R E G I S T E R", () -> {
                Menu.changeMenu(REGISTER_MENU);
            }),

            new Pair<String, Runnable>("E x i t", () -> {
                Menu.changeMenu(EXIT);
            })
    );
    private static Parent createContent() {
        addBackground(root, "Background_B");
        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;
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
        username.setTextFill(Color.WHITE);
        password.setTextFill(Color.WHITE);
        username.setFont(Font.font(14));
        password.setFont(Font.font(14));
        usernameField = new TextField();
        passwordField = new PasswordField();
        vBox.getChildren().addAll(username, usernameField, password, passwordField, new Text());
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

    private static void login() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ParameterKeys.USERNAME.code, usernameField.getText());
        params.put(ParameterKeys.PASSWORD.code, passwordField.getText());

//        Response.LoginMenu response = Response.LoginMenu.values()[Network.getResponseEnumIntOf(RequestActions.LOGIN.code, params)];
        Request responseRequest = Network.sendRequest(RequestActions.LOGIN.code, params);

        Response.LoginMenu response = (Response.LoginMenu) responseRequest.getObj();
        myTokenSTr = responseRequest.params.get("token");
        if (response.equals(Response.LoginMenu.USERNAME_PASSWORD_DONT_MATCH)) {
            showAlert(alert, response.getString());
        } else {
            changeMenu(MAIN_MENU);
        }
    }
}
