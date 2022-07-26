package View;

import Model.User;
import View.Components.Civ6Title;
import enums.RequestActions;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static View.Menu.MenuType.*;

public class MainMenu extends Menu {
    private static final String chatClientCommand = """
            "C:\\Program Files\\Java\\jdk1.8.0_311\\bin\\java.exe" "-javaagent:E:\\IntelliJ IDEA 2021.3.2\\lib\\idea_rt.jar=3919:E:\\IntelliJ IDEA 2021.3.2\\bin" -Dfile.encoding=UTF-8 -classpath "C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\charsets.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\deploy.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\access-bridge-64.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\cldrdata.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\dnsns.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\jaccess.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\jfxrt.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\localedata.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\nashorn.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\sunec.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\sunjce_provider.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\sunmscapi.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\sunpkcs11.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\ext\\zipfs.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\javaws.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\jce.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\jfr.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\jfxswt.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\jsse.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\management-agent.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\plugin.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\resources.jar;C:\\Program Files\\Java\\jdk1.8.0_311\\jre\\lib\\rt.jar;D:\\UNIVERSITY\\2\\AP\\Projects\\ChatClient\\out\\production\\ChatClient" main 
            """ + ((User) Network.getResponseObjOf(RequestActions.GET_THIS_USER.code, null)).getUsername();


    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("N e w   G a m e", () -> {
                Menu.changeMenu(NEW_GAME_MENU);
            }),
            new Pair<String, Runnable>("P r o f i l e", () -> {
                Menu.changeMenu(PROFILE_MENU);
            }),
            new Pair<String, Runnable>("S c o r e b o a r d", () -> {
                Menu.changeMenu(SCOREBOARD);
            }),
            new Pair<String, Runnable>("C h a t   R o o m", () -> {
                try {
                    Runtime.getRuntime().exec(chatClientCommand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }),
            new Pair<String, Runnable>("L o g o u t", () -> {
                logout();
            }),
            new Pair<String, Runnable>("E x i t", () -> {
                Menu.changeMenu(EXIT);
            })
    );

    private static void logout() {
        Network.sendRequest(RequestActions.LOGOUT.code, null);
        changeMenu(LOGIN_MENU);
    }

    private static Pane root = new Pane();
    private static final VBox menuBox = new VBox(-5);
    private static Line line;

    private static Parent createContent() {
        root = new Pane();
        menuBox.getChildren().clear();
        addBackground(root, "Background_A");
        addTitle();

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;

        line = addLine(lineX, lineY, 300, root);
        addMenu(lineX + 5, lineY + 5, menuBox, menuData, root);

        startAnimation(line, menuBox);

        return root;
    }

    private static void addTitle() {
        Civ6Title title = new Civ6Title("CIVILIZATION VI");
        title.setTranslateX(WIDTH / 2 - title.getTitleWidth() / 2);
        title.setTranslateY(HEIGHT / 5);

        root.getChildren().add(title);
    }

    public static void show(Stage stage) throws Exception {
        createContent();
        Pane pane = root;
        stage.setScene(new Scene(pane, WIDTH, HEIGHT));
        stage.show();
    }

    ////////////////

}
