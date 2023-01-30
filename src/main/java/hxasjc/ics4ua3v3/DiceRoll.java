package hxasjc.ics4ua3v3;

import java.time.Instant;
import java.util.Random;

public class DiceRoll {
    private static final Random random = new Random();

    public static int rollDice(int numDice, int dieSides, int modifier) {
        //Random random = new Random(Instant.now().getEpochSecond());

        switch (dieSides) {
            case 4, 6, 8, 10, 12, 20, 100 -> {
                int total = 0;

                for (int i = 0; i < numDice; i++) {
                    total += random.nextInt(1, dieSides + 1);
                }

                total = total + modifier;

                return total;
            }

            default -> throw new IllegalArgumentException("Cannot roll a die with " + dieSides + " sides");
        }
    }

    public static int rollDice(int numDice, int dieSides) {
        return rollDice(numDice, dieSides, 0);
    }

    public static int rollDice(DieRollPreset rollPreset) {
        int result = rollDice(rollPreset.numDice(), rollPreset.dieSides(), rollPreset.modifier());
        System.out.println(rollPreset + " " + result);
        return result;
    }

    public static int rollD20() {
        return rollDice(1, 20, 0);
    }
}
