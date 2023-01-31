package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;

import java.util.Map;

/**
 * The class representing Ogre Zombies. Source: DnD 5e Monster Manual p316
 */
public class OgreZombie extends AbstractZombie {
    static {
        try {
            BattleApp.CURRENT_APP.getValue().getBattleBeginController().addMonster(OgreZombie.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * Creates a new Ogre Zombie
     */
    public OgreZombie() {
        super(
                DiceRoll.rollDice(9, 10, 36),
                8,
                Map.ofEntries(
                        Map.entry(StatRolls.STR, 19),
                        Map.entry(StatRolls.DEX, 6),
                        Map.entry(StatRolls.CON, 18),
                        Map.entry(StatRolls.INT, 3),
                        Map.entry(StatRolls.WIS, 6),
                        Map.entry(StatRolls.CHA, 5)
                ),
                2
        );
        addDamageImmunities(DamageType.POISON);
        setPrimaryAttack(
                new Attack()
                        .attackModifier(6)
                        .damage(new DieRollPreset(2, 8, 4), DamageType.BLUDGEONING)
                        .name("Morningstar")
        );
    }

    /**
     * @see Monster#ping()
     */
    public static void ping(){}
}
