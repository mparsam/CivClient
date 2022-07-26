package View;

import Model.User;
import enums.RequestActions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreBoardMenu extends Menu {
    private static Pane root;
    private static VBox menuBox;
    private static Line line;
    private static Alert alert;
    private static ObservableList<User> data;
    private static ArrayList<User> users = new ArrayList<>();
    private static Thread updater;
    private static TableView<User> table;
    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("B a c k", () -> {
                try {
                    updater.interrupt();
                } catch (Exception e) {
                }
                changeMenu(MenuType.MAIN_MENU);
            }
            )
    );

    private static Parent createContent() {
        addBackground(root, "Background_A");
        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3 + 50;
        line = addLine(lineX, lineY, 160, root);
        menuBox.getChildren().add(addFields());
        addMenu(lineX + 5, lineY + 5, menuBox, menuData, root);
        startAnimation(line, menuBox);
        return root;
    }

    private static VBox addFields() {
        VBox vBox = new VBox(10);
        users = (ArrayList<User>) Network.getResponseObjOf(RequestActions.GET_USERS.code, null);
        users = new ArrayList<>(users.stream().sorted(Comparator.comparing(User::getBestScore).thenComparing(User::getBestScoreTimeForSorting).thenComparing(User::getUsername).reversed()).collect(Collectors.toList()));
        data = FXCollections.observableArrayList(users);
        table = new TableView<>();
        updater = new Thread(() -> {
            try {
                while (true) {
                    users = null;
                    HashMap<String, String> tada = new HashMap<>();
                    tada.put("gg", LocalDateTime.now().toString());
                    users = (ArrayList<User>) Network.getResponseObjOf(RequestActions.GET_USERS.code, tada);

                    users.forEach(e -> System.out.println(e.getOnlineStatus()));
                    users = new ArrayList<>(users.stream().sorted(Comparator.comparing(User::getBestScore).thenComparing(User::getBestScoreTimeForSorting).thenComparing(User::getUsername).reversed()).collect(Collectors.toList()));
                    data = FXCollections.observableArrayList(users);

                    System.out.println("reSending");
                    table.refresh();
                    table.setItems(data);
                    table.setRowFactory(tv -> new TableRow<>() {
                        @Override
                        protected void updateItem(User user, boolean b) {
                            super.updateItem(user, b);
                            if (Network.getResponseObjOf(RequestActions.GET_THIS_USER.code, null).equals(user)) {
                                setStyle("-fx-background-color: #ff8888");
                            }
                        }
                    });
                    table.getSelectionModel().select((User) Network.getResponseObjOf(RequestActions.GET_THIS_USER.code, null));
                    table.refresh();
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {

            }
        });
        updater.start();
        table.setEditable(false);
        TableColumn nickCol = new TableColumn("NickName");
        TableColumn scoreCol = new TableColumn("Score");
        TableColumn best_timeCol = new TableColumn("Best Time");
        TableColumn statusCol = new TableColumn<>("Status");
        TableColumn lastSeenCol = new TableColumn<>("Last Update Time");
        nickCol.setSortable(false);
        scoreCol.setSortable(false);
        best_timeCol.setSortable(false);
        statusCol.setSortable(false);
        lastSeenCol.setSortable(false);
        nickCol.setCellValueFactory(new PropertyValueFactory<User, String>("nickname"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<User, Integer>("bestScore"));
        best_timeCol.setCellValueFactory(new PropertyValueFactory<User, String>("bestScoreTime"));
        statusCol.setCellValueFactory(new PropertyValueFactory<User, String>("onlineStatus"));
        lastSeenCol.setCellValueFactory(new PropertyValueFactory<User, String>("lastUpdate"));
        table.setPrefHeight(300);


        table.setItems(data);

        table.getColumns().addAll(nickCol, scoreCol, best_timeCol, statusCol, lastSeenCol);
        vBox.getChildren().add(table);
        vBox.getChildren().add(new Label());

        return vBox;

    }


    public static void show(Stage stage) throws Exception {
        root = new Pane();
//        alert = initAlert();
        menuBox = new VBox(-5);
        createContent();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add(LoginMenu.class.getClassLoader().getResource("css/MenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
