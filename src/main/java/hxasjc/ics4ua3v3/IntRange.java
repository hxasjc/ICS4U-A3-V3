package hxasjc.ics4ua3v3;

public record IntRange(int min, int max) {
    public boolean isInRange(int i) {
        return (i >= min) && (i <= max);
    }
}
