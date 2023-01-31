package hxasjc.ics4ua3v3;

/**
 * A simple record representing an integer range
 * @param min The minimum value of the range, inclusive
 * @param max The maximum value of the range, inclusive
 */
public record IntRange(int min, int max) {
    /**
     * Checks if the specified int is in the range
     * @param i Number to check
     * @return Whether the number is in the range
     */
    public boolean isInRange(int i) {
        return (i >= min) && (i <= max);
    }
}
