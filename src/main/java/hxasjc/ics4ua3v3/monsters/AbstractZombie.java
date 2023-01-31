package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.StatRolls;

import java.util.Map;

/**
 * An abstract class providing the Undead Fortitude ability that all zombies have. The ability is triggered by {@link AbstractZombie#onKnockOut(int)}
 */
public abstract class AbstractZombie extends Monster {
    /**
     * Allows individual zombie classes to access the main constructor for monsters ({@link Monster#Monster(int, int, Map, double)}).
     * @see Monster#Monster(int, int, Map, double)  Monster
     * @param health The monster's health
     * @param armourClass The monster's armour class
     * @param skillScores The monster's scores corresponding to each of the {@link StatRolls} stats
     * @param challengeRating The monster's challenge rating
     */
    protected AbstractZombie(int health, int armourClass, Map<StatRolls, Integer> skillScores, double challengeRating) {
        super(health, armourClass, skillScores, challengeRating);
    }

    /**
     * Handles the functionality for the Undead Fortitude ability, which allows zombies to roll when they reach 0 health, to possible prevent death
     */
    @Override
    protected boolean onKnockOut(int damage) {
        if (rollSavingThrow(StatRolls.CON, 5 + damage)) {
            heal(Math.abs(1 - getHealth()));
        }
        return true;
    }
}
