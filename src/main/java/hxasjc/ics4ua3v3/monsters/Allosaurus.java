package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;

import java.util.Map;

/**
 * The class representing the Allosaurus dinosaur. Source: DnD 5e Monster Manual p79
 */
public class Allosaurus extends Monster {
    static {
        try {
            BattleApp.CURRENT_APP.getValue().getBattleBeginController().addMonster(Allosaurus.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Creates a new Allosaurus
     */
    public Allosaurus() {
        super(
                DiceRoll.rollDice(6, 10, 18),
                13,
                Map.ofEntries(
                        Map.entry(StatRolls.STR, 19),
                        Map.entry(StatRolls.DEX, 13),
                        Map.entry(StatRolls.CON, 17),
                        Map.entry(StatRolls.INT, 2),
                        Map.entry(StatRolls.WIS, 12),
                        Map.entry(StatRolls.CHA, 5)
                ),
                2
        );
        setPrimaryAttack(
                new Attack()
                        .attackModifier(6)
                        .damage(new DieRollPreset(2, 10, 4), DamageType.PIERCING)
                        .name("Bite")
        );
        addSecondaryAttack(
                new Attack()
                        .attackModifier(6)
                        .damage(new DieRollPreset(1, 8, 4), DamageType.SLASHING)
                        .name("Claw")
        );
        //TODO implement pounce ability
    }

    /**
     * @see Monster#ping()
     */
    public static void ping(){}
}
