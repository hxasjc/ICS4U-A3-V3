package hxasjc.ics4ua3v3.monsters;

import hxasjc.ics4ua3v3.*;
import javafx.concurrent.ScheduledService;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

import static hxasjc.ics4ua3v3.DiceRoll.*;

/**
 * Base class that all monsters are based on and providing 99% of the functionality
 */
public abstract class Monster {
    /**
     * A map allowing easy translation from skill scores to modifiers (Source: DnD 5e Player's Handbook, p173)
     */
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

    /**
     * The monster's max health
     */
    private int maxHealth;

    /**
     * The monster's current health
     */
    private int health;

    /**
     * The monster's armour class
     */
    private int armourClass = 0;

    /**
     * A map of the monster's score for each skill
     */
    private Map<StatRolls, Integer> skillScores;

    /**
     * The monster's challenge rating
     */
    private double challengeRating;

    /**
     * Any damage types that the monster is immune to
     */
    private final HashSet<DamageType> damageImmunities = new HashSet<>();

    /**
     * Any damage types that the monster is vulnerable to
     */
    private final HashSet<DamageType> damageVulnerabilities = new HashSet<>();

    /**
     * Any damage types that the monster is resistant to
     */
    private final HashSet<DamageType> damageResistancea = new HashSet<>();

    /**
     * The monster's secondary attacks. These only get used if the monster is being controlled by the player
     */
    private final HashSet<Supplier<Attack>> secondaryAttacks = new HashSet<>();

    /**
     * The monster's primary attack. This is the only attack used by the computer
     */
    private Supplier<Attack> primaryAttack;

    /**
     * All monster subclasses should override this empty constructor. This will be called when a monster is summoned.
     */
    public Monster() {
        //
    }

    /**
     * Main constructor used for initializing monsters. This constructor must be called from a constructor with no parameters
     * @param health The monster's health
     * @param armourClass The monster's armour class
     * @param skillScores The monster's scores corresponding to each of the {@link StatRolls} stats
     * @param challengeRating The monster's challenge rating
     */
    protected Monster(int health, int armourClass, Map<StatRolls, Integer> skillScores, double challengeRating) {
        maxHealth = health;
        this.health = health;
        this.armourClass = armourClass;
        this.skillScores = skillScores;
        this.challengeRating = challengeRating;
    }

    /**
     * Checks if the given number is higher than or equal to the monster's armour class. This is used to determine if an attack hits or not
     * @param check The number to check
     * @return Whether the given number is higher than or equal to the monster's armour class
     */
    public final boolean checkAgainstArmourClass(int check) {
        return check >= armourClass;
    }

    /**
     * Checks if the monster is immune to attacks that deal the specified damage type
     * @param type The damage type to check
     * @return If the monster is immune to the given damage type
     */
    public final boolean checkAgainstDamageImmunities(DamageType type) {
        return damageImmunities.contains(type);
    }

    /**
     * Checks if the monster is vulnerable to attacks that deal the specified damage type
     * @param type The damage type to check
     * @return If the monster is vulnerable to the given damage type
     */
    public final boolean checkAgainstDamageVulnerabilities(DamageType type) {
        return damageVulnerabilities.contains(type);
    }

    /**
     * Checks if the monster is resistant to attacks that deal the specified damage type
     * @param type The damage type to check
     * @return If the monster is resistant to the given damage type
     */
    public final boolean checkAgainstDamageResistances(DamageType type) {
        return damageResistancea.contains(type);
    }

    /**
     * Get the monster's max health
     * @return The monster's max health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Get the monster's current health
     * @return The monster's current health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Get the monster's armour class
     * @return The monster's armour class
     */
    public final int getArmourClass() {
        return armourClass;
    }

    /**
     * Get the monster's score for the given stat
     * @param stat The stat to get the score for
     * @return The score for the given stat
     */
    public final int getStat(StatRolls stat) {
        return skillScores.get(stat);
    }

