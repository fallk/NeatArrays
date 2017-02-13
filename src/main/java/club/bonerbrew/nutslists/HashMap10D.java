
package club.bonerbrew.nutslists;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides an abstraction for an n-dimensional {@link Map}
 * 
 * @author Rafael
 *
 * @param <K1> the type for rows
 * @param <K2> the type for columns
 * @param <K3> the type for pages
 * @param <K4> the type for frames
 * @param <K5> the type for verses
 * @param <K6> the type for universes
 * @param <K...> take a wild guess
 * @param <V> the type for values
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class HashMap10D<K1, K2, K3, K4, K5, K6, K7, K8, K9, K10, V> extends HashMap {

    /**
     * 
     */
    private static final long serialVersionUID = 5184948037872736489L;

    /**
     * Creates an empty cube
     */
    public HashMap10D() {
        
    }

    /**
     * Constructs an empty <tt>Cube</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public HashMap10D(int initialCapacity) {
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
    public HashMap10D(Map m) {
        super(m);
    }

    /**
     * Creates a copy of the cube denoted by {@code cube}
     * 
     * @param cube the cube to copy
     */
    public HashMap10D(HashMap10D<K1, K2, K3, K4, K5, K6, K7, K8, K9, K10, V> cube) {
        super(cube);
    }

    public HashMap10D(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Gets an object at the specified row, column and page.
     * @param k1 the row
     * @param k2 the column
     * @param k3 the page
     * @return the object at the specified row, column and page, or null if there is none.
     */
    public V get(K1 k1, K2 k2, K3 k3, K4 k4, K5 k5, K6 k6, K7 k7, K8 k8, K9 k9, K10 k10) {
        Map m1 = (Map) super.get(k1);
        if (m1 == null) {
            return null;
        }

        
        Map m2 = (Map) m1.get(k2);
        if (m2 == null) {
            return null;
        }

        Map m3 = (Map) m2.get(k3);
        if (m3 == null) {
            return null;
        }

        Map m4 = (Map) m3.get(k4);
        if (m4 == null) {
            return null;
        }

        Map m5 = (Map) m4.get(k5);
        if (m5 == null) {
            return null;
        }

        Map m6 = (Map) m5.get(k6);
        if (m6 == null) {
            return null;
        }

        Map m7 = (Map) m6.get(k7);
        if (m7 == null) {
            return null;
        }

        Map m8 = (Map) m7.get(k8);
        if (m8 == null) {
            return null;
        }

        Map m9 = (Map) m8.get(k9);
        if (m9 == null) {
            return null;
        }
        
        return (V) m9.get(k10);
    }

    /**
     * Puts an object at the specified row, column and page.
     * @param k1 the row
     * @param k2 the column
     * @param k3 the page
     * @return the previous object at the specified row, column and page, or null if there was none.
     */
    public V put(K1 k1, K2 k2, K3 k3, K4 k4, K5 k5, K6 k6, K7 k7, K8 k8, K9 k9, K10 k10, V value) {
        Map m1 = (Map) super.get(k1);
        if (m1 == null) {
            super.put(k1, m1 = new HashMap<>());
        }

        
        Map m2 = (Map) m1.get(k2);
        if (m2 == null) {
            m1.put(k2, m2 = new HashMap<>());
        }

        Map m3 = (Map) m2.get(k3);
        if (m3 == null) {
            m2.put(k3, m3 = new HashMap<>());
        }

        Map m4 = (Map) m3.get(k4);
        if (m4 == null) {
            m3.put(k4, m4 = new HashMap<>());
        }

        Map m5 = (Map) m4.get(k5);
        if (m5 == null) {
            m4.put(k5, m5 = new HashMap<>());
        }

        Map m6 = (Map) m5.get(k6);
        if (m6 == null) {
            m5.put(k6, m6 = new HashMap<>());
        }

        Map m7 = (Map) m6.get(k7);
        if (m7 == null) {
            m6.put(k7, m7 = new HashMap<>());
        }

        Map m8 = (Map) m7.get(k8);
        if (m8 == null) {
            m7.put(k8, m8 = new HashMap<>());
        }

        Map m9 = (Map) m8.get(k9);
        if (m9 == null) {
            m8.put(k9, m9 = new HashMap<>());
        }

        return (V) m9.put(k10, value);
    }

    public boolean containsKey(K1 k1, K2 k2, K3 k3, K4 k4, K5 k5, K6 k6, K7 k7, K8 k8, K9 k9, K10 k10) {
        Map m1 = (Map)super.get(k1);
        if (m1 == null) {
            return false;
        }

        
        Map m2 = (Map)m1.get(k2);
        if (m2 == null) {
            return false;
        }

        Map m3 = (Map)m2.get(k3);
        if (m3 == null) {
            return false;
        }

        Map m4 = (Map)m3.get(k4);
        if (m4 == null) {
            return false;
        }

        Map m5 = (Map)m4.get(k5);
        if (m5 == null) {
            return false;
        }

        Map m6 = (Map)m5.get(k6);
        if (m6 == null) {
            return false;
        }

        Map m7 = (Map)m6.get(k7);
        if (m7 == null) {
            return false;
        }

        Map m8 = (Map)m7.get(k8);
        if (m8 == null) {
            return false;
        }

        Map m9 = (Map)m8.get(k9);
        if (m9 == null) {
            return false;
        }
                
        return m9.containsKey(k10);
    }
    
    public boolean containsValue(Object value) {
        if (value instanceof Map) {
            return super.containsValue(value);
        } else {
            throw new UnsupportedOperationException("use #containsAnyValue(V) instead!");
        }
    }

    public boolean containsAnyValue(V value) {
        for (Object m1 : super.values()) {
            for (Object m2 : ((Map) m1).values()) {for (Object m3 : ((Map) m2).values()) {for (Object m4 : ((Map) m3).values()) {for (Object m5 : ((Map) m4).values()) {for (Object m6 : ((Map) m5).values()) {for (Object m7 : ((Map) m6).values()) {for (Object m8 : ((Map) m7).values()) {for (Object m9 : ((Map) m8).values()) {
            if (((Map) m9).containsValue(value)) return true;
            }}}}}}}}
        }
        return false;
    }
}

