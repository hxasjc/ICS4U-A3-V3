package hxasjc.ics4ua3v3;

import java.util.HashMap;

public class RangeMap<T> extends HashMap<IntRange, T> {
    public T getResult(int i) {
        for (Entry<IntRange, T> entry : entrySet()) {
            if (entry.getKey().isInRange(i)) {
                return get(entry.getKey());
            }
        }
        throw new IllegalArgumentException("Could not find any ranges matching the given input: " + i);
    }
}
