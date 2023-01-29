package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

import static hxasjc.ics4ua3v3.DiceRoll.*;

public abstract class Monster {
    public static final RangeMap<Integer> SKILL_MODIFIER_MAP = new RangeMap<>();

    static {
        SKILL_MODIFIER_MAP.put(new IntRange(0, 1), -5);
        SKILL_MODIFIER_MAP.put(new IntRange(2, 3), -4);
        SKILL_MODIFIER_MAP.put(new IntRange(4, 5), -3);
        SKILL_MODIFIER_MAP.put(new IntRange(6, 7), -2);
        SKILL_MODIFIER_MAP.put(new IntRange(8, 9), -1);
        SKILL_MODIFIER_MAP.put(new IntRange(10, 11), 0);
        SKILL_MODIFIER_MAP.put(new IntRange(12, 13), 1);
        SKILL_MODIFIER_MAP.put(new IntRange(14, 15), 2);
        SKILL_MODIFIER_MAP.put(new IntRange(16, 17), 3);
        SKILL_MODIFIER_MAP.put(new IntRange(18, 19), 4);
        SKILL_MODIFIER_MAP.put(new IntRange(20, 21), 5);
        SKILL_MODIFIER_MAP.put(new IntRange(22, 23), 6);
        SKILL_MODIFIER_MAP.put(new IntRange(24, 25), 7);
        SKILL_MODIFIER_MAP.put(new IntRange(26, 27), 8);
        SKILL_MODIFIER_MAP.put(new IntRange(28, 29), 9);
        SKILL_MODIFIER_MAP.put(new IntRange(30, 31), 10);
    }

    private int maxHealth;
    private int health;
    private int armourClass = 0;
    private Map<StatRolls, Integer> skillScores;
    private final HashSet<DamageType> damageImmunities = new HashSet<>();
    private final HashSet<DamageType> damageVulnerabilities = new HashSet<>();
    private final HashSet<DamageType> damageResistancea = new HashSet<>();
    private Supplier<Attack> primaryAttack;

    /**
     * All monster subclasses should override this empty constructor. This will be called when a monster is summoned.
     */
    public Monster() {
        //
    }

    /**
     * Use this constructor for initializing a monster. It should be called from an empty constructor.
     *
     * @param health
     * @param armourClass
     * @param skillScores
     */
    protected Monster(int health, int armourClass, Map<StatRolls, Integer> skillScores) {
        maxHealth = health;
        this.health = health;
        this.armourClass = armourClass;
        this.skillScores = skillScores;
    }

    public final boolean checkAgainstArmourClass(int check) {
        return check >= armourClass;
    }

    public final boolean checkAgainstDamageImmunities(DamageType type) {
        return damageImmunities.contains(type);
    }

    public final boolean checkAgainstDamageVulnerabilities(DamageType type) {
        return damageVulnerabilities.contains(type);
    }

    public final boolean checkAgainstDamageResistances(DamageType type) {
        return damageResistancea.contains(type);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public final int getArmourClass() {
        return armourClass;
    }

    public final int getStat(StatRolls stat) {
        return skillScores.get(stat);
    }

    public Supplier<Attack> getPrimaryAttack() {
        return primaryAttack;
    }

    protected final void addDamageImmunities(DamageType... types) {
        damageImmunities.addAll(Arrays.asList(types));
    }

    protected final void addDamageVulnerabilities(DamageType... types) {
        damageVulnerabilities.addAll(Arrays.asList(types));
    }

    protected final void addDamageResistances(DamageType... types) {
        damageResistancea.addAll(Arrays.asList(types));
    }

    protected final void setPrimaryAttack(Attack attack) {
        primaryAttack = () -> attack;
    }

    protected final void setPrimaryAttack(Supplier<Attack> supplier) {
        primaryAttack = supplier;
    }

    public final boolean rollSavingThrow(StatRolls stat, int dc) {
        int result = DiceRoll.rollD20() + SKILL_MODIFIER_MAP.getResult(skillScores.get(stat));
        return result >= dc;
    }

    public final void heal(int i) {
        health =+ i;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    public final boolean damage(DamageType type, int amount) {
        if (!checkAgainstDamageImmunities(type)) {
            int effectiveAmount;

            if (checkAgainstDamageVulnerabilities(type)) {
                effectiveAmount = amount * 2;
            } else if (checkAgainstDamageResistances(type)) {
                effectiveAmount = amount / 2;
            } else {
                effectiveAmount = amount;
            }

            health =- effectiveAmount;

            if (health < 0) {
                health = 0;
                onKnockOut(effectiveAmount);
            }

            return true;
        }

        return false;
    }

    public final boolean damageWithPrimary(Monster monster, BattleTranscript transcript) {
        return damageWithAttack(monster, monster.primaryAttack.get(), transcript);
    }

    public final boolean damageWithAttack(Monster target, Attack attack, BattleTranscript transcript) {
        if (!target.checkAgainstDamageImmunities(attack.getDamageType())) {
            int attackDie = rollD20();
            int toAttack = attackDie + attack.getAttackModifier();

            if (target.checkAgainstArmourClass(toAttack)) {
                int damage;

                if (attackDie == 20) {
                    damage = rollDice(attack.getDamageRoll().crit());
                } else {
                    damage = rollDice(attack.getDamageRoll());
                }

                target.damage(attack.getDamageType(), damage);
                transcript.add(
                        Util.getNiceName(this.getClass()) +
                                " attacked " +
                                Util.getNiceName(target.getClass()) +
                                " amd dealt " +
                                damage +
                                " " +
                                " damage"
                );

                return true;
            }
        } else {
            transcript.add(
                    Util.getNiceName(this.getClass()) +
                    " tried to attack " +
                            Util.getNiceName(target.getClass()) +
                            " using " +
                            attack.getName() +
                            " but " +
                            Util.getNiceName(target.getClass()) +
                            " is immune to " +
                            attack.getDamageType() +
                            " damage");
        }

        return false;
    }

    protected boolean onKnockOut(int damage) {
        return false;
    }
}
