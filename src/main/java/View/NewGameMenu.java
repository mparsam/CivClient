package View;


import Model.Request;
import Model.User;
import enums.RequestActions;
import enums.Responses.Response;
import javafx.application.Platform;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static View.Menu.MenuType.EXIT;
import static View.Menu.MenuType.GAME_VIEW;

public class NewGameMenu extends Menu {
    private static Pane root;
    private static VBox menuBox;
    private static Line line;
    private static Alert alert;
    private static DialogPane dialogPane;
    private static Stage stage;
    private static final Label invitationStatus = new Label("");
    private static TextField playerCountField;
    private static TextField playerUsernamesField;
    private static TextField mapSize;
    private static VBox invitationPopUp;
    private static Label invitationLabel;
    private static boolean isAcceptedInvs = false;
    private static Thread invitationThread;

    private static final List<Pair<String, Runnable>> menuData = Arrays.asList(
            new Pair<String, Runnable>("S E N D   I N V I T A T I O N S", () -> {
                try {
                    sendInvitations(Integer.parseInt(playerCountField.getText().trim()),
                            new ArrayList<String>(Arrays.stream(playerUsernamesField.getText().split(",")).toList()));
                } catch (Exception e) {
                    showAlert(alert, " invalid input for invitations");
                    System.err.println(" INVALID INPUTS FOR INVITATIONS!");
                    e.printStackTrace();
                }
            }),
            new Pair<String, Runnable>(" S T A R T   G A M E", () -> {
                startGame();
            }),
            new Pair<String, Runnable>("E x i t", () -> {
                Menu.changeMenu(EXIT);
            })
    );

    private static void initInvitationPopup() {
        invitationPopUp = new VBox();
        invitationPopUp.setStyle("-fx-background-color: #3f3f9b");
        Button accpBtn = new Button("Accept");
        accpBtn.setMinWidth(200);
        accpBtn.setOnMouseClicked(e -> {
            Network.sendRequest(RequestActions.ACCEPT_INVITAION.code, null);
            invitationPopUp.setVisible(false);
            isAcceptedInvs = true;
            areInvitationsAccptedThreadJoinee();
            System.out.println("after here");
        });
        Button rjctBtn = new Button("Reject");
        rjctBtn.setOnMouseClicked(e -> {
            invitationPopUp.setVisible(false);
        });
        rjctBtn.setMinWidth(200);
        invitationLabel = new Label("");
        invitationLabel.setFont(Font.font(19));
        invitationPopUp.getChildren().addAll(invitationLabel, accpBtn, rjctBtn);
        invitationPopUp.setLayoutX(WIDTH / 2 - 100);
        invitationPopUp.setLayoutY(HEIGHT / 2 - 150);
        invitationPopUp.setMinSize(200, 300);
        invitationPopUp.setVisible(false);
    }

    private static void startGame() {
        // TODO: 7/12/2022
        if (newGame(new ArrayList<String>(Arrays.stream(playerUsernamesField.getText().split(",")).toList()), Integer.parseInt(mapSize.getText()))) {
        } else {
            showAlert(alert, "game start failed");
        }
        Menu.changeMenu(GAME_VIEW);
    }

    private static void sendInvitations(int playersCount, ArrayList<String> invitationsUsernames) {
        int mapSizeInteger;
        try {
            mapSizeInteger = Integer.parseInt(mapSize.getText().trim());
        } catch (Exception e) {
            showAlert(alert, "invalid mapSize");
            return;
        }

        if (playersCount - 1 != invitationsUsernames.size()) {
            showAlert(alert, "usernames count is not valid");
            return;
        }

        if (areUsernamesValid(invitationsUsernames)) {
            invitationStatus.setText("invitations sent, not accepted yet!");
            Request req = new Request(RequestActions.SEND_INVITATIONS.code, null, invitationsUsernames);
            Network.sendRequest(req);
            areInvitationsAccptedThreadHost(invitationsUsernames, mapSizeInteger);

            // TODO: 7/11/2022 seding initations and waiting for acceptence and then initing the game
        } else {
            showAlert(alert, "invalid usernames");
        }

    }

