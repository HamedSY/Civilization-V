package Controller;

import Controller.GameControllers.GameController;
import Model.Game;
import Model.Users.User;
import View.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

public class MainController extends Controller{

    public void logoutUser() {
        Game.setLoggedInUser(null);
        menuChange("login menu");
    }

    public String playGame(Matcher matcher) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(matcher.group(1).split("\\s*-p\\d+\\s*")));
        tokens.remove(0);
        ArrayList<User> players = new ArrayList<>();
        for (String token : tokens) {
            if (Game.getUserByName(token) == null) return "at least one user name doesn't exist";
            else players.add(Game.getUserByName(token));
        }

        Game.setPlayersInGame(players);
        GameController.setCurrentTurnUser(Game.getPlayersInGame().get(0));
        Menu.setMenuName("GameMenu");
        return "game successfully started!";
    }

    public String menuChange(String menuName){
        if (!menuName.equals("login menu") && !menuName.equals("profile menu")){
            return ("invalid menu name\navailable menu names : \"login menu\" \"profile menu\"");
        }

        return ("menu changed successfully");
    }
}
