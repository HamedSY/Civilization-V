import Controller.GameControllers.LandController;
import Enums.Consts;
import Model.Game;
import Model.LandPair;
import Model.Lands.Land;
import Model.Pair;
import View.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        HashMap<String, Integer> menuNumbers = new HashMap<>();
        menuNumbers.put("LoginMenu", 0);
        menuNumbers.put("MainMenu", 1);
        menuNumbers.put("GameMenu", 2);
        menuNumbers.put("ProfileMenu", 3);

        Menu[] menus = new Menu[4];
        menus[0] = new LoginMenu();
        menus[1] = new MainMenu();
        menus[2] = new GameMenu();
        menus[3] = new ProfileMenu();
        String currentMenu = "LoginMenu";
        Scanner scanner = new Scanner(System.in);
        LandController.printMap(Game.map);
        LandController.initializeDistances();
        LandController.updateDistances();
        while (!currentMenu.equals("EXIT")) {
            menus[menuNumbers.get(currentMenu)].run(scanner);
            currentMenu = Menu.getMenuName();
        }
        Game.saveUserListToDatabase();
    }


}
