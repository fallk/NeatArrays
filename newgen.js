'use strict';
const fs = require('fs');

let generics = 'K1, K2, K3, K4, K5, K6, V';
let argumentes = 'K1 k1, K2 k2, K3 k3, K4 k4, K5 k5, K6 k6'; // intentional misspelling

for (let i = 7; i <= 253; i++) {
  process(i);
}

function process(nGenerics) {
let agenerics = [], aargumentes = [];
for (let i = 1; i <= nGenerics; i++) {
  agenerics.push('K' + i);
  aargumentes.push('K' + i + ' k' + i);
}
generics = agenerics.join(', ') + ', V';
argumentes = aargumentes.join(', ');

function getter(i) {
  return `
        Map m${i} = (Map) m${i-1}.get(k${i});
        if (m${i} == null) {
            return null;
        }`;
}
function setter(i) {
  return `
        Map m${i} = (Map) m${i-1}.get(k${i});
        if (m${i} == null) {
            m${i-1}.put(k${i}, m${i} = new HashMap<>());
        }`;
}
function ckey(i) {
  return `
        Map m${i} = (Map)m${i-1}.get(k${i});
        if (m${i} == null) {
            return false;
        }`;
        
}

function pair(start, end, func) {
  let a = [];
  for (let i = start; i < end; i++) { // i < end here is right!
    a.push(func(i));
  }
  return a.join('\n');
}

function containsAnyValue() {
  let output = [];
  for (let i = 2; i < nGenerics; i++) {
    output.push(`for (Object m${i} : ((Map) m${i-1}).values()) {`);
  }
  output.push(`\n            if (((Map) m${nGenerics-1}).containsValue(value)) return true;\n            `);
  for (let i = 2; i < nGenerics; i++) {
    output.push(`}`);
  }
  return output.join('');
}

const it = `
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
public class HashMap${nGenerics}D<${ generics }> extends HashMap {

    /**
     * 
     */
    private static final long serialVersionUID = 5184948037872736489L;

    /**
     * Creates an empty cube
     */
    public HashMap${nGenerics}D() {
        
    }

    /**
     * Constructs an empty <tt>Cube</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param  initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public HashMap${nGenerics}D(int initialCapacity) {
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
    public HashMap${nGenerics}D(Map m) {
        super(m);
    }

    /**
     * Creates a copy of the cube denoted by {@code cube}
     * 
     * @param cube the cube to copy
     */
    public HashMap${nGenerics}D(HashMap${nGenerics}D<${ generics }> cube) {
        super(cube);
    }

    public HashMap${nGenerics}D(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Gets an object at the specified row, column and page.
     * @param k1 the row
     * @param k2 the column
     * @param k3 the page
     * @return the object at the specified row, column and page, or null if there is none.
     */
    public V get(${argumentes}) {
        Map m1 = (Map) super.get(k1);
        if (m1 == null) {
            return null;
        }

        ${pair(2,nGenerics,getter)}
        
        return (V) m${nGenerics-1}.get(k${nGenerics});
    }

    /**
     * Puts an object at the specified row, column and page.
     * @param k1 the row
     * @param k2 the column
     * @param k3 the page
     * @return the previous object at the specified row, column and page, or null if there was none.
     */
    public V put(${argumentes}, V value) {
        Map m1 = (Map) super.get(k1);
        if (m1 == null) {
            super.put(k1, m1 = new HashMap<>());
        }

        ${pair(2,nGenerics,setter)}

        return (V) m${nGenerics-1}.put(k${nGenerics}, value);
    }

    public boolean containsKey(${argumentes}) {
        Map m1 = (Map)super.get(k1);
        if (m1 == null) {
            return false;
        }

        ${pair(2,nGenerics,ckey)}
                
        return m${nGenerics-1}.containsKey(k${nGenerics});
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
            ${containsAnyValue()}
        }
        return false;
    }
}

`;
fs.writeFileSync(`src/main/java/club/bonerbrew/nutslists/HashMap${nGenerics}D.java`, it);
}