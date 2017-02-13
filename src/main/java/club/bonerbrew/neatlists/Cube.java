package club.bonerbrew.neatlists;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides an abstraction for a four-dimensional {@link Map}
 * 
 * @author Rafael
 *
 * @param <R> the type for rows
 * @param <C> the type for columns
 * @param <P> the type for pages
 * @param <V> the type for values
 */
public class Cube<R, C, P, V> extends HashMap<R, Map<C, Map<P, V>>> {

    /**
     * 
     */
    private static final long serialVersionUID = 5184948037872736489L;

    /**
     * Creates an empty cube
     */
    public Cube() {
        
    }

    /**
     * Constructs an empty <tt>Cube</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public Cube(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a new <tt>Cube</tt> with the same mappings as the
     * specified <tt>Map</tt>.  The <tt>Cube</tt> is created with
     * default load factor (0.75) and an initial capacity sufficient to
     * hold the mappings in the specified <tt>Map</tt>.
     *
     * @param   m the map whose mappings are to be placed in this map
     * @throws  NullPointerException if the specified map is null
     */
    public Cube(Map<? extends R, ? extends Map<C, Map<P, V>>> m) {
        super(m);
    }

    /**
     * Creates a copy of the cube denoted by {@code cube}
     * 
     * @param cube the cube to copy
     */
    public Cube(Cube<R, C, P, V> cube) {
        super(cube);
    }

    public Cube(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Gets an object at the specified row, column and page.
     * @param row the row
     * @param column the column
     * @param page the page
     * @return the object at the specified row, column and page, or null if there is none.
     */
    public V get(R row, C column, P page) {
        Map<C, Map<P, V>> mR = super.get(row);
        if (mR != null) {
            Map<P, V> mC = mR.get(column);
            if (mC != null) {
                return mC.get(page);
            }
        }
        return null;
    }

    /**
     * Puts an object at the specified row, column and page.
     * @param row the row
     * @param column the column
     * @param page the page
     * @return the previous object at the specified row, column and page, or null if there was none.
     */
    public V put(R row, C column, P page, V value) {
        Map<C, Map<P, V>> mR = super.get(row);
        if (mR == null) {
            super.put(row, mR = new HashMap<>());
        }
        
        Map<P, V> mC = mR.get(column);
        if (mC == null) {
            mR.put(column, mC = new HashMap<>());
        }
        return mC.put(page, value);
    }

    public boolean containsKey(R row, C column, P page) {
        Map<C, Map<P, V>> mR = super.get(row);
        if (mR != null) {
            Map<P, V> mC = mR.get(column);
            if (mC != null) {
                return mC.containsKey(page);
            }
        }
        return false;
    }
    
    public boolean containsValue(Object value) {
        if (value instanceof Map) {
            return super.containsValue(value);
        } else {
            throw new UnsupportedOperationException("use #containsAnyValue(V) instead!");
        }
    }

    public boolean containsAnyValue(V value) {
        for (Map<C, Map<P, V>> rows : super.values()) {
            for (Map<P, V> columns : rows.values()) {
                if (columns.containsValue(value)) return true;
            }
        }
        return false;
    }
}
