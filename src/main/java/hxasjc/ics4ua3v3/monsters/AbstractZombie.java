package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.StatRolls;

import java.util.Map;

public abstract class AbstractZombie extends Monster {
    protected AbstractZombie(int health, int armourClass, Map<StatRolls, Integer> skillScores) {
        super(health, armourClass, skillScores);
    }

    @Override
    protected boolean onKnockOut(int damage) {
        if (rollSavingThrow(StatRolls.CON, 5 + damage)) {
            heal(Math.abs(1 - getHealth()));
        }
        return true;
    }
}
