package hxasjc.ics4ua3v3;

public class Attack {
    private int attackModifier;
    private DieRollPreset damageRoll;
    private DamageType damageType;
    private String name;

    public Attack() {}

    public Attack attackModifier(int i) {
        attackModifier = i;
        return this;
    }

    public Attack damage(DieRollPreset rollPreset, DamageType type) {
        damageRoll = rollPreset;
        damageType = type;
        return this;
    }

    public Attack name(String name) {
        this.name = name;
        return this;
    }

    public int getAttackModifier() {
        return attackModifier;
    }

    public DieRollPreset getDamageRoll() {
        return damageRoll;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public String getName() {
        return name;
    }
}
