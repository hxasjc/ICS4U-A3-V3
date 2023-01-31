package hxasjc.ics4ua3v3;

import java.util.Random;

/**
 * A utility class that provides methods for "rolling" dice
 */
public class DiceRoll {
    /**
     * Random instance used to roll dice
     */
    private static final Random random = new Random();

    /**
     * Main method in this class. The other methods simply provide a simpler way of using it
     * @param numDice The number of dice to roll
     * @param dieSides The number of side on a die to roll. Options are 4, 6, 8, 10, 12, 20, 100
     * @param modifier A modifier that is added to the result of the rolls before returning
     * @return The sum of the dice being rolled
     */
    public static int rollDice(int numDice, int dieSides, int modifier) {
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

    /**
     * Rolls dice without a modifier
     * @param numDice The number of dice to roll
     * @param dieSides The number of side on a die to roll. Options are 4, 6, 8, 10, 12, 20, 100
     * @return The sum of the dice being rolled
     */
    public static int rollDice(int numDice, int dieSides) {
        return rollDice(numDice, dieSides, 0);
    }

    /**
     * Rolls the dice described in the DieRollPreset
     * @param rollPreset The preset to roll
     * @return The sum of the dice being rolled
     */
    public static int rollDice(DieRollPreset rollPreset) {
        int result = rollDice(rollPreset.numDice(), rollPreset.dieSides(), rollPreset.modifier());
        System.out.println(rollPreset + " " + result);
        return result;
    }

    /**
     * Rolls a single D20 die
     * @return The result of rolling a single D20
     */
    public static int rollD20() {
        return rollDice(1, 20, 0);
    }
}
