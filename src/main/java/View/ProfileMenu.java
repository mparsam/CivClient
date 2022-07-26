package View;

import Model.Request;
import Model.User;
import View.Components.Civ6Profile;
import View.Components.Civ6Title;
import enums.RequestActions;
import enums.Responses.Response;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static View.Menu.MenuType.*;

public class ProfileMenu extends Menu{
    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("C h a n g e   P i c t u r e", ProfileMenu::changePicture),
            new Pair<String, Runnable>("C h a n g e   N i c k n a m e", () -> changeMenu(NICK_CHANGE_MENU)),
            new Pair<String, Runnable>("C h a n g e   P a s s w o r d", () -> changeMenu(PASS_CHANGE_MENU)),
            new Pair<String, Runnable>("D e l e t e   A c c o u n t", ProfileMenu::deleteAccount),
            new Pair<String, Runnable>("B a c k", () -> changeMenu(MAIN_MENU))
    );

    private static Pane root = new Pane();
    private static final VBox menuBox = new VBox(-5);
    private static Line line;
    private static Alert alert;
    private static FileChooser fileChooser;
    private static File file;

    private static Parent createContent() {
        root = new Pane();
        menuBox.getChildren().clear();
        addBackground(root, "Background_A");
        addTitle();

        Civ6Profile civ6Profile = new Civ6Profile((User) Network.getResponseObjOf(RequestActions.GET_THIS_USER.code, null));
        civ6Profile.getfileName();
        civ6Profile.setTranslateX((WIDTH - civ6Profile.getWidth()) / 2);
        civ6Profile.setTranslateY(HEIGHT / 2 - civ6Profile.getHeight() + 50);
        root.getChildren().add(civ6Profile);

        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 2 + 100;

        line = addLine(lineX, lineY, 200, root);
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
        alert = initAlert("invalid input");
        Pane pane = root;
        stage.setScene(new Scene(pane, WIDTH, HEIGHT));
        stage.show();
    }

    ///////////////

    private static void deleteAccount() {
        Network.sendRequest(RequestActions.REMOVE_USER.code, null);
//        UserController.removeUser();

        changeMenu(REGISTER_MENU);
    }

    private static void changePicture() {
        try {
            fileChooser = new FileChooser();
            fileChooser.setTitle("Select Image");
            file = fileChooser.showOpenDialog(Menu.getStage().getScene().getWindow());
            Request request = new Request(RequestActions.CHANGE_PROFILE_PICTURE.code, null);

            byte[] rawBytes = Files.readAllBytes(file.toPath());
            Byte[] bytes = new Byte[rawBytes.length];
            for (int i = 0; i < rawBytes.length; i++) {
                bytes[i] = rawBytes[i];
            }
            ArrayList<Byte> data = new ArrayList<Byte>(Arrays.asList(bytes));
            request.setObj(data);
            System.out.println("dataByteSize" + data.size());
            //        Response.ProfileMenu response = Response.ProfileMenu.values()[Network.getResponseEnumIntOf(request)];
            Response.ProfileMenu response = (Response.ProfileMenu) Network.getResponseObjOf(request);
            //        Response.ProfileMenu response = UserController.changePicture(file);
            if (!response.equals(Response.ProfileMenu.SUCCESSFUL_PICTURE_CHANGE)) {
                showAlert(alert, response.getString());
                System.out.println(file.length());
            } else {
                changeMenu(PROFILE_MENU);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
