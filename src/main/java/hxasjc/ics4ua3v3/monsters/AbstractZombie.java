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
     */
    protected AbstractZombie(int health, int armourClass, Map<StatRolls, Integer> skillScores, double challengeRating) {
        super(health, armourClass, skillScores, challengeRating);
    }

    /**
     * Handles the functionality for the Undead Fortitude ability, which allows zombies to roll when they reach 0 health, to possible prevent death
     * @param damage The damage dealt on the killing blow
     * @return Whether any special actions took place
     */
    @Override
    protected boolean onKnockOut(int damage) {
        if (rollSavingThrow(StatRolls.CON, 5 + damage)) {
            heal(Math.abs(1 - getHealth()));
        }
        return true;
    }
}
