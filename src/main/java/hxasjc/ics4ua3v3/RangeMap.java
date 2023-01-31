package hxasjc.ics4ua3v3;

import java.util.HashMap;

/**
 * Custom data structure containing a number of ranges and allows you to get an item corresponding to the range the specified int is in
 * @param <T> The type of values in the map
 */
public class RangeMap<T> extends HashMap<IntRange, T> {

    /**
     * Get the value corresponding to the specified int
     * @param i The int to get the value for
     * @return The value corresponding to it
     */
    public T getResult(int i) {
        for (Entry<IntRange, T> entry : entrySet()) {
            if (entry.getKey().isInRange(i)) {
                return get(entry.getKey());
            }
        }
        throw new IllegalArgumentException("Could not find any ranges matching the given input: " + i);
    }
}
