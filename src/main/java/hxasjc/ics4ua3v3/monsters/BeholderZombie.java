package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;

import java.util.Map;
import java.util.function.Supplier;

public class BeholderZombie extends AbstractZombie {
    static {
        try {
            BattleApp.CURRENT_APP.getValue().getBattleBeginController().addMonster(BeholderZombie.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

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
                ));
        addDamageImmunities(DamageType.POISON);

        Attack bite = new Attack()
                .attackModifier(3)
                .damage(new DieRollPreset(4, 6, 0), DamageType.PIERCING)
                .name("Bite");
        Supplier<Attack> eyeRay = () -> {
            throw new RuntimeException("Not implemented yet");
            /*switch (new Random().nextInt(4) + 1) {
                case 1 -> {
                    //
                }
            }*/
        };

        setPrimaryAttack(bite);
    }

    public static void ping(){}
}
