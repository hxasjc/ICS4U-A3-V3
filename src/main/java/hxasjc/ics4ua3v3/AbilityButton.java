package hxasjc.ics4ua3v3;

import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * A subclass of JavaFX Button that is associated to a specific ability and automatically sets its text
 */
public class AbilityButton extends Button {
    /**
     * The attack associated with the button
     */
    private final Attack attack;

    /**
     * Creates a new AbilityButton using the specified attack
     * @param attack The attack to associate with the button
     */
    public AbilityButton(Attack attack) {
        super(getText(attack));
        this.attack = attack;
    }

    /**
     * Creates a new AbilityButton using the specified attack and setting a node as its parent. Note: setting a
     * button's parent this way is not something I have done before, I just added this constructor for completeness
     * @param attack The attack to associate with the button
     * @param parent The Node to set as this button's parent
     */
    public AbilityButton(Attack attack, Node parent) {
        super(getText(attack), parent);
        this.attack = attack;
    }

    /**
     * Get the attack associated with this button
     * @return The attack associated with this button
     */
    public Attack getAttack() {
        return attack;
    }

    /**
     * Generate the button text used for these buttons from an attack.
     * @param attack Attack to generate the text for
     * @return Text providing the name and damage roll of an attack (such as {@code Bite (2d6 + 3)})
     */
    private static String getText(Attack attack) {
        return attack.getName() + " (" + attack.getDamageRoll() + ")";
    }
}
