package View;

import View.Components.Civ6MenuItem;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.List;

public class Menu extends Application {
    public static final float WIDTH = 1440;
    public static final float HEIGHT = 720;
    private static Stage stage;

    private static final int SERVER_PORT = 7777;
    private static OutputStream outputStream;
    private static ObjectOutputStream objectOutputStream;
    private static InputStream inputStream;
    private static ObjectInputStream objectInputStream;
    private static Socket socket;
    public static String myTokenSTr;

    public enum MenuType {
        MAIN_MENU("mainMenu"),
        LOGIN_MENU("loginMenu"),
        REGISTER_MENU("registerMenu"),
        NEW_GAME_MENU("newGameMenu"),
        PROFILE_MENU("profileMenu"),
        PASS_CHANGE_MENU("passChangeMenu"),
        NICK_CHANGE_MENU("nickChangeMenu"),
        GAME_VIEW("gameView"),
        SCOREBOARD("scoreBoard"),
        EXIT("exit");

        String name;

        MenuType(String name) {
            this.name = name;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Network.connectToServer();
        stage = primaryStage;
        stage.setTitle("Civilization VI");
        stage.setResizable(false);

        MenuType currentMenu = MenuType.LOGIN_MENU;
        switch (currentMenu) {
            case MAIN_MENU -> MainMenu.show(stage);
            case LOGIN_MENU -> LoginMenu.show(stage);
            case REGISTER_MENU -> RegisterMenu.show(stage);
            case PROFILE_MENU -> ProfileMenu.show(stage);
            case GAME_VIEW -> GameView.show(stage);
        }
    }

    public static void changeMenu(MenuType menuType) {
        try {
            System.out.println("Going to: " + menuType.name);
            switch (menuType) {
                case MAIN_MENU -> MainMenu.show(stage);
                case LOGIN_MENU -> LoginMenu.show(stage);
                case PROFILE_MENU -> ProfileMenu.show(stage);
                case NICK_CHANGE_MENU -> NickChangeMenu.show(stage);
                case PASS_CHANGE_MENU -> PassChangeMenu.show(stage);
                case REGISTER_MENU -> RegisterMenu.show(stage);
                case NEW_GAME_MENU -> NewGameMenu.show(stage);
                case GAME_VIEW -> GameView.show(stage);
                case SCOREBOARD -> ScoreBoardMenu.show(stage);
                case EXIT -> System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Parent loadFXML(String name) {
        try {
            URL address = new URL(Menu.class.getResource("/fxml/" + name + ".fxml").toString());
            return FXMLLoader.load(address);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static void addBackground(Pane root, String background) {
        ImageView imageView = new ImageView(MainMenu.class.getClassLoader().getResource("images/" + background + ".png").toExternalForm());
        imageView.setFitWidth(WIDTH);
        imageView.setFitHeight(HEIGHT);

        root.getChildren().add(imageView);
    }

    protected static Line addLine(double x, double y, double length, Pane root) {
        Line line = new Line(x, y, x, y + length);
        line.setStrokeWidth(3);
        line.setStroke(Color.color(1, 1, 1, 0.75));
        line.setEffect(new DropShadow(5, Color.BLACK));
        line.setScaleY(0);
        root.getChildren().add(line);
        return line;
    }

    protected static void startAnimation(Line line, VBox menuBox) {
        ScaleTransition st = new ScaleTransition(Duration.seconds(1), line);
        st.setToY(1);
        st.setOnFinished(e -> {
            for (int i = 0; i < menuBox.getChildren().size(); i++) {
                Node n = menuBox.getChildren().get(i);

                TranslateTransition tt = new TranslateTransition(Duration.seconds(1 + i * 0.15), n);
                tt.setToX(0);
                tt.setOnFinished(e2 -> n.setClip(null));
                tt.play();
            }
        });
        st.play();
    }

    protected static void addMenu(double x, double y, VBox menuBox, List<Pair<String, Runnable>> menuData, Pane root) {
        menuBox.setTranslateX(x);
        menuBox.setTranslateY(y);
        menuData.forEach(data -> {
            Civ6MenuItem item = new Civ6MenuItem(data.getKey());
            item.setTranslateX(-300);
            item.setOnAction(data.getValue());
            Rectangle clip = new Rectangle(300, 30);
            clip.translateXProperty().bind(item.translateXProperty().negate());
            item.setClip(clip);
            menuBox.getChildren().addAll(item);
        });
        root.getChildren().addAll(menuBox);
    }

    protected static Alert initAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(RegisterMenu.class.getClassLoader().getResource("css/MenuStyle.css").toExternalForm());
        return alert;
    }

    protected static void showAlert(Alert alert, String message) {
        alert.setHeaderText(message);
        alert.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}
