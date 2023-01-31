package hxasjc.ics4ua3v3;

/**
 * Provides a simple record allowing for the easy storage of a number of dice to roll
 * @param numDice The number of dice to roll
 * @param dieSides The number of side on a die to roll. Options are 4, 6, 8, 10, 12, 20, 100
 * @param modifier A modifier that is added to the result of the rolls before returning
 */
public record DieRollPreset(int numDice, int dieSides, int modifier) {
    /**
     * Doubles the number of dice. This is used when a monster rolls a 20 on their attack roll and scores a crit
     * @return A die roll preset with a doubled number of dice
     */
    public DieRollPreset crit() {
        return new DieRollPreset(
                numDice() * 2,
                dieSides(),
                modifier()
        );
    }

    /**
     * Generates a string representation following the standard notation for dice in DnD. For example rolling 2 20 sided die with a modifier of 5 would be represented as 2d20 + 5
     * @return A string representation of the dice roll preset
     */
    @Override
    public String toString() {
        return numDice + "d" + dieSides + " + " + modifier;
    }
}
