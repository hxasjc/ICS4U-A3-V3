package hxasjc.ics4ua3v3;

public record DieRollPreset(int numDice, int dieSides, int modifier) {
    public DieRollPreset crit() {
        return new DieRollPreset(
                numDice() * 2,
                dieSides(),
                modifier()
        );
    }

    @Override
    public String toString() {
        return numDice + "d" + dieSides + " + " + modifier;
    }
}
