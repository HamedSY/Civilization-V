package Enums.GameEnums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands {
<<<<<<< HEAD:CivilizationGame/src/main/java/Enums/GameCommands.java
    cheatIncreaseGold("increase --gold (?<amount>\\d+)"),
    cheatIncreaseTurn("increase --turn (?<amount>\\d+)"),
    cheatIncreaseFood("increase --food (?<amount>\\d+)"),
    cheatIncreaseProduction("increase --production (?<amount>\\d+)");
    //TODO fill enums
    private final String regex;
=======
    SELECT_COMBAT_UNIT("select combat unit on -i (?<i>\\d+) -j (?<j>\\d+)") ,
    SELECT_CIVILIZED_UNIT("select civilized unit on -i (?<i>\\d+) -j (?<j>\\d+)"); //TODO fill enums
    private String regex;
>>>>>>> Logic/unitMovement:CivilizationGame/src/main/java/Enums/GameEnums/GameCommands.java

    GameCommands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, GameCommands gameCommands) {
        return Pattern.compile(gameCommands.regex).matcher(input);
    }
}