package hxasjc.ics4ua3v3;

/**
 * A basic attack. No special mechanics implemented yet. These attacks can have an attack roll modifier, damage roll and a name. There is no functionality for conditions or saving throws
 */
public class Attack {
    /**
     * The attack's attack modifier. This value is added to a D20 to determine if an attack hits
     */
    private int attackModifier = 0;

    /**
     * The number of dice that will be rolled to determine how much damage the attack deals
     */
    private DieRollPreset damageRoll = new DieRollPreset(0, 6, 0);

    /**
     * The damage type that this attack will deal
     */
    private DamageType damageType = DamageType.BLUDGEONING;

    /**
     * The attack's name
     */
    private String name = "Unnamed Attack";

    /**
     * Create a new attack object
     */
    public Attack() {}

    /**
     * Specify an attack modifier. Defaults to 0
     * @param i The attack's attack modifier
     * @return The current attack object
     */
    public Attack attackModifier(int i) {
        attackModifier = i;
        return this;
    }

    /**
     * Specify the attack's damage roll and type
     * @param rollPreset The attack's damage roll preset
     * @param type The attack's damage type
     * @return The current attack object
     */
    public Attack damage(DieRollPreset rollPreset, DamageType type) {
        damageRoll = rollPreset;
        damageType = type;
        return this;
    }

    /**
     * Specify the attack's name
     * @param name The attack's name
     * @return The current attack object
     */
    public Attack name(String name) {
        this.name = name;
        return this;
    }

    /**
     * Get the attack's attack modifier
     * @return The attack modifier
     */
    public int getAttackModifier() {
        return attackModifier;
    }

    /**
     * Get the attack's damage roll
     * @return The damage roll
     */
    public DieRollPreset getDamageRoll() {
        return damageRoll;
    }

    /**
     * Get the attack's damage type
     * @return The damage type
     */
    public DamageType getDamageType() {
        return damageType;
    }

    /**
     * Get the attack's name
     * @return The attack's name
     */
    public String getName() {
        return name;
    }
}
