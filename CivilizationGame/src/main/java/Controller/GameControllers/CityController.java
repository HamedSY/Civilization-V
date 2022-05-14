package Controller.GameControllers;

import Model.City;
import Model.Game;
import Model.Lands.Land;
import Model.Pair;

import java.util.regex.Matcher;

public class CityController extends GameController {

    public String buildCity(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Pair main = new Pair(x, y);
        if (isCityBuildable(main)){
            City city = new City(currentTurnUser.getNation());
            Land mainLand = Game.map[x][y];
            mainLand.setCityCenter(true);
            mainLand.setOwnerCity(city);

            Pair neighbors[] = new Pair[6];
            for (int i = 0; i < 6; i++)
                neighbors[i] = LandController.getNeighborIndex(main, i);
            for (int i = 0; i < 6; i++) {
                if (LandController.isPairValid(neighbors[i]))
                    Game.map[neighbors[i].x][neighbors[i].y].setOwnerCity(city);
            }
            return "City built successfully";
        }
        return "Can't build a city here";
    }

    public boolean isCityBuildable(Pair main){
        Pair neighbors[] = new Pair[6];
        for (int i = 0; i < 6; i++)
            neighbors[i] = LandController.getNeighborIndex(main, i);

        for (int i = 0; i < 6; i++) {
            if (LandController.isPairValid(neighbors[i])){
                Pair neighbors2[] = new Pair[6];
                for (int j = 0; j < 6; j++)
                    neighbors2[j] = LandController.getNeighborIndex(neighbors[i], j);

                for (int j = 0; j < 6; j++) {
                    if (LandController.isPairValid(neighbors2[j])){
                        if (Game.map[neighbors2[j].x][neighbors2[j].y].getOwnerCity() != null)
                            return false;
                    }
                }
            }
        }
        return true;
    }

    public void cityRangeAttack(Matcher matcher){

    }

    public void sendCitizen(Matcher matcher){

    }

    public String cityBuyLand(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Pair landPair = new Pair(x, y);

        Pair neighbors[] = new Pair[6];
        for (int i = 0 ; i<6 ; i++)
            neighbors[i] = LandController.getNeighborIndex(landPair, i);

        boolean canBuy = false;
        for (int i = 0; i < 6; i++) {
            if (LandController.isPairValid(neighbors[i])){
                if (Game.map[neighbors[i].x][neighbors[i].y].getOwnerCity().equals(selectedCity))
                    canBuy = true;
            }
        }

        Land land = Game.map[x][y];
        if (selectedCity.getOwnerNation().getCoin().getBalance() >= land.getCost()
                && canBuy && land.isBuyable()){
            land.setOwnerCity(selectedCity);
            selectedCity.getOwnerNation().getCoin().addBalance(-land.getCost());
            return "Land bought successfully";
        }
        return "The specific land is not buyable";

    }

    public void cityBuyBuilding(Matcher matcher){

    }

    public void cityProduceBuilding(Matcher matcher){

    }

    public void cityProgressBuilding(){

    }

    public void cityBuyUnit(){

    }

    public void cityProduceUnit(){

    }

    public void cityProgressUnit(){

    }

    public void cityChangeState(){

    }

    public void cityLevelUp(City city){

    }

}
