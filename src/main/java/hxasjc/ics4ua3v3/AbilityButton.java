package hxasjc.ics4ua3v3;

import javafx.scene.Node;
import javafx.scene.control.Button;

public class AbilityButton extends Button {
    private final Attack attack;

    public AbilityButton(Attack attack) {
        super(getText(attack));
        this.attack = attack;
    }

    public AbilityButton(Attack attack, Node parent) {
        super(getText(attack), parent);
        this.attack = attack;
    }

    public Attack getAttack() {
        return attack;
    }

    private static String getText(Attack attack) {
        return attack.getName() + " (" + attack.getDamageRoll() + ")";
    }
}
