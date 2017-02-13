
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
public class HashMap33D<K1, K2, K3, K4, K5, K6, K7, K8, K9, K10, K11, K12, K13, K14, K15, K16, K17, K18, K19, K20, K21, K22, K23, K24, K25, K26, K27, K28, K29, K30, K31, K32, K33, V> extends HashMap {

    /**
     * 
     */
    private static final long serialVersionUID = 5184948037872736489L;

    /**
     * Creates an empty cube
     */
    public HashMap33D() {
        
    }

    /**
     * Constructs an empty <tt>Cube</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public HashMap33D(int initialCapacity) {
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
    public HashMap33D(Map m) {
        super(m);
    }

    /**
     * Creates a copy of the cube denoted by {@code cube}
     * 
     * @param cube the cube to copy
     */
    public HashMap33D(HashMap33D<K1, K2, K3, K4, K5, K6, K7, K8, K9, K10, K11, K12, K13, K14, K15, K16, K17, K18, K19, K20, K21, K22, K23, K24, K25, K26, K27, K28, K29, K30, K31, K32, K33, V> cube) {
        super(cube);
    }

    public HashMap33D(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Gets an object at the specified row, column and page.
     * @param k1 the row
     * @param k2 the column
     * @param k3 the page
     * @return the object at the specified row, column and page, or null if there is none.
     */
    public V get(K1 k1, K2 k2, K3 k3, K4 k4, K5 k5, K6 k6, K7 k7, K8 k8, K9 k9, K10 k10, K11 k11, K12 k12, K13 k13, K14 k14, K15 k15, K16 k16, K17 k17, K18 k18, K19 k19, K20 k20, K21 k21, K22 k22, K23 k23, K24 k24, K25 k25, K26 k26, K27 k27, K28 k28, K29 k29, K30 k30, K31 k31, K32 k32, K33 k33) {
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

        Map m10 = (Map) m9.get(k10);
        if (m10 == null) {
            return null;
        }

        Map m11 = (Map) m10.get(k11);
        if (m11 == null) {
            return null;
        }

        Map m12 = (Map) m11.get(k12);
        if (m12 == null) {
            return null;
        }

        Map m13 = (Map) m12.get(k13);
        if (m13 == null) {
            return null;
        }

        Map m14 = (Map) m13.get(k14);
        if (m14 == null) {
            return null;
        }

        Map m15 = (Map) m14.get(k15);
        if (m15 == null) {
            return null;
        }

        Map m16 = (Map) m15.get(k16);
        if (m16 == null) {
            return null;
        }

        Map m17 = (Map) m16.get(k17);
        if (m17 == null) {
            return null;
        }

        Map m18 = (Map) m17.get(k18);
        if (m18 == null) {
            return null;
        }

        Map m19 = (Map) m18.get(k19);
        if (m19 == null) {
            return null;
        }

        Map m20 = (Map) m19.get(k20);
        if (m20 == null) {
            return null;
        }

        Map m21 = (Map) m20.get(k21);
        if (m21 == null) {
            return null;
        }

        Map m22 = (Map) m21.get(k22);
        if (m22 == null) {
            return null;
        }

        Map m23 = (Map) m22.get(k23);
        if (m23 == null) {
            return null;
        }

        Map m24 = (Map) m23.get(k24);
        if (m24 == null) {
            return null;
        }

        Map m25 = (Map) m24.get(k25);
        if (m25 == null) {
            return null;
        }

        Map m26 = (Map) m25.get(k26);
        if (m26 == null) {
            return null;
        }

        Map m27 = (Map) m26.get(k27);
        if (m27 == null) {
            return null;
        }

        Map m28 = (Map) m27.get(k28);
        if (m28 == null) {
            return null;
        }

        Map m29 = (Map) m28.get(k29);
        if (m29 == null) {
            return null;
        }

        Map m30 = (Map) m29.get(k30);
        if (m30 == null) {
            return null;
        }

        Map m31 = (Map) m30.get(k31);
        if (m31 == null) {
            return null;
        }

        Map m32 = (Map) m31.get(k32);
        if (m32 == null) {
            return null;
        }
        
        return (V) m32.get(k33);
    }

    /**
     * Puts an object at the specified row, column and page.
     * @param k1 the row
     * @param k2 the column
     * @param k3 the page
     * @return the previous object at the specified row, column and page, or null if there was none.
     */
    public V put(K1 k1, K2 k2, K3 k3, K4 k4, K5 k5, K6 k6, K7 k7, K8 k8, K9 k9, K10 k10, K11 k11, K12 k12, K13 k13, K14 k14, K15 k15, K16 k16, K17 k17, K18 k18, K19 k19, K20 k20, K21 k21, K22 k22, K23 k23, K24 k24, K25 k25, K26 k26, K27 k27, K28 k28, K29 k29, K30 k30, K31 k31, K32 k32, K33 k33, V value) {
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

        Map m10 = (Map) m9.get(k10);
        if (m10 == null) {
            m9.put(k10, m10 = new HashMap<>());
        }

        Map m11 = (Map) m10.get(k11);
        if (m11 == null) {
            m10.put(k11, m11 = new HashMap<>());
        }

        Map m12 = (Map) m11.get(k12);
        if (m12 == null) {
            m11.put(k12, m12 = new HashMap<>());
        }

        Map m13 = (Map) m12.get(k13);
        if (m13 == null) {
            m12.put(k13, m13 = new HashMap<>());
        }

        Map m14 = (Map) m13.get(k14);
        if (m14 == null) {
            m13.put(k14, m14 = new HashMap<>());
        }

        Map m15 = (Map) m14.get(k15);
        if (m15 == null) {
            m14.put(k15, m15 = new HashMap<>());
        }

        Map m16 = (Map) m15.get(k16);
        if (m16 == null) {
            m15.put(k16, m16 = new HashMap<>());
        }

        Map m17 = (Map) m16.get(k17);
        if (m17 == null) {
            m16.put(k17, m17 = new HashMap<>());
        }

        Map m18 = (Map) m17.get(k18);
        if (m18 == null) {
            m17.put(k18, m18 = new HashMap<>());
        }

        Map m19 = (Map) m18.get(k19);
        if (m19 == null) {
            m18.put(k19, m19 = new HashMap<>());
        }

        Map m20 = (Map) m19.get(k20);
        if (m20 == null) {
            m19.put(k20, m20 = new HashMap<>());
        }

        Map m21 = (Map) m20.get(k21);
        if (m21 == null) {
            m20.put(k21, m21 = new HashMap<>());
        }

        Map m22 = (Map) m21.get(k22);
        if (m22 == null) {
            m21.put(k22, m22 = new HashMap<>());
        }

        Map m23 = (Map) m22.get(k23);
        if (m23 == null) {
            m22.put(k23, m23 = new HashMap<>());
        }

        Map m24 = (Map) m23.get(k24);
        if (m24 == null) {
            m23.put(k24, m24 = new HashMap<>());
        }

        Map m25 = (Map) m24.get(k25);
        if (m25 == null) {
            m24.put(k25, m25 = new HashMap<>());
        }

        Map m26 = (Map) m25.get(k26);
        if (m26 == null) {
            m25.put(k26, m26 = new HashMap<>());
        }

        Map m27 = (Map) m26.get(k27);
        if (m27 == null) {
            m26.put(k27, m27 = new HashMap<>());
        }

        Map m28 = (Map) m27.get(k28);
        if (m28 == null) {
            m27.put(k28, m28 = new HashMap<>());
        }

        Map m29 = (Map) m28.get(k29);
        if (m29 == null) {
            m28.put(k29, m29 = new HashMap<>());
        }

        Map m30 = (Map) m29.get(k30);
        if (m30 == null) {
            m29.put(k30, m30 = new HashMap<>());
        }

        Map m31 = (Map) m30.get(k31);
        if (m31 == null) {
            m30.put(k31, m31 = new HashMap<>());
        }

        Map m32 = (Map) m31.get(k32);
        if (m32 == null) {
            m31.put(k32, m32 = new HashMap<>());
        }

        return (V) m32.put(k33, value);
    }

    public boolean containsKey(K1 k1, K2 k2, K3 k3, K4 k4, K5 k5, K6 k6, K7 k7, K8 k8, K9 k9, K10 k10, K11 k11, K12 k12, K13 k13, K14 k14, K15 k15, K16 k16, K17 k17, K18 k18, K19 k19, K20 k20, K21 k21, K22 k22, K23 k23, K24 k24, K25 k25, K26 k26, K27 k27, K28 k28, K29 k29, K30 k30, K31 k31, K32 k32, K33 k33) {
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

        Map m10 = (Map)m9.get(k10);
        if (m10 == null) {
            return false;
        }

        Map m11 = (Map)m10.get(k11);
        if (m11 == null) {
            return false;
        }

        Map m12 = (Map)m11.get(k12);
        if (m12 == null) {
            return false;
        }

        Map m13 = (Map)m12.get(k13);
        if (m13 == null) {
            return false;
        }

        Map m14 = (Map)m13.get(k14);
        if (m14 == null) {
            return false;
        }

        Map m15 = (Map)m14.get(k15);
        if (m15 == null) {
            return false;
        }

        Map m16 = (Map)m15.get(k16);
        if (m16 == null) {
            return false;
        }

        Map m17 = (Map)m16.get(k17);
        if (m17 == null) {
            return false;
        }

        Map m18 = (Map)m17.get(k18);
        if (m18 == null) {
            return false;
        }

        Map m19 = (Map)m18.get(k19);
        if (m19 == null) {
            return false;
        }

        Map m20 = (Map)m19.get(k20);
        if (m20 == null) {
            return false;
        }

        Map m21 = (Map)m20.get(k21);
        if (m21 == null) {
            return false;
        }

        Map m22 = (Map)m21.get(k22);
        if (m22 == null) {
            return false;
        }

        Map m23 = (Map)m22.get(k23);
        if (m23 == null) {
            return false;
        }

        Map m24 = (Map)m23.get(k24);
        if (m24 == null) {
            return false;
        }

        Map m25 = (Map)m24.get(k25);
        if (m25 == null) {
            return false;
        }

        Map m26 = (Map)m25.get(k26);
        if (m26 == null) {
            return false;
        }

        Map m27 = (Map)m26.get(k27);
        if (m27 == null) {
            return false;
        }

        Map m28 = (Map)m27.get(k28);
        if (m28 == null) {
            return false;
        }

        Map m29 = (Map)m28.get(k29);
        if (m29 == null) {
            return false;
        }

        Map m30 = (Map)m29.get(k30);
        if (m30 == null) {
            return false;
        }

        Map m31 = (Map)m30.get(k31);
        if (m31 == null) {
            return false;
        }

        Map m32 = (Map)m31.get(k32);
        if (m32 == null) {
            return false;
        }
                
        return m32.containsKey(k33);
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
            for (Object m2 : ((Map) m1).values()) {for (Object m3 : ((Map) m2).values()) {for (Object m4 : ((Map) m3).values()) {for (Object m5 : ((Map) m4).values()) {for (Object m6 : ((Map) m5).values()) {for (Object m7 : ((Map) m6).values()) {for (Object m8 : ((Map) m7).values()) {for (Object m9 : ((Map) m8).values()) {for (Object m10 : ((Map) m9).values()) {for (Object m11 : ((Map) m10).values()) {for (Object m12 : ((Map) m11).values()) {for (Object m13 : ((Map) m12).values()) {for (Object m14 : ((Map) m13).values()) {for (Object m15 : ((Map) m14).values()) {for (Object m16 : ((Map) m15).values()) {for (Object m17 : ((Map) m16).values()) {for (Object m18 : ((Map) m17).values()) {for (Object m19 : ((Map) m18).values()) {for (Object m20 : ((Map) m19).values()) {for (Object m21 : ((Map) m20).values()) {for (Object m22 : ((Map) m21).values()) {for (Object m23 : ((Map) m22).values()) {for (Object m24 : ((Map) m23).values()) {for (Object m25 : ((Map) m24).values()) {for (Object m26 : ((Map) m25).values()) {for (Object m27 : ((Map) m26).values()) {for (Object m28 : ((Map) m27).values()) {for (Object m29 : ((Map) m28).values()) {for (Object m30 : ((Map) m29).values()) {for (Object m31 : ((Map) m30).values()) {for (Object m32 : ((Map) m31).values()) {
            if (((Map) m32).containsValue(value)) return true;
            }}}}}}}}}}}}}}}}}}}}}}}}}}}}}}}
        }
        return false;
    }
}

