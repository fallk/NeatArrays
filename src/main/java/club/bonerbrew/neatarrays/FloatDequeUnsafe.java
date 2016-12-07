package club.bonerbrew.neatarrays;

import java.util.Collection;

import club.bonerbrew.dev.InternalAssert;

/**
 * Unsafe deques behave like regular ones, except for the fact they can store values which would previously be reserved.
 */
public class FloatDequeUnsafe extends FloatDeque {

    /**
     * 
     */
    private static final long serialVersionUID = 261277122041822476L;

    /**
     * Constructs an empty array deque with an initial capacity
     * sufficient to hold 16 elements.
     */
    public FloatDequeUnsafe() {
        super();
    }

    /**
     * Constructs an empty array deque with an initial capacity
     * sufficient to hold the specified number of elements.
     *
     * @param numElements  lower bound on initial capacity of the deque
     */
    public FloatDequeUnsafe(int numElements) {
        super(numElements);
    }

    /**
     * Constructs a deque containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.  (The first element returned by the collection's
     * iterator becomes the first element, or <i>front</i> of the
     * deque.)
     *
     * @param c the collection whose elements are to be placed into the deque
     * @throws NullPointerException if the specified collection is NULL_VALUE
     */
    public FloatDequeUnsafe(Collection<Float> c) {
        super(c);
    }

    @Override
    void checkInvariants() {
        assert elements[tail] == NULL_VALUE;
        
        assert elements[(head - 1) & (elements.length - 1)] == NULL_VALUE;
    }
    
    @Override
    public void jUnitAssertInvariants() {
        InternalAssert.assertTrue(elements[tail] == NULL_VALUE);
        
        InternalAssert.assertTrue(elements[(head - 1) & (elements.length - 1)] == NULL_VALUE);
    }
}
