package Controller.GameControllers;

import Controller.Controller;
import Enums.Consts;
import Model.Improvements.ImprovementType;
import Model.Nations.Nation;
import Model.Nations.NationType;
import Model.Technologies.Technology;
import Model.Technologies.TechnologyType;
import Model.Units.*;
import Model.City;
import Model.Game;
import Model.Units.CombatUnit;
import Model.Units.Enums.CivilizedUnitType;
import Model.Units.Enums.UnitStatus;
import Model.Users.User;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class GameController extends Controller {
    protected static CombatUnit selectedCombatUnit;
    protected static CivilizedUnit selectedCivilizedUnit;
    protected static City selectedCity;
    protected static User currentTurnUser;
    private UnitController unitController;
    private WorkerController workerController;


    public static User getCurrentTurnUser() {
        return currentTurnUser;
    }

    public void chooseNation(int chosenNumber, int playerNum){

        Nation nation = switch (chosenNumber) {
            case 0 -> new Nation(NationType.INDUS_VALLEY);
            case 1 -> new Nation(NationType.MAYA);
            case 2 -> new Nation(NationType.ANCIENT_GREECE);
            case 3 -> new Nation(NationType.PERSIA);
            case 4 -> new Nation(NationType.ANCIENT_EGYPT);
            case 5 -> new Nation(NationType.MESOPOTAMIAN);
            case 6 -> new Nation(NationType.ROME);
            case 7 -> new Nation(NationType.AZTEC);
            default -> new Nation(NationType.INCA);
        };

        Game.getPlayersInGame().get(playerNum).setNation(nation);
    }

    public static void setCurrentTurnUser(User currentTurnUser) {
        GameController.currentTurnUser = currentTurnUser;
    }

    public ArrayList<String> showResearches() {
        ArrayList<String> output = new ArrayList<>();
        output.add("All of " + currentTurnUser.getNation().getNationType().name + "'s technologies:");
        int i = 0;
        for (TechnologyType technologyType : TechnologyType.values())
            if (currentTurnUser.getNation().getTechnologies().get(technologyType)) {
                output.add(i + "- " + technologyType.name);
                i++;
            }
        return output;
    }

    public ArrayList<String> showUnits() {
        ArrayList<String> output = new ArrayList<>();
        output.add("All of " + currentTurnUser.getNation().getNationType().name + "'s units:");
        int i = 1;
        for (Unit unit : currentTurnUser.getNation().getUnits()) {
            output.add(String.format("%d- %s\tLocation: (%d , %d)\tStatus: %s", i, unit.getName(), unit.getLocation().x, unit.getLocation().y, unit.getUnitStatus().toString()));
            i++;
        }
        return output;
    }

    public ArrayList<String> showCities() {
        ArrayList<String> output = new ArrayList<>();
        output.add("All of " + currentTurnUser.getNation().getNationType().name + "'s cities:");
        int i = 1;
        for (City city : currentTurnUser.getNation().getCities()) {
            if (city == currentTurnUser.getNation().getCapital()) output.add("(*Capital) ");
            output.add(String.format("%d- %s\tArea Size: %d\tPopulation: %d", i, city.getName(), city.getLands().size(), city.getCitizens()));
            i++;
        }
        return output;
    }

    public ArrayList<String> showDiplomacies() {
        ArrayList<String> output = new ArrayList<>();
        output.add(currentTurnUser.getNation().getNationType().name + "'s friends:");
        int i = 0;
        for (Nation nation : currentTurnUser.getNation().getFriends()) {
            output.add(String.format("%d- %s", i, nation.getNationType().name));
            i++;
        }

        output.add(currentTurnUser.getNation().getNationType().name + "'s enemies:");
        i = 0;
        for (Nation nation : currentTurnUser.getNation().getEnemies()) {
            output.add(String.format("%d- %s", i, nation.getNationType().name));
            i++;
        }
        return output;
    }

    public ArrayList<String> showDemographics() {
        ArrayList<String> output = new ArrayList<>();
        for (User user : Game.getPlayersInGame()) {
            output.add(user.getNation().getNationType().name + " :");
            int population = 0;
            for (City city : user.getNation().getCities())
                population += city.getCitizens();
            output.add("- Population: " + population);
            output.add("- Units: " + user.getNation().getUnits().size());
            int lands = 0;
            for (City city : user.getNation().getCities())
                lands += city.getLands().size();
            output.add("- Lands: " + lands);
        }
        return output;
    }

    public void showNotifications() {

    }

    public ArrayList<String> showMilitaries() {
        ArrayList<String> output = new ArrayList<>();
        output.add("All of " + currentTurnUser.getNation().getNationType().name + "'s combat units:");
        int i = 1;
        for (Unit unit : currentTurnUser.getNation().getUnits())
            if (unit instanceof CombatUnit) {
                output.add(String.format("%d- %s\tLocation: (%d , %d)\tStatus: %s", i, unit.getName(), unit.getLocation().x, unit.getLocation().y, unit.getUnitStatus().toString()));
                i++;
            }
        return output;
    }

    public ArrayList<String> showEconomics() {
        ArrayList<String> output = new ArrayList<>();
        output.add("Economic Overview:");
        int i = 0;
        for(City city : currentTurnUser.getNation().getCities()) {
            output.add(String.format("%d- %s\tLevel: %d\tStrength: %d\tCoin: %d\tFood: %d\tProduction: %d" ,
                    i, city.getName(), city.getLevel(), city.getHP(), city.getCoinGrowth(), city.getFoodGrowth(), city.getProductionGrowth()));
            i++;
        }
        return output;
    }
    

    public String selectCombatUnit(Matcher matcher) {
        int selectedLandI = Integer.parseInt(matcher.group("x"));
        int selectedLandJ = Integer.parseInt(matcher.group("y"));

        if (Game.map[selectedLandI][selectedLandJ].getCombatUnit() != null) {
            selectedCombatUnit = Game.map[selectedLandI][selectedLandJ].getCombatUnit();
            return (selectedCombatUnit.getName() + " is now selected");
        }
        return ("There is no combat unit here!");
    }

    public String selectCivilizedUnit(Matcher matcher) {
        int selectedLandI = Integer.parseInt(matcher.group("x"));
        int selectedLandJ = Integer.parseInt(matcher.group("y"));

        if (Game.map[selectedLandI][selectedLandJ].getCivilizedUnit() != null) {
            selectedCivilizedUnit = Game.map[selectedLandI][selectedLandJ].getCivilizedUnit();
            return (selectedCivilizedUnit.getName() + " is now selected");
        }
        return ("There is no civilized unit here!");
    }

    public String selectCity(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));

        if (Game.map[x][y].getOwnerCity() != null) {
            selectedCity = Game.map[x][y].getOwnerCity();
            return (selectedCity + " is now selected");
        }
        return "There is no city here!";

    }

    public void mapShow() {
        LandController.printMap(Game.map);
    }

    public void mapMove(Matcher matcher) {

    }

    public void nextPlayerTurn() {
        currentTurnUser = Game.getPlayersInGame().get(Game.getSubTurn() % Game.getPlayersInGame().size());
        Game.setSubTurn(Game.getSubTurn() + 1);
        if (Game.getSubTurn() == Game.getPlayersInGame().size()) {
            nextGameTurn();
            Game.setSubTurn(Game.getSubTurn() % Game.getPlayersInGame().size());
        }
    }

    public void nextGameTurn() {
        Game.setTurn(Game.getTurn() + 1);

        for (User user : Game.getPlayersInGame()) {
            Nation userNation = user.getNation();
            for (Unit unit : userNation.getUnits()) {
               nextTurnUnitMove(unit);
               nextTurnWorkerWorks(unit);
            }
            //update Currencies
            userNation.getCoin().addGrowthRateToBalance();
            userNation.getFood().addGrowthRateToBalance();
            userNation.getHappiness().addGrowthRateToBalance();
            userNation.getProduction().addGrowthRateToBalance();
            userNation.getScience().addGrowthRateToBalance();
        }

        //Create Unit => for in cities

        //Create Improvement

        //update Resources
        for (int i = 0; i < Consts.MAP_SIZE.amount.y;i++){
            for (int j = 0; j < Consts.MAP_SIZE.amount.x; j++) {

                Game.map[i][j].addGrowthToLandOwner();
            }
        }

        //update Technology progress

        LandController.printMap(Game.map);
    }

    public void nextTurnUnitMove(Unit unit){
        if (unit instanceof CivilizedUnit){
            unit.setMP(((CivilizedUnit) unit).getCivilizedUnitType().MP);
        }else if (unit instanceof  CloseCombatUnit){
            unit.setMP(((CloseCombatUnit) unit).getCloseCombatUnitType().MP);
        }else if (unit instanceof  RangedCombatUnit){
            unit.setMP(((RangedCombatUnit) unit).getRangedCombatUnitType().MP);
        }

        if (unit.getUnitStatus() == UnitStatus.ALERT || unit.getUnitStatus() == UnitStatus.AWAKE || unit.getUnitStatus() == UnitStatus.FORTIFY){
            if (unit.getPath() != ""){
                while (unit.getMP() > 0)
                    unitController.unitGoForward(unit);
            }
        }
    }

    private void nextTurnWorkerWorks(Unit unit) {
        if (unit instanceof CivilizedUnit &&
                ((CivilizedUnit) unit).getCivilizedUnitType() == CivilizedUnitType.WORKER) {
            if (unit.getUnitStatus() == UnitStatus.WORKING) {
                if (((CivilizedUnit) unit).getTurnsLeft() != 0) {
                    ((CivilizedUnit) unit).decreaseTurnsLeft(1);
                } else {
                    if (((CivilizedUnit) unit).getImprovementType() != null) {
                        workerController.workerBuildImprovement(((CivilizedUnit) unit).getImprovementType());
                        ((CivilizedUnit) unit).setImprovementType(null);
                        System.out.println(((CivilizedUnit) unit).getImprovementType().name + " was built!");
                    } else if (((CivilizedUnit) unit).getWorkerWorks() != null) {
                        workerController.workerWork(((CivilizedUnit) unit).getWorkerWorks());
                        ((CivilizedUnit) unit).setWorkerWorks(null);
                        System.out.println(((CivilizedUnit) unit).getWorkerWorks().toString() + " was done!");
                    } else System.out.println("ERROR!");
                }
            }
        }
    }

    public void saveGame() {

    }

}
