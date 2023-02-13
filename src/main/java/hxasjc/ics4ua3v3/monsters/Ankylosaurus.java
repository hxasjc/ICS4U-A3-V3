package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;

import java.util.Map;

/**
 * The class representing the Anklesaurus dinosaur. Source: DnD 5e Monster Manual p79
 */
public class Ankylosaurus extends Monster {
    static {
        try {
            BattleApp.CURRENT_APP.getValue().getBattleBeginController().addMonster(Ankylosaurus.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Creates a new Anklesaurus
     */
    public Ankylosaurus() {
        super(
                DiceRoll.rollDice(8, 12, 16),
                15,
                Map.ofEntries(
                        Map.entry(StatRolls.STR, 19),
                        Map.entry(StatRolls.DEX, 11),
                        Map.entry(StatRolls.CON, 15),
                        Map.entry(StatRolls.INT, 2),
                        Map.entry(StatRolls.WIS, 12),
                        Map.entry(StatRolls.CHA, 5)
                ),
                3
        );

        setPrimaryAttack(
                new Attack()
                        .name("Tail")
                        .attackModifier(7)
                        .damage(new DieRollPreset(4, 6, 4), DamageType.BLUDGEONING)
                //TODO add mechanic that requires DC14 STR saving throw or target is knocked prone
        );
    }

    /**
     * @see Monster#ping()
     */
    public static void ping(){}
}
