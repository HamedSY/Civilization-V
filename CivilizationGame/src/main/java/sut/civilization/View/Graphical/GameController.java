package sut.civilization.View.Graphical;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sut.civilization.Enums.Menus;
import sut.civilization.Model.Classes.Building;
import sut.civilization.Model.Classes.Game;
import sut.civilization.Model.Classes.User;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {
    private final sut.civilization.Controller.GameControllers.GameController gameController = new sut.civilization.Controller.GameControllers.GameController();
    public Label chooseNation1;
    public ChoiceBox nations1;
    public ScrollPane scrollPane;

    public ArrayList<String> opponents = new ArrayList<>();
    public HashMap<String, ChoiceBox> selectedNations = new HashMap<>();


    public void mainMenu(MouseEvent mouseEvent) {
        Game.changeScene(Menus.MAIN_MENU);
    }

    public void startGame(MouseEvent mouseEvent) {
        if (canGameStart()){
            int nation_one = Integer.parseInt(String.valueOf(nations1.getValue().toString().charAt(0)));
            Game.getPlayersInGame().add(Game.getLoggedInUser());
            gameController.chooseNation(nation_one, 0);
            int i = 1;
            for (String opponent : opponents) {
                Game.getPlayersInGame().add(gameController.getUserByName(opponent));
                int nationNumber = Integer.parseInt(String.valueOf((selectedNations.get(opponent).getValue().toString().charAt(0))));
                gameController.chooseNation(nationNumber, i++);
            }
            for (User user : Game.getPlayersInGame()) {
                System.out.println(user.getNation().getNationType().name);
                System.out.println(user.getUsername());
            }
            Game.changeScene(Menus.GAME_MENU);
        }
    }

    public void loadGame(MouseEvent mouseEvent) {

    }

    public boolean canGameStart(){
//        if (nations1.getValue().equals(null)){
//            System.out.println("ridi");
//            return false;
//        }

        if (opponents.size() == 0)
            return false;
        return true;
    }

    public void initialize(){
        String[] nations = {"0- Indus Valley", "1- Maya", "2- Ancient Greece", "3- Persia", "4- Ancient Egypt", "5- Mesopotamian",
                "6- Rome", "7- Aztec", "8- Inca"};
        for (int i = 0; i < 9; i++) {
            Label label = new Label(nations[i]);
            nations1.getItems().add(label.getText());
        }


        HBox hBox = new HBox();
        VBox usernames = new VBox();
        VBox opponentsNations = new VBox();
        usernames.setPrefWidth(200);
        usernames.setPrefHeight(400);
        for (User user : Game.getUsers()) {
            Button button = new Button(user.getUsername());
            button.setPrefHeight(50);
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    button.setStyle("-fx-background-color: red");
                    opponents.add(button.getText());
                }
            });
            usernames.getChildren().add(button);

            ChoiceBox choiceBox = new ChoiceBox<>();
            choiceBox.setPrefHeight(50);
            for (int i = 0; i < 9; i++) {
                Label label = new Label(nations[i]);
                choiceBox.getItems().add(label.getText());
            }
            opponentsNations.getChildren().add(choiceBox);
            selectedNations.put(user.getUsername(), choiceBox);
        }

        hBox.getChildren().add(usernames);
        hBox.getChildren().add(opponentsNations);
        scrollPane.setContent(hBox);
    }
}
