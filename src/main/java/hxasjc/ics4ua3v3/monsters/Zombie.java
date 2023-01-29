package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;

import java.util.Map;

public class Zombie extends AbstractZombie {
    static {
        try {
            BattleApp.CURRENT_APP.getValue().getBattleBeginController().addMonster(Zombie.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Zombie() {
        super(DiceRoll.rollDice(3, 8, 9),
                8,
                Map.ofEntries(
                        Map.entry(StatRolls.STR, 13),
                        Map.entry(StatRolls.DEX, 6),
                        Map.entry(StatRolls.CON, 16),
                        Map.entry(StatRolls.INT, 3),
                        Map.entry(StatRolls.WIS, 6),
                        Map.entry(StatRolls.CHA, 5)
                )
        );
        addDamageImmunities(DamageType.POISON);
        setPrimaryAttack(
                new Attack()
                        .attackModifier(3)
                        .damage(new DieRollPreset(1, 6, 1), DamageType.BLUDGEONING)
                        .name("Slam"));
    }

    public static void ping(){}
}