    private static void areInvitationsAccptedThreadHost(ArrayList<String> invitationsUsernames, int mapSizeInteger) {
        new Thread(() -> {
            while (true) {
                Boolean ok = (Boolean) Network.getResponseObjOf(RequestActions.ARE_INVITATIONS_ACCEPTED.code, null);
                if (ok) {
                    Platform.runLater(() -> {
                        invitationThread.interrupt();
                        newGame(invitationsUsernames, mapSizeInteger);
                    });
                    break;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void areInvitationsAccptedThreadJoinee() {
        new Thread(() -> {
            while (true) {
                Boolean ok = (Boolean) Network.getResponseObjOf(RequestActions.ARE_INVITATIONS_ACCEPTED.code, null);
                if (ok) {
                    Platform.runLater(() -> {
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Menu.changeMenu(GAME_VIEW);
                        System.out.println(Response.MainMenu.NEW_GAME_STARTED.getString());
                    });
                    break;
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static boolean areUsernamesValid(ArrayList<String> usernames) {
        if (usernames == null) {
            return false;
        }
        ArrayList<User> playingUsers = new ArrayList<>();
        playingUsers.add((User) Network.getResponseObjOf(RequestActions.GET_THIS_USER.code, null));
        ArrayList<String> nonexistenceUsernames = new ArrayList<>();
        for (String username : usernames) {
            User user = (User) Network.getResponseObjOf(new Request(RequestActions.GET_USER_BY_USERNAME.code, null, username));
            if (user == null)
                nonexistenceUsernames.add(username);
            else
                playingUsers.add(user);
        }

        if (nonexistenceUsernames.size() != 0) {
            String invalidUsernames = "";
            for (int i = 0; i < nonexistenceUsernames.size(); i++) {
                invalidUsernames += nonexistenceUsernames.get(i);
                if (i != nonexistenceUsernames.size() - 1) invalidUsernames += ",";
            }
            System.out.println(Response.MainMenu.NONEXISTENCE_USERS.getString(invalidUsernames));
            return false;
        }
        return true;
    }


    private static Parent createContent() {
        initInvitationPopup();
        addBackground(root, "Background_B");
        double lineX = WIDTH / 2 - 100;
        double lineY = HEIGHT / 3;
        line = addLine(lineX, lineY, 350, root);
        addNewGameItems();
        addMenu(lineX + 5, lineY + 5, menuBox, menuData, root);

        startAnimation(line, menuBox);
        return root;
    }

    private static void addNewGameItems() {
        VBox vBox = new VBox(10);
        Label playerCount = new Label("number of players:");
        Label playerUsernames = new Label("other players username(','):");
        Label mapSizeLabel = new Label("Map Size: (1-5): ");
        playerCountField = new TextField();
        playerCountField.setTooltip(new Tooltip("enter the number of all players playing"));
        playerUsernamesField = new TextField();
        playerUsernamesField.setTooltip(new Tooltip("enter other players usernames seperated bt ',' "));
        mapSize = new TextField();
        mapSize.setTooltip(new Tooltip("enter the map size in 1-5"));
        // TODO: 7/11/2022 sending and checking invitations, if initaions are send or accepted, invitationStatus changes;
        playerCount.setTextFill(Color.WHITE);
        playerUsernames.setTextFill(Color.WHITE);
        invitationStatus.setTextFill(Color.WHITE);
        mapSizeLabel.setTextFill(Color.WHITE);

        playerCount.setFont(Font.font(14));
        mapSizeLabel.setFont(Font.font(14));
        invitationStatus.setFont(Font.font(14));
        invitationStatus.setTooltip(new Tooltip("states: sent but not accepted/ accepted.\n start the game when is accepted"));
        playerUsernames.setFont(Font.font(14));
        vBox.getChildren().addAll(playerCount, playerCountField, playerUsernames, playerUsernamesField, mapSizeLabel, mapSize, invitationStatus, new Text());
        menuBox.getChildren().addAll(vBox);
    }

    public static void show(Stage primaryStage) throws Exception {
        invitationThread = new Thread(() -> {
            while (true) {
                ArrayList<User> users = (ArrayList<User>) Network.getResponseObjOf(RequestActions.GET_INVITATIONS.code, null);
                System.out.println(users + " " + isAcceptedInvs);
                if (isAcceptedInvs) {
                    break;
                }
                if (users != null) {
                    String usersLable = "WANT TO PLAT A GAME WITH:\n";
                    for (User user : users) {
                        usersLable += "\n" + user.getNickname();
                    }
                    String labelOf = usersLable;
                    Platform.runLater(() -> {
                        invitationLabel.setText(labelOf);
                        invitationPopUp.setVisible(true);
                    });
                    break;
                }
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        invitationThread.start();


        stage = primaryStage;
        root = new Pane();
        alert = initAlert("invalid input");
        menuBox = new VBox(-5);
        createContent();
        Pane pane = root;
        pane.getChildren().add(invitationPopUp);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.getStylesheets().add(LoginMenu.class.getClassLoader().getResource("css/MenuStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    /**
     * @return true if newGame is created, false if not
     */
    public static boolean newGame(ArrayList<String> usernames, int mapSize) {
        isAcceptedInvs = true;

        if (usernames == null) {
            return false;
        }
        ArrayList<User> playingUsers = new ArrayList<>();
        playingUsers.add((User) Network.getResponseObjOf(RequestActions.GET_THIS_USER.code, null));
        ArrayList<String> nonexistenceUsernames = new ArrayList<>();
        for (String username : usernames) {
            User user = (User) Network.getResponseObjOf(new Request(RequestActions.GET_USER_BY_USERNAME.code, null, username));
            if (user == null)
                nonexistenceUsernames.add(username);
            else
                playingUsers.add(user);
        }

        if (nonexistenceUsernames.size() != 0) {
            String invalidUsernames = "";
            for (int i = 0; i < nonexistenceUsernames.size(); i++) {
                invalidUsernames += nonexistenceUsernames.get(i);
                if (i != nonexistenceUsernames.size() - 1) invalidUsernames += ",";
            }
            System.out.println(Response.MainMenu.NONEXISTENCE_USERS.getString(invalidUsernames));
            return false;
        }

        // TODO: 7/11/2022 this parts needs to fit for graphical start
        HashMap<String, String> params = new HashMap<>();
        params.put("mapSize", String.valueOf(mapSize));
        Network.sendRequest(new Request(RequestActions.NEW_GAME.code, params, playingUsers));
//        GameController.newGame(playingUsers, mapSize);
        Menu.changeMenu(GAME_VIEW);
        System.out.println(Response.MainMenu.NEW_GAME_STARTED.getString());
        return true;
    }
    //////////////

}

