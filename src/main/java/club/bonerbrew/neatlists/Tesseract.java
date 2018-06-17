package fallk.neatlists;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides an abstraction for a five-dimensional {@link Map}
 * 
 * @author Rafael
 *
 * @param <R> the type for rows
 * @param <C> the type for columns
 * @param <P> the type for pages
 * @param <F> the type for frames
 * @param <V> the type for values
 */
public class Tesseract<R, C, P, F, V> extends HashMap<R, Map<C, Map<P, Map<F, V>>>> {

    /**
     * 
     */
    private static final long serialVersionUID = 5184948037872736489L;

    /**
     * Creates an empty cube
     */
    public Tesseract() {
        
    }

    /**
     * Constructs an empty <tt>Cube</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public Tesseract(int initialCapacity) {
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
    public Tesseract(Map<? extends R, ? extends Map<C, Map<P, Map<F, V>>>> m) {
        super(m);
    }

    /**
     * Creates a copy of the cube denoted by {@code cube}
     * 
     * @param cube the cube to copy
     */
    public Tesseract(Tesseract<R, C, P, F, V> cube) {
        super(cube);
    }

    public Tesseract(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Gets an object at the specified row, column and page.
     * @param row the row
     * @param column the column
     * @param page the page
     * @return the object at the specified row, column and page, or null if there is none.
     */
    public V get(R row, C column, P page, F frame) {
        Map<C, Map<P, Map<F, V>>> mR = super.get(row);
        if (mR != null) {
            Map<P, Map<F, V>> mC = mR.get(column);
            if (mC != null) {
                Map<F, V> mP = mC.get(page);
                if (mP != null) {
                    return mP.get(frame);
                }
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
    public V put(R row, C column, P page, F frame, V value) {
        Map<C, Map<P, Map<F, V>>> mR = super.get(row);
        if (mR == null) {
            super.put(row, mR = new HashMap<>());
        }

        Map<P, Map<F, V>> mC = mR.get(column);
        if (mC == null) {
            mR.put(column, mC = new HashMap<>());
        }

        Map<F, V> mP = mC.get(page);
        if (mP == null) {
            mC.put(page, mP = new HashMap<>());
        }
        return mP.put(frame, value);
    }

    public boolean containsKey(R row, C column, P page, F frame) {
        Map<C, Map<P, Map<F, V>>> mR = super.get(row);
        if (mR != null) {
            Map<P, Map<F, V>> mC = mR.get(column);
            if (mC != null) {
                Map<F, V> mP = mC.get(page);
                if (mP != null) {
                    return mP.containsKey(frame);
                }
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
        for (Map<C, Map<P, Map<F, V>>> rows : super.values()) {
            for (Map<P, Map<F, V>> columns : rows.values()) {
                for (Map<F, V> pages : columns.values()) {
                    if (pages.containsValue(value)) return true;
                }
            }
        }
        return false;
    }
}
