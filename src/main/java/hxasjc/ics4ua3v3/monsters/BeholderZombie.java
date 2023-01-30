package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;

import java.util.Map;
import java.util.function.Supplier;

/**
 * The class representing Beholder Zombies. Source: DnD 5e Monster Manual p316
 */
public class BeholderZombie extends AbstractZombie {
    static {
        try {
            BattleApp.CURRENT_APP.getValue().getBattleBeginController().addMonster(BeholderZombie.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Creates a new Beholder Zombie
     */
    public BeholderZombie() {
        super(DiceRoll.rollDice(11, 10, 33),
                15,
                Map.ofEntries(
                        Map.entry(StatRolls.STR, 10),
                        Map.entry(StatRolls.DEX, 8),
                        Map.entry(StatRolls.CON, 16),
                        Map.entry(StatRolls.INT, 3),
                        Map.entry(StatRolls.WIS, 8),
                        Map.entry(StatRolls.CHA, 5)
                ),
                5
        );
        addDamageImmunities(DamageType.POISON);

        Attack bite = new Attack()
                .attackModifier(3)
                .damage(new DieRollPreset(4, 6, 0), DamageType.PIERCING)
                .name("Bite");
        Supplier<Attack> eyeRay = () -> {
            throw new RuntimeException("Not implemented yet");
            //TODO implement
            /*switch (new Random().nextInt(4) + 1) {
                case 1 -> {
                    //
                }
            }*/
        };

        setPrimaryAttack(bite);
    }

    /**
     * @see Monster#ping()
     */
    public static void ping(){}
}
