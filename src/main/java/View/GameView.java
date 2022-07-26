package View;
// THIS IS FOR ONLINE PLAYING ...

import Model.*;
import Model.Units.Unit;
import View.ClientPanels.ClientCitySelectedPanel;
import View.ClientPanels.ClientResearchPanel;
import View.ClientPanels.ClientUnitSelectedPanel;
import View.Panels.*;
import View.PastViews.MapMaker;
import enums.RequestActions;
import enums.Types.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class GameView extends Menu {
    private final static int LEFT_WIDTH = 200;
    private final static int RIGHT_WIDTH = 400;
    private final static int BOTTOM_HEIGHT = 150;

    private static int cameraRow, cameraColumn;

    protected static Pane root;
    protected static Pane map = new Pane();
    protected static HBox topPane;
    protected static Label topPaneLabel;
    protected static VBox panelsPane;

    protected static VBox notificationPane;
    protected static VBox militaryPane;
    protected static VBox economyPane;
    protected static VBox demographicsPane;
    protected static VBox unitsPane;
    protected static VBox citiesPane;

    protected static VBox diplomacyPane;
    protected static VBox researchPane;
    // TODO: 7/25/2022 select panel panel

    protected static VBox unitSelectedPane;
    protected static VBox citySelectedPane;

    protected static Alert infoAlert;
    protected static Alert invalidAlert;
    protected static DialogPane dialogPane;
    protected static final Label waitiingLable = new Label("NOT your turn. wait ...");
    protected static Stage stage;
    private static TextField CheatField = new TextField();
    protected static int selectedRow = -1, selectedColumn = -1;
    protected static Unit selectedUnit;
    protected static City selectedCity;
    private static boolean isMyTurn;
    private static Button nextTurnButton;

    private static void putRiver(int x, int y, int w, int h){
        ImageView river = new ImageView(GameView.class.getClassLoader().getResource("images/river.png").toExternalForm());
        river.setTranslateX(x);
        river.setTranslateY(y);
        river.setFitWidth(w);
        river.setFitHeight(h);
        map.getChildren().add(river);
    }

    private static Tooltip getToolTip(Tile tile){
        if(tile.getFogState() == FogState.UNKNOWN){
            return new Tooltip(tile.getRow() + "," + tile.getColumn());
        }
        StringBuilder text = new StringBuilder(tile.getRow() + "," + tile.getColumn() + "\n"
                + tile.getTerrainType().name + "," + tile.getTerrainFeature().name + "," + tile.getResourceType().name + "\n");
        if(tile.getRuin() != null) text.append("Ruin!\n");
        if(tile.getCity() != null) text.append(tile.getCity().getName() + "\n");
        if(tile.getUnit() != null) text.append(tile.getUnit().getUnitType().name);
        text.append(" - ");
        if(tile.getTroop() != null) text.append(tile.getTroop().getUnitType().name);
        Tooltip tooltip = new Tooltip(text.toString());
        tooltip.setWrapText(true);
        tooltip.setShowDelay(Duration.seconds(0));
        return tooltip;
    }

    public static void makeMap() {
        map.getChildren().clear();
        System.err.println("map Cleared!");
        if (!(Boolean) Network.getResponseObjOf(RequestActions.IS_MY_TURN.code, null)) {
            System.err.println("This is not my turn!");
            map.setVisible(false);
            waitiingLable.setVisible(true);
            waitiingLable.setLayoutX(WIDTH / 2);
            new Thread(() -> {
                while (true) {
                    Boolean res = (Boolean) Network.getResponseObjOf(RequestActions.IS_MY_TURN.code, null);
                    if (res) {
                        Platform.runLater(() -> {
                            show(stage);
                        });
                        break;
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            return;
        }
        System.err.println("this is my turn");
        waitiingLable.setVisible(false);
        map.setVisible(true);
        Map gameMap = ((Map) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYERS_MAP.code, null));

        // putting rivers in
        for (int row = 0; row < gameMap.getHeight(); row++) {
            for (int column = 0; column < gameMap.getWidth(); column++) {
                int x, y;
                if (column % 2 == 0) {
                    y = row * 130 + 65;
                    x = column * 115;
                } else {
                    y = row * 130;
                    x = column * 115;
                }
                Tile tile = gameMap.getTile(row, column);
                if(tile.getUnit() != null) {
                    System.err.println("this one has a unit");
                }
                if (tile.getFogState() == FogState.UNKNOWN) continue;
                if(tile.getRiverInDirection(0) == 1){
                    putRiver(x, y - 10, 140, 20);
                }
                if(tile.getRiverInDirection(2) == 1){
                    putRiver(x + 110, y - 5, 40, 70);
                }
                if(tile.getRiverInDirection(4) == 1){
                    putRiver(x + 110, y + 60, 40, 70);
                }
                if(tile.getRiverInDirection(6) == 1){
                    putRiver(x, y + 110, 140, 20);
                }
                if(tile.getRiverInDirection(8) == 1){
                    putRiver(x - 10, y + 60, 40, 70);
                }
                if (tile.getRiverInDirection(10) == 1) {
                    putRiver(x - 10, y - 10, 40, 70);

                }
            }
        }

        // putting tiles in place
        for (int row = 0; row < gameMap.getHeight(); row++) {
            System.err.println("putting tiles ...");
            for (int column = 0; column < gameMap.getWidth(); column++) {
                int x, y;
                if (column % 2 == 0) {
                    y = row * 130 + 65;
                    x = column * 115;
                } else {
                    y = row * 130;
                    x = column * 115;
                }
                Pane image = gameMap.getTile(row, column).getTileImage();
                image.setTranslateX(x);
                image.setTranslateY(y);
                map.getChildren().add(image);

                Button button = new Button();
                button.setStyle("-fx-border-color: transparent;-fx-background-color: transparent;-fx-text-fill: transparent;");
                button.setPrefWidth(100);
                button.setPrefHeight(100);
                button.setTranslateX(x + 20);
                button.setTranslateY(y + 10);
                int thisRow = row, thisColumn = column;
                button.setOnMouseClicked(e -> {
                    selectTile(thisRow, thisColumn);
                    selectedColumn = thisColumn;
                    selectedRow = thisRow;

                    System.out.println(selectedRow + " " + selectedColumn + " is clicked");
                    map.requestFocus();
                });
                button.setFocusTraversable(false);
                button.setTooltip(getToolTip(gameMap.getTile(row, column)));
                map.getChildren().add(button);
            }
        }
        map.setOnKeyPressed(e -> moveMap(e));
    }

    public static void closePanels() { // TODO: 7/25/2022
        militaryPane.setVisible(false);
        notificationPane.setVisible(false);
        demographicsPane.setVisible(false);
        economyPane.setVisible(false);
        researchPane.setVisible(false);
        unitsPane.setVisible(false);
        citiesPane.setVisible(false);
        diplomacyPane.setVisible(false);

        selectedUnit = null;
        selectedCity = null;
        selectedRow = -1;
        selectedColumn = -1;

        unitSelectedPane.setVisible(false);
        citySelectedPane.setVisible(false);
    }

    public static void show(Stage primaryStage) {
        if(((boolean) Network.getResponseObjOf(RequestActions.AM_I_LOST.code, null))){
            showAlert(infoAlert, "YOU LOST!!!\n(not only in this game but in your whole life.\ncause you wasted so much of your time playing this stupid game)");
            Network.sendRequest(RequestActions.PASS_TURN.code, null);
            changeMenu(MenuType.MAIN_MENU);
            return;
        }
        if(((boolean) Network.getResponseObjOf(RequestActions.AM_I_WON.code, null))){
            showAlert(infoAlert, "YOU WON!!!\n(but at what cost)");
            Network.getResponseObjOf(RequestActions.END_GAME.code, null);
            changeMenu(MenuType.MAIN_MENU);
            return;
        }
        try {
            stage = primaryStage;
            root = new Pane();
            infoAlert = new Alert(Alert.AlertType.INFORMATION, "Hey!!!", ButtonType.OK);
            invalidAlert = new Alert(Alert.AlertType.ERROR, "invalid!", ButtonType.OK);
            selectedUnit = null;
            selectedCity = null;
            selectedRow = -1;
            selectedColumn = -1;
            Pane pane = root;
            initTopPane();
            map = new Pane();
            map.setVisible(true);
            makeMap();
            Player player = ((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null));
            cameraRow = player.getCameraRow();
            cameraColumn = player.getCameraColumn();
            setCamera();
            pane.getChildren().add(map);
            // initing panes
            initElements();

            // TODO: 7/25/2022
            pane.getChildren().addAll(topPane, panelsPane, notificationPane, militaryPane, economyPane, demographicsPane, unitsPane, citiesPane, diplomacyPane, unitSelectedPane, citySelectedPane, researchPane);
            waitiingLable.setVisible(false);
            pane.getChildren().add(waitiingLable);
            Platform.runLater(() -> map.requestFocus());
            pane.getChildren().add(nextTurnButton);
//            Button btn = new Button("_Mnemonic");
//            btn.setOnAction(e -> {
//                System.err.println("HELLLOOOOOOOOOOOOOOOO");
//                CheatField.setVisible(!CheatField.isVisible());
//            });
//            btn.setMaxSize(0,0);
//            btn.setVisible(false);
            CheatField.setTranslateX(WIDTH/2);
            CheatField.setTranslateY(HEIGHT/2-30);
            CheatField.setMinSize(100, 20);
            CheatField.setStyle("-fx-background-color: black; -fx-text-fill: white");
            CheatField.setVisible(false);
            CheatField.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER){
                    Network.sendRequest(new Request(RequestActions.PANEL_COMMAND.code,null, CheatField.getText()));
                    CheatField.setVisible(false);
                    CheatField.clear();
                }
            });

            pane.getChildren().add(CheatField);
//            topPane.getChildren().add(btn);
            Scene scene = new Scene(pane, WIDTH, HEIGHT);
            scene.getStylesheets().add(LoginMenu.class.getClassLoader().getResource("css/MenuStyle.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void initElements() {
        unitSelectedPane = new VBox();
        citySelectedPane = new VBox();
        initTopPane(); // TODO: 7/25/2022
        initPanelsPane();
        initNotificationPane();
        initMilitaryPane();
        initDemographicsPane();
        initEconomyPane();
        initResearchPane();
        initCitiesPane();
        initUnitsPane();
        initDiplomacyPane();
        nextTurnButton = new Button("pass turn");
        nextTurnButton.setOnMouseClicked((e -> passTurn()));
        nextTurnButton.setFocusTraversable(false);
        nextTurnButton.setLayoutX(0);
        nextTurnButton.setLayoutY(HEIGHT - 100);
        nextTurnButton.setMinWidth(100);
        nextTurnButton.setMinHeight(100);
        closePanels();
    }

    private static void passTurn() {
        // TODO: 7/15/2022 in web it must bo into a waiting state?
        closePanels();
        System.out.println("passing turn!!");
        Network.sendRequest(RequestActions.PASS_TURN.code, null);
        System.out.println("passing turn!!");

        show(stage);
        System.out.println("passing turn!!");
        updateElements();
    }


    private static void updateElements() {
        topPaneLabel.setText(MapMaker.getColorlessTopBar());
    }

    private static void initTopPane() {
        topPane = new HBox();
        topPane.setLayoutX(0);
        topPane.setLayoutY(0);
        topPane.setMinWidth(stage.getWidth());
        topPane.setStyle("-fx-background-color: #C0C0C0");

        topPaneLabel = new Label(MapMaker.getColorlessTopBar());
        topPaneLabel.setFont(Font.font(14));
        topPaneLabel.setStyle("-fx-font-family: 'monospaced'");
        topPaneLabel.setTextFill(Color.WHITE);
        topPane.getChildren().addAll(topPaneLabel);
    }

    private static void initPanelsPane() { // TODO: 7/25/2022
        panelsPane = new VBox();
        panelsPane.setVisible(true);
        panelsPane.setAlignment(Pos.CENTER);
        panelsPane.setLayoutY(15);
        panelsPane.setMinWidth(LEFT_WIDTH);
        panelsPane.setMinHeight(300);
        panelsPane.setStyle("-fx-background-color: rgb(26,141,113); -fx-background-size: 100, 100;");
        Label header = new Label("OPEN PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(24));
        header.setAlignment(Pos.CENTER);

        Button notifications = new Button("Notifications");
        notifications.setOnMouseClicked(e -> {
            closePanels();
            notificationPane.setVisible(true);
        });
        notifications.setFocusTraversable(false);
        Button military = new Button("Military");
        military.setOnMouseClicked(e -> {
            closePanels();
            militaryPane.setVisible(true);
        });
        military.setFocusTraversable(false);
        Button economy = new Button("Economy");
        economy.setOnMouseClicked(e -> {
            closePanels();
            economyPane.setVisible(true);
        });
        economy.setFocusTraversable(false);
        Button demographics = new Button("Demographics");
        demographics.setOnMouseClicked(e -> {
            closePanels();
            demographicsPane.setVisible(true);
        });
        demographics.setFocusTraversable(false);
        Button units = new Button("Units");
        units.setOnMouseClicked(e -> {
            closePanels();
            unitsPane.setVisible(true);
        });
        units.setFocusTraversable(false);
        Button cities = new Button("Cities");
        cities.setOnMouseClicked(e -> {
            closePanels();
            citiesPane.setVisible(true);
        });
        cities.setFocusTraversable(false);
        Button diplomacy = new Button("Diplomacy");
        diplomacy.setOnMouseClicked(e -> {
            closePanels();
            diplomacyPane.setVisible(true);
        });
        diplomacy.setFocusTraversable(false);
        Button research = new Button("Research");
        research.setOnMouseClicked(e -> {
            closePanels();
            researchPane.setVisible(true);
        });
        research.setFocusTraversable(false);
        Button closeAll = new Button("Close All");
        closeAll.setOnMouseClicked(e -> closePanels());
        closeAll.setFocusTraversable(false);

        // TODO: 7/25/2022
        panelsPane.getChildren().addAll(notifications, military, economy, demographics, units, cities, research, diplomacy, closeAll);
    }


    private static void initNotificationPane() {
        notificationPane = new VBox();
        notificationPane.setVisible(false);
        notificationPane.setAlignment(Pos.CENTER);
        notificationPane.setLayoutY(HEIGHT - BOTTOM_HEIGHT);
        notificationPane.setMinWidth(WIDTH);
        notificationPane.setMinHeight(BOTTOM_HEIGHT);
        notificationPane.setStyle("-fx-background-color: #C0C0C0; -fx-background-size: 100, 100;");
        Label header = new Label("NOTIFICATION PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(18));
        header.setAlignment(Pos.CENTER);
        Label content = new Label(NotificationsPanel.showPanel());
        content.setFont(Font.font(14));
        content.setTextFill(Color.WHITE);
        content.setStyle("-fx-font-family: 'monospaced'");

        notificationPane.getChildren().addAll(header, content);
    }

    private static void initMilitaryPane() {
        militaryPane = new VBox();
        militaryPane.setVisible(false);
        militaryPane.setAlignment(Pos.CENTER);
        militaryPane.setLayoutY(HEIGHT - BOTTOM_HEIGHT);
        militaryPane.setMinWidth(WIDTH);
        militaryPane.setMinHeight(BOTTOM_HEIGHT);
        militaryPane.setStyle("-fx-background-color: #C0C0C0; -fx-background-size: 100, 100;");
        Label header = new Label("MILITARY PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(18));
        header.setAlignment(Pos.CENTER);
        Label content = new Label(MilitaryPanel.printPanel());
        content.setFont(Font.font(14));
        content.setTextFill(Color.WHITE);
        content.setStyle("-fx-font-family: 'monospaced'");

        militaryPane.getChildren().addAll(header, content);
    }

    private static void initDemographicsPane() {
        demographicsPane = new VBox();
        demographicsPane.setVisible(false);
        demographicsPane.setAlignment(Pos.CENTER);
        demographicsPane.setLayoutY(HEIGHT - BOTTOM_HEIGHT);
        demographicsPane.setMinWidth(WIDTH);
        demographicsPane.setMinHeight(BOTTOM_HEIGHT);
        demographicsPane.setStyle("-fx-background-color: #C0C0C0; -fx-background-size: 100, 100;");
        Label header = new Label("DEMOGRAPHICS PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(18));
        header.setAlignment(Pos.CENTER);
        Label content = new Label(DemographicsPanel.printPanel());
        content.setFont(Font.font(14));
        content.setTextFill(Color.WHITE);
        content.setStyle("-fx-font-family: 'monospaced'");

        demographicsPane.getChildren().addAll(header, content);
    }

    private static void initEconomyPane() {
        economyPane = new VBox();
        economyPane.setVisible(false);
        economyPane.setAlignment(Pos.CENTER);
        economyPane.setLayoutY(HEIGHT - BOTTOM_HEIGHT);
        economyPane.setMinWidth(WIDTH);
        economyPane.setMinHeight(BOTTOM_HEIGHT);
        economyPane.setStyle("-fx-background-color: #C0C0C0; -fx-background-size: 100, 100;");
        Label header = new Label("ECONOMY PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(18));
        header.setAlignment(Pos.CENTER);
        Label content = new Label(EconomyPanel.printPanel());
        content.setFont(Font.font(14));
        content.setTextFill(Color.WHITE);
        content.setStyle("-fx-font-family: 'monospaced'");

        economyPane.getChildren().addAll(header, content);
    }

    private static void initUnitsPane() {
        unitsPane = new VBox();
        unitsPane.setVisible(false);
        unitsPane.setAlignment(Pos.CENTER);
        unitsPane.setLayoutY(HEIGHT - BOTTOM_HEIGHT);
        unitsPane.setMinWidth(WIDTH);
        unitsPane.setMinHeight(BOTTOM_HEIGHT);
        unitsPane.setStyle("-fx-background-color: #C0C0C0; -fx-background-size: 100, 100;");
        Label header = new Label("UNITS PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(18));
        header.setAlignment(Pos.CENTER);
        Label content = new Label(UnitsPanel.showPanel());
        content.setFont(Font.font(14));
        content.setTextFill(Color.WHITE);
        content.setStyle("-fx-font-family: 'monospaced'");

        unitsPane.getChildren().addAll(header, content);
    }

    private static void initCitiesPane() {
        citiesPane = new VBox();
        citiesPane.setVisible(false);
        citiesPane.setAlignment(Pos.CENTER);
        citiesPane.setLayoutY(HEIGHT - BOTTOM_HEIGHT);
        citiesPane.setMinWidth(WIDTH);
        citiesPane.setMinHeight(BOTTOM_HEIGHT);
        citiesPane.setStyle("-fx-background-color: #C0C0C0; -fx-background-size: 100, 100;");
        Label header = new Label("CITIES PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(18));
        header.setAlignment(Pos.CENTER);
        Label content = new Label(CitiesPanel.showPanel());
        content.setFont(Font.font(14));
        content.setTextFill(Color.WHITE);
        content.setStyle("-fx-font-family: 'monospaced'");

        citiesPane.getChildren().addAll(header, content);
    }

    private static void initDiplomacyPane() {
        diplomacyPane = new VBox();
        diplomacyPane.setVisible(false);
        diplomacyPane.setAlignment(Pos.CENTER);
        diplomacyPane.setLayoutX(WIDTH - RIGHT_WIDTH);
        diplomacyPane.setMinWidth(RIGHT_WIDTH);
        diplomacyPane.setMinHeight(HEIGHT);
        diplomacyPane.setStyle("-fx-background-color: rgba(33,43,66,0.5); -fx-background-size: 100, 100;");
        Label header = new Label("DIPLOMACY PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(22));
        header.setAlignment(Pos.CENTER);

        Label info = new Label(DiplomacyPanel.showPanel());
        info.setStyle("-fx-font-family: 'monospaced'");

        diplomacyPane.getChildren().addAll(header, info, getPossiblePlayersPane());
    }

    private static VBox getPossiblePlayersPane() {
        VBox enemies = new VBox();
        enemies.setAlignment(Pos.CENTER);
        enemies.setMinWidth(RIGHT_WIDTH / 2);
        Label header = new Label("DECLARE WAR ON:");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(20));
        header.setAlignment(Pos.CENTER);
        enemies.getChildren().add(header);

        Player player = ((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null));
        ArrayList<Player> players = ((Game) Network.getResponseObjOf(RequestActions.GET_GAME.code, null)).getPlayers();

        for (Player p1 : players) {
            if(!p1.getName().equals(player.getName()) && player.getInWarPlayerByName(p1.getName()) == null) {
                Button button = new Button(p1.getName());
                button.setFocusTraversable(false);
                button.setOnMouseClicked(e -> {
                    Network.getResponseObjOfPanelCommand("declare on -t " + p1.getName());
                    Network.getResponseObjOf(RequestActions.UPDATE_FIELD_OF_VIEW.code, null);
                    show(stage);
                });
                enemies.getChildren().add(button);

            }
        }

        return enemies;
    }

    private static void initResearchPane() {
        researchPane = new VBox();
        researchPane.setVisible(false);
        researchPane.setAlignment(Pos.CENTER);
        researchPane.setLayoutX(WIDTH - RIGHT_WIDTH);
        researchPane.setMinWidth(RIGHT_WIDTH);
        researchPane.setMinHeight(HEIGHT);
        researchPane.setStyle("-fx-background-color: rgba(33,43,66,0.5); -fx-background-size: 100, 100;");
        Label header = new Label("RESEARCH PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(22));
        header.setAlignment(Pos.CENTER);

        Label info = new Label(ClientResearchPanel.showPanel());
        info.setStyle("-fx-font-family: 'monospaced'");

        researchPane.getChildren().addAll(header, info, getPossibleTechsPane());
    }

    private static VBox getPossibleTechsPane() {
        VBox techs = new VBox();
        techs.setAlignment(Pos.CENTER);
        techs.setMinWidth(RIGHT_WIDTH / 2);
        Label header = new Label("RESEARCH TECHS:");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(20));
        header.setAlignment(Pos.CENTER);
        techs.getChildren().add(header);

        Player player = ((Player) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYER.code, null));
        for (TechnologyType possibleTech : player.getPossibleTechs()) {
            Button button = new Button(possibleTech.name);
            button.setFocusTraversable(false);
            button.setOnMouseClicked(e -> ClientResearchPanel.researchTech(possibleTech));
            button.setTooltip(new Tooltip("cost: " + possibleTech.cost + "\nunlocks: " + possibleTech.unlocks));
            techs.getChildren().add(button);
        }

        return techs;
    }

    private static void initUnitSelectedPane() {
        unitSelectedPane = new VBox();
        unitSelectedPane.setVisible(false);
        unitSelectedPane.setAlignment(Pos.CENTER);
        unitSelectedPane.setLayoutX(WIDTH - RIGHT_WIDTH);
        unitSelectedPane.setMinWidth(RIGHT_WIDTH);
        unitSelectedPane.setMinHeight(HEIGHT);
        unitSelectedPane.setStyle("-fx-background-color: rgba(33,43,66,0.5); -fx-background-size: 100, 100;");
        Label header = new Label("UNIT PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(22));
        header.setAlignment(Pos.CENTER);

        Label info = new Label(ClientUnitSelectedPanel.showSelected());
        info.setStyle("-fx-font-family: 'monospaced'");

        Button move = new Button("Move Unit");
        move.setOnMouseClicked(e -> ClientUnitSelectedPanel.moveTo());
        move.setFocusTraversable(false);
        Button buildCity = new Button("Build City");
        buildCity.setOnMouseClicked(e -> ClientUnitSelectedPanel.foundCity());
        buildCity.setFocusTraversable(false);
        Button sleep = new Button("Sleep");
        sleep.setOnMouseClicked(e -> ClientUnitSelectedPanel.sleep());
        sleep.setFocusTraversable(false);
        Button alert = new Button("Alert");
        alert.setOnMouseClicked(e -> ClientUnitSelectedPanel.alert());
        alert.setFocusTraversable(false);
        Button fortify = new Button("Fortify");
        fortify.setOnMouseClicked(e -> ClientUnitSelectedPanel.fortify());
        fortify.setFocusTraversable(false);
        Button heal = new Button("Heal");
        heal.setOnMouseClicked(e -> ClientUnitSelectedPanel.heal());
        heal.setFocusTraversable(false);
        Button wake = new Button("Wake Up");
        wake.setOnMouseClicked(e -> ClientUnitSelectedPanel.wake());
        wake.setFocusTraversable(false);
        Button delete = new Button("Delete");
        delete.setOnMouseClicked(e -> ClientUnitSelectedPanel.delete());
        delete.setFocusTraversable(false);
        Button buildRoad = new Button("Build Road");
        buildRoad.setOnMouseClicked(e -> ClientUnitSelectedPanel.buildRoad("Road"));
        buildRoad.setFocusTraversable(false);
        Button buildRailRoad = new Button("Build RailRoad");
        buildRailRoad.setOnMouseClicked(e -> ClientUnitSelectedPanel.buildRoad("Railroad"));
        buildRailRoad.setFocusTraversable(false);
        Button removeForest = new Button("Remove Forest");
        removeForest.setOnMouseClicked(e -> ClientUnitSelectedPanel.removeForest());
        removeForest.setFocusTraversable(false);
        Button removeJungle = new Button("Remove Jungle");
        removeJungle.setOnMouseClicked(e -> ClientUnitSelectedPanel.removeJungle());
        removeJungle.setFocusTraversable(false);
        Button removeMarsh = new Button("Remove Marsh");
        removeMarsh.setOnMouseClicked(e -> ClientUnitSelectedPanel.removeMarsh());
        removeMarsh.setFocusTraversable(false);
        Button removeRoad = new Button("Remove Road");
        removeRoad.setOnMouseClicked(e -> ClientUnitSelectedPanel.removeRoute());
        removeRoad.setFocusTraversable(false);
        Button pillage = new Button("Pillage");
        pillage.setOnMouseClicked(e -> ClientUnitSelectedPanel.pillage());
        pillage.setFocusTraversable(false);
        Button repair = new Button("Repair");
        repair.setOnMouseClicked(e -> ClientUnitSelectedPanel.repair());
        repair.setFocusTraversable(false);
        Button setUp = new Button("Set Up");
        setUp.setOnMouseClicked(e -> ClientUnitSelectedPanel.setup());
        setUp.setFocusTraversable(false);
        Button garrison = new Button("Garrison");
        garrison.setOnMouseClicked(e -> ClientUnitSelectedPanel.garrison());
        garrison.setFocusTraversable(false);
        Button attack = new Button("Attack");
        attack.setOnMouseClicked(e -> ClientUnitSelectedPanel.attack());
        attack.setFocusTraversable(false);

        unitSelectedPane.getChildren().addAll(header, info, move, buildCity, sleep, alert, fortify, heal, wake, delete, buildRoad, buildRailRoad, removeForest, removeJungle, removeMarsh, removeRoad, pillage, repair, setUp, garrison, attack);

        if(selectedUnit.getUnitType() == UnitType.WORKER){
            unitSelectedPane.getChildren().add(getPossibleImprovementsPane());
        }
    }

    private static VBox getPossibleImprovementsPane() {
        VBox improvements = new VBox();
        improvements.setAlignment(Pos.CENTER);
        improvements.setMinWidth(RIGHT_WIDTH / 2);
        Label header = new Label("BUILD IMPROVEMENT");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(20));
        header.setAlignment(Pos.CENTER);
        improvements.getChildren().add(header);

        for (ImprovementType possibleImprovement : selectedUnit.getPossibleImprovements()) {
            Button button = new Button(possibleImprovement.name);
            button.setFocusTraversable(false);
            button.setOnMouseClicked(e -> ClientUnitSelectedPanel.buildImprovement(possibleImprovement));
            improvements.getChildren().add(button);
        }

        return improvements;
    }

    private static void initCitySelectedPane() {
        citySelectedPane = new VBox();
        citySelectedPane.setVisible(false);
        citySelectedPane.setAlignment(Pos.CENTER);
        citySelectedPane.setLayoutX(WIDTH - RIGHT_WIDTH);
        citySelectedPane.setMinWidth(RIGHT_WIDTH);
        citySelectedPane.setMinHeight(HEIGHT);
        citySelectedPane.setStyle("-fx-background-color: rgba(33,43,66,0.5); -fx-background-size: 100, 100;");
        Label header = new Label("CITY PANEL");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(22));
        header.setAlignment(Pos.CENTER);

        Label info = new Label(ClientCitySelectedPanel.showBanner());
        info.setStyle("-fx-font-family: 'monospaced'");
        // setting buttons
        Button assignCitizen = new Button("Assign Citizen");
        assignCitizen.setOnMouseClicked(e -> ClientCitySelectedPanel.assignCitizen());
        assignCitizen.setFocusTraversable(false);
        Button freeCitizen = new Button("Free Citizen");
        freeCitizen.setOnMouseClicked(e -> ClientCitySelectedPanel.freeCitizen());
        freeCitizen.setFocusTraversable(false);
        Button buyTile = new Button("Buy Tile");
        buyTile.setOnMouseClicked(e -> ClientCitySelectedPanel.buyTile());
        buyTile.setFocusTraversable(false);
        Button attack = new Button("attack");
        attack.setOnMouseClicked(e -> ClientCitySelectedPanel.attack());
        attack.setFocusTraversable(false);
        Button delete = new Button("delete");
        delete.setOnMouseClicked(e -> ClientCitySelectedPanel.delete());
        delete.setFocusTraversable(false);

        // builds
        HBox hBox = new HBox(getPossibleUnitsPane(), getPossibleBuildingsPane());

        citySelectedPane.getChildren().addAll(header, info, assignCitizen, freeCitizen, buyTile, attack, delete, hBox);
    }

    private static VBox getPossibleUnitsPane() {
        VBox units = new VBox();
        units.setAlignment(Pos.CENTER);
        units.setMinWidth(RIGHT_WIDTH / 2);
        Label header = new Label("BUILD UNIT");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(20));
        header.setAlignment(Pos.CENTER);
        units.getChildren().add(header);

        for (UnitType possibleUnit : selectedCity.getPossibleUnits()) {
            Button button = new Button(possibleUnit.name + " - cost: " + possibleUnit.cost);
            button.setFocusTraversable(false);
            button.setOnMouseClicked(e -> ClientCitySelectedPanel.buildUnit(possibleUnit));
            units.getChildren().add(button);
        }

        return units;
    }

    private static VBox getPossibleBuildingsPane() {
        VBox buildings = new VBox();
        buildings.setAlignment(Pos.CENTER);
        buildings.setMinWidth(RIGHT_WIDTH / 2);
        Label header = new Label("BUILD BUILDING");
        header.setTextFill(Color.WHITE);
        header.setFont(Font.font(20));
        header.setAlignment(Pos.CENTER);
        buildings.getChildren().add(header);

        for (BuildingType possibleBuilding : selectedCity.getPossibleBuildings()) {
            Button button = new Button(possibleBuilding.name + " - cost: " + possibleBuilding.cost);
            button.setFocusTraversable(false);
            button.setOnMouseClicked(e -> ClientCitySelectedPanel.buildBuilding(possibleBuilding));
            buildings.getChildren().add(button);
        }

        return buildings;
    }

    /////////////////////

    public static void selectTile(int row, int column) {
        if (selectedRow != row || selectedColumn != column) {
            return;
        }
        Tile tile = ((Map) Network.getResponseObjOf(RequestActions.GET_THIS_PLAYERS_MAP.code, null)).getTile(row, column);
        if (tile.getCity() != null) {
            if (selectedCity == null){
                closePanels();
                selectedCity = tile.getCity();
                selectedUnit = null;
                root.getChildren().remove(citySelectedPane);
                initCitySelectedPane();
                citySelectedPane.setVisible(true);
                root.getChildren().add(citySelectedPane);
                Network.getResponseObjOfPanelCommand("select city -l " + row + " " + column);
                System.err.println("city");
            }
            else selectedCity = null;
        }
        if (selectedCity == null && tile.getUnit() != null) {
            if (selectedUnit == null || selectedUnit.getCombatType() != CombatType.CIVILIAN){
                closePanels();
                selectedUnit = tile.getUnit();
                selectedCity = null;
                root.getChildren().remove(unitSelectedPane);
                initUnitSelectedPane();
                unitSelectedPane.setVisible(true);
                root.getChildren().add(unitSelectedPane);
                Network.getResponseObjOfPanelCommand("select unit -l " + row + " " + column);
                System.err.println("unit");
            }
            else selectedUnit = null;
        }
        if (selectedUnit == null && selectedCity == null && tile.getTroop() != null) {
            closePanels();
            selectedUnit = tile.getTroop();
            selectedCity = null;
            root.getChildren().remove(unitSelectedPane);
            initUnitSelectedPane();
            unitSelectedPane.setVisible(true);
            root.getChildren().add(unitSelectedPane);
            Network.getResponseObjOfPanelCommand("select troop -l " + row + " " + column);
            System.err.println("troop");
        }
        System.out.println(selectedRow + "," + selectedColumn);
        System.out.println(selectedUnit);
        System.out.println(selectedCity);
    }

    private static int getCameraX(int row, int column) {
        return column * 115;
    }

    private static int getCameraY(int row, int column) {
        if (column % 2 == 0) {
            return row * 130 + 65;
        } else {
            return row * 130;
        }
    }

    private static void setCamera() {
        map.setTranslateX(-getCameraX(cameraRow, cameraColumn) + WIDTH / 2 - 70);
        map.setTranslateY(-getCameraY(cameraRow, cameraColumn) + HEIGHT / 2 - 60);
    }

    public static void moveMap(KeyEvent keyEvent) {
        String keyName = keyEvent.getCode().getName();
        switch (keyName) {
            case "Down":
                Network.getResponseObjOfPanelCommand("move map -d d 1");
                cameraRow++;
                break;
            case "Up":
                Network.getResponseObjOfPanelCommand("move map -d u 1");
                cameraRow--;
                break;
            case "Right":
                Network.getResponseObjOfPanelCommand("move map -d r 1");
                cameraColumn++;
                break;
            case "Left":
                Network.getResponseObjOfPanelCommand("move map -d l 1");
                cameraColumn--;
                break;
            case "C":
                CheatField.setVisible(!CheatField.isVisible());
                break;
        }
        setCamera();
    }
}
