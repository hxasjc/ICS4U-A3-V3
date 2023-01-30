package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;

import java.util.Map;

/**
 * The class representing the Awakened Shrub, a shrub that has been awakened by magic. Source: DnD 5e Monster Manual p317
 */
public class AwakenedShrub extends Monster {
    static {
        try {
            BattleApp.CURRENT_APP.getValue().getBattleBeginController().addMonster(AwakenedShrub.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Creates a new Awakened Shrub
     */
    public AwakenedShrub() {
        super(
                DiceRoll.rollDice(3, 6),
                9,
                Map.ofEntries(
                        Map.entry(StatRolls.STR, 3),
                        Map.entry(StatRolls.DEX, 8),
                        Map.entry(StatRolls.CON, 11),
                        Map.entry(StatRolls.INT, 10),
                        Map.entry(StatRolls.WIS, 10),
                        Map.entry(StatRolls.CHA, 6)
                ),
                0
        );
        addDamageVulnerabilities(DamageType.FIRE);
        setPrimaryAttack(
                new Attack()
                        .attackModifier(1)
                        .damage(new DieRollPreset(1, 4, -1), DamageType.SLASHING)
                        .name("Rake")
        );
    }

    /**
     * @see Monster#ping()
     */
    public static void ping(){}
}
