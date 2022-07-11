package sut.civilization.Model.ModulEnums;

public enum CloseCombatUnitType {
    SCOUT("Scout", 25, 4, 2, null, null, 0, 10),
    SPEAR_MAN("Spearman", 50, 7, 2, null, TechnologyType.BRONZE_WORKING,
            0, 10),
    WARRIOR("Warrior", 40, 6, 2, null, null, 0, 10),
    HORSE_MAN("Horse Man", 80, 12, 4, ResourceType.HORSE, TechnologyType.HORSEBACK_RIDING,
            0, 10),
    SWORDS_MAN("Swords Man", 80, 11, 2, ResourceType.IRON, TechnologyType.IRON_WORKING,
            0, 10),
    KNIGHT("Knight", 150, 18, 3, ResourceType.HORSE, TechnologyType.CHIVALRY,
            0, 10),
    LONG_SWORDS_MAN("Longs Words Man", 150, 18, 3, ResourceType.IRON, TechnologyType.STEEL,
            0, 10),
    PIKE_MAN("Pike Man", 100, 10, 2, null, TechnologyType.CIVIL_SERVICE,
            0, 10),
    CAVALRY("Cavalry", 260, 25, 3, ResourceType.HORSE, TechnologyType.MILITARY_SCIENCE,
            0, 10),
    LANCER("Lancer", 220, 22, 4, ResourceType.HORSE, TechnologyType.METALLURGY,
            0, 10),
    MUSKET_MAN("Musket Man", 120, 16, 2, null, TechnologyType.GUNPOWDER,
            0, 10),
    RIFLE_MAN("Rifle Man", 200, 25, 2, null, TechnologyType.RIFLING,
            0, 10),
    ANTI_TANK_GUN("Anti-Tank Gun", 300, 32, 2, null,
            TechnologyType.REPLACEABLE_PARTS, 0, 10),
    INFANTRY("Infantry", 300, 36, 2, null, TechnologyType.REPLACEABLE_PARTS,
            0, 10),
    PANZER("Panzer", 450, 60, 5, null, TechnologyType.COMBUSTION,
            0, 10),
    TANK("Tank", 450, 50, 4, null, TechnologyType.COMBUSTION,
            0, 10);

    public final String name;
    public final int cost;
    public final int combatStrength;
    public final int MP;
    public final ResourceType resourceType;
    public final TechnologyType technologyType;
    public final int turns;
    public final int hp;
    CloseCombatUnitType(String name, int cost, int combatStrength, int MP, ResourceType resourceType, TechnologyType technologyType, int turns, int hp) {
        this.name = name;
        this.cost = cost;
        this.combatStrength = combatStrength;
        this.MP = MP;
        this.resourceType = resourceType;
        this.technologyType = technologyType;
        this.turns = turns;
        this.hp = hp;
    }
}