    /**
     * Get the supplier for the monster's primary attack
     * @return The supplier for the monster's primary attack
     */
    public Supplier<Attack> getPrimaryAttack() {
        return primaryAttack;
    }

    /**
     * Add new damage immunities
     * @param types Damage types the monster is immune to
     */
    protected final void addDamageImmunities(DamageType... types) {
        damageImmunities.addAll(Arrays.asList(types));
    }

    /**
     * Adds new damage vulnerabilities
     * @param types Damage types the monster is vulnerable to
     */
    protected final void addDamageVulnerabilities(DamageType... types) {
        damageVulnerabilities.addAll(Arrays.asList(types));
    }

    /**
     * Adds new damage resistances
     * @param types Damage types the monster is resistant to
     */
    protected final void addDamageResistances(DamageType... types) {
        damageResistancea.addAll(Arrays.asList(types));
    }

    /**
     * Set the monster's primary attack
     * @param attack The monster's primary attack
     */
    protected final void setPrimaryAttack(Attack attack) {
        primaryAttack = () -> attack;
    }

    /**
     * Set the monster's primary attack supplier
     * @param supplier The monster's primary attack supplier
     */
    protected final void setPrimaryAttack(Supplier<Attack> supplier) {
        primaryAttack = supplier;
    }

    /**
     * Add a new secondary attack
     * @param attack The monster's new secondary attack
     */
    protected final void addSecondaryAttack(Attack attack) {
        addSecondaryAttack(() -> attack);
    }

    /**
     * Add a new secondary attack supplier
     * @param supplier The supplier for the monster's new secondary attack
     */
    protected final void addSecondaryAttack(Supplier<Attack> supplier) {
        secondaryAttacks.add(supplier);
    }

    /**
     * Get a set of the monster's secondary attacks
     * @return The monster's secondary attacks
     */
    public HashSet<Supplier<Attack>> getSecondaryAttacks() {
        return secondaryAttacks;
    }

    /**
     * Rolls a saving throw for the stat
     * @param stat the stat to roll for
     * @param dc the difficulty class to beat
     * @return whether the monster succeeded on the saving throw
     */
    public final boolean rollSavingThrow(StatRolls stat, int dc) {
        int result = DiceRoll.rollD20() + SKILL_MODIFIER_MAP.getResult(skillScores.get(stat));
        return result >= dc;
    }

    /**
     * Heals the monster for the given amount, while preventing the health going over the max health
     * @param i The amount to heal for
     */
    public final void heal(int i) {
        health = health + i;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }

    /**
     * Deal damage to the monster
     * @param type The damage type to deal
     * @param amount The amount of damage to deal
     * @return If any damage was dealt
     */
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

            System.out.println("effective damage " + effectiveAmount);

            //health =- effectiveAmount;
            health = health - effectiveAmount;

            if (health < 0) {
                health = 0;
                onKnockOut(effectiveAmount);
            }

            return true;
        }

        return false;
    }

    public final boolean damageWithPrimary(Monster monster, BattleTranscript transcript) {
        return damageWithAttack(monster, this.primaryAttack.get(), transcript);
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

                System.out.println("damage " + damage);

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
            } else {
                System.out.println("fail ac check");
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

    public Image getMonsterImage() {
        try {
            return new Image(Monster.class.getResource(this.getClass().getSimpleName() + ".png").getPath());
        } catch (NullPointerException e) {
            return null;
        }
    }

    public double getChallengeRating() {
        return challengeRating;
    }

    public static String getChallengeRatingString(Monster monster) {
        double cr = monster.getChallengeRating();
        if (cr < 1) {
            return switch (Double.toString(cr)) {
                case "0.25" -> "1/4";
                case "0.5" -> "1/2";
                case "0.75" -> "3/4";
                default -> Double.toString(cr);
            };
        }
        return Double.toString(cr);
    }

    /**
     * All monsters have a ping method to allow me to initialize them, otherwise they will not load and are therefore unusable in the game.
     */
    public static void ping() {}
}
