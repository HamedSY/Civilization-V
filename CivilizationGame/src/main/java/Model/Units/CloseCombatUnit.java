package Model.Units;

import Model.Resources.Resource;

public class CloseCombatUnit extends Unit{
    protected int combatStrength;

    public CloseCombatUnit(String name, int hp, int movement, int cost, Resource resource, int combatStrength) {
        super(name, hp, movement, cost, resource);
        this.combatStrength = combatStrength;
    }

    public int getCombatStrength() {
        return combatStrength;
    }
}