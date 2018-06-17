package fallk.dev;

import static java.lang.String.valueOf;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A set of assertion methods useful for writing tests. Only failed assertions are recorded. These methods can be used directly: <code>Assert.assertEquals(...)</code>, however, they read better if they are referenced through static import:
 *
 * <pre>
 * import static org.junit.Assert.*;
 *    ...
 *    assertEquals(...);
 * </pre>
 *
 * @see AssertionError
 * @since 4.0
 */
public final class InternalAssert {

    /**
     * Thrown when an {@link org.junit.Assert#assertEquals(Object, Object) assertEquals(String, String)} fails. Create and throw a <code>ComparisonFailure</code> manually if you want to show users the difference between two complex strings.
     * <p/>
     * Inspired by a patch from Alex Chaffee (alex@purpletech.com)
     *
     * @since 4.0
     */
    static private class InternalComparisonFailure extends AssertionError {
        /**
         * The maximum length for expected and actual strings. If it is exceeded, the strings should be shortened.
         *
         * @see ComparisonCompactor
         */
        private static final int MAX_CONTEXT_LENGTH = 20;
        private static final long serialVersionUID = 1L;

        /*
         * We have to use the f prefix until the next major release to ensure
         * serialization compatibility. 
         * See https://github.com/junit-team/junit/issues/976
         */
        private String fExpected;
        private String fActual;

        /**
         * Constructs a comparison failure.
         *
         * @param message the identifying message or null
         * @param expected the expected string value
         * @param actual the actual string value
         */
        public InternalComparisonFailure(String message, String expected, String actual) {
            super(message);
            this.fExpected = expected;
            this.fActual = actual;
        }

        /**
         * Returns "..." in place of common prefix and "..." in place of common suffix between expected and actual.
         *
         * @see Throwable#getMessage()
         */
        @Override
        public String getMessage() {
            return new ComparisonCompactor(MAX_CONTEXT_LENGTH, fExpected, fActual).compact(super.getMessage());
        }

        private static class ComparisonCompactor {
            private static final String ELLIPSIS = "...";
            private static final String DIFF_END = "]";
            private static final String DIFF_START = "[";

            /**
             * The maximum length for <code>expected</code> and <code>actual</code> strings to show. When <code>contextLength</code> is exceeded, the Strings are shortened.
             */
            private final int contextLength;
            private final String expected;
            private final String actual;

            /**
             * @param contextLength the maximum length of context surrounding the difference between the compared strings. When context length is exceeded, the prefixes and suffixes are compacted.
             * @param expected the expected string value
             * @param actual the actual string value
             */
            public ComparisonCompactor(int contextLength, String expected, String actual) {
                this.contextLength = contextLength;
                this.expected = expected;
                this.actual = actual;
            }

            public String compact(String message) {
                if (expected == null || actual == null || expected.equals(actual)) {
                    return InternalAssert.format(message, expected, actual);
                } else {
                    DiffExtractor extractor = new DiffExtractor();
                    String compactedPrefix = extractor.compactPrefix();
                    String compactedSuffix = extractor.compactSuffix();
                    return InternalAssert.format(message, compactedPrefix + extractor.expectedDiff() + compactedSuffix, compactedPrefix + extractor.actualDiff() + compactedSuffix);
                }
            }

            private String sharedPrefix() {
                int end = Math.min(expected.length(), actual.length());
                for (int i = 0; i < end; i++) {
                    if (expected.charAt(i) != actual.charAt(i)) {
                        return expected.substring(0, i);
                    }
                }
                return expected.substring(0, end);
            }

            private String sharedSuffix(String prefix) {
                int suffixLength = 0;
                int maxSuffixLength = Math.min(expected.length() - prefix.length(), actual.length() - prefix.length()) - 1;
                for (; suffixLength <= maxSuffixLength; suffixLength++) {
                    if (expected.charAt(expected.length() - 1 - suffixLength) != actual.charAt(actual.length() - 1 - suffixLength)) {
                        break;
                    }
                }
                return expected.substring(expected.length() - suffixLength);
            }

            private class DiffExtractor {
                private final String sharedPrefix;
                private final String sharedSuffix;

                /**
                 * Can not be instantiated outside {@link org.junit.ComparisonFailure.ComparisonCompactor}.
                 */
                private DiffExtractor() {
                    sharedPrefix = sharedPrefix();
                    sharedSuffix = sharedSuffix(sharedPrefix);
                }

                public String expectedDiff() {
                    return extractDiff(expected);
                }

                public String actualDiff() {
                    return extractDiff(actual);
                }

                public String compactPrefix() {
                    if (sharedPrefix.length() <= contextLength) {
                        return sharedPrefix;
                    }
                    return ELLIPSIS + sharedPrefix.substring(sharedPrefix.length() - contextLength);
                }

                public String compactSuffix() {
                    if (sharedSuffix.length() <= contextLength) {
                        return sharedSuffix;
                    }
                    return sharedSuffix.substring(0, contextLength) + ELLIPSIS;
                }

                private String extractDiff(String source) {
                    return DIFF_START + source.substring(sharedPrefix.length(), source.length() - sharedSuffix.length()) + DIFF_END;
                }
            }
        }
    }

    /**
     * Thrown when two array elements differ
     *
     * @see Assert#assertArrayEquals(String, Object[], Object[])
     */
    static private class ArrayComparisonFailure extends AssertionError {

        private static final long serialVersionUID = 1L;

        /*
         * We have to use the f prefix until the next major release to ensure
         * serialization compatibility. 
         * See https://github.com/junit-team/junit/issues/976
         */
        private final List<Integer> fIndices = new ArrayList<Integer>();
        private final String fMessage;

        /**
         * Construct a new <code>ArrayComparisonFailure</code> with an error text and the array's dimension that was not equal
         *
         * @param cause the exception that caused the array's content to fail the assertion test
         * @param index the array position of the objects that are not equal.
         * @see Assert#assertArrayEquals(String, Object[], Object[])
         */
        public ArrayComparisonFailure(String message, AssertionError cause, int index) {
            this.fMessage = message;
            initCause(cause);
            addDimension(index);
        }

        public void addDimension(int index) {
            fIndices.add(0, index);
        }

        @Override
        public String getMessage() {
            StringBuilder sb = new StringBuilder();
            if (fMessage != null) {
                sb.append(fMessage);
            }
            sb.append("arrays first differed at element ");
            for (int each : fIndices) {
                sb.append("[");
                sb.append(each);
                sb.append("]");
            }
            sb.append("; ");
            sb.append(getCause().getMessage());
            return sb.toString();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return getMessage();
        }
    }

    /**
     * Defines criteria for finding two items "equal enough". Concrete subclasses may demand exact equality, or, for example, equality within a given delta.
     */
    static abstract private class ComparisonCriteria {
        /**
         * Asserts that two arrays are equal, according to the criteria defined by the concrete subclass. If they are not, an {@link AssertionError} is thrown with the given message. If <code>expecteds</code> and <code>actuals</code> are <code>null</code>, they are considered equal.
         *
         * @param message the identifying message for the {@link AssertionError} ( <code>null</code> okay)
         * @param expecteds Object array or array of arrays (multi-dimensional array) with expected values.
         * @param actuals Object array or array of arrays (multi-dimensional array) with actual values
         */
        public void arrayEquals(String message, Object expecteds, Object actuals) throws ArrayComparisonFailure {
            if (expecteds == actuals || Arrays.deepEquals(new Object[] {
                    expecteds
            }, new Object[] {
                    actuals
            })) {
                // The reflection-based loop below is potentially very slow, especially for primitive
                // arrays. The deepEquals check allows us to circumvent it in the usual case where
                // the arrays are exactly equal.
                return;
            }
            String header = message == null ? "" : message + ": ";

            int expectedsLength = assertArraysAreSameLength(expecteds, actuals, header);

            for (int i = 0; i < expectedsLength; i++) {
                Object expected = Array.get(expecteds, i);
                Object actual = Array.get(actuals, i);

                if (isArray(expected) && isArray(actual)) {
                    try {
                        arrayEquals(message, expected, actual);
                    } catch (ArrayComparisonFailure e) {
                        e.addDimension(i);
                        throw e;
                    }
                } else {
                    try {
                        assertElementsEqual(expected, actual);
                    } catch (AssertionError e) {
                        throw new ArrayComparisonFailure(header, e, i);
                    }
                }
            }
        }

        private boolean isArray(Object expected) {
            return expected != null && expected.getClass().isArray();
        }

        private int assertArraysAreSameLength(Object expecteds, Object actuals, String header) {
            if (expecteds == null) {
                InternalAssert.fail(header + "expected array was null");
            }
            if (actuals == null) {
                InternalAssert.fail(header + "actual array was null");
            }
            int actualsLength = Array.getLength(actuals);
            int expectedsLength = Array.getLength(expecteds);
            if (actualsLength != expectedsLength) {
                InternalAssert.fail(header + "array lengths differed, expected.length=" + expectedsLength + " actual.length=" + actualsLength);
            }
            return expectedsLength;
        }

        protected abstract void assertElementsEqual(Object expected, Object actual);
    }

    private static class ExactComparisonCriteria extends ComparisonCriteria {
        @Override
        protected void assertElementsEqual(Object expected, Object actual) {
            InternalAssert.assertEquals(expected, actual);
        }
    }

    private static class InexactComparisonCriteria extends ComparisonCriteria {
        public Object fDelta;

        public InexactComparisonCriteria(double delta) {
            fDelta = delta;
        }

        public InexactComparisonCriteria(float delta) {
            fDelta = delta;
        }

        @Override
        protected void assertElementsEqual(Object expected, Object actual) {
            if (expected instanceof Double) {
                InternalAssert.assertEquals((Double) expected, (Double) actual, (Double) fDelta);
            } else {
                InternalAssert.assertEquals((Float) expected, (Float) actual, (Float) fDelta);
            }
        }
    }

    /*  Copyright (c) 2000-2006 hamcrest.org
     */
    /**
     * A description of a Matcher. A Matcher will describe itself to a description which can later be used for reporting.
     *
     * @see Matcher#describeTo(Description)
     */
    private interface Description {
        /**
         * Appends some plain text to the description.
         */
        Description appendText(String text);

        /**
         * Appends the description of a {@link SelfDescribing} value to this description.
         */
        Description appendDescriptionOf(SelfDescribing value);

        /**
         * Appends an arbitary value to the description.
         */
        Description appendValue(Object value);

        /**
         * Appends a list of values to the description.
         */
        <T> Description appendValueList(String start, String separator, String end, @SuppressWarnings("unchecked") T... values);

        /**
         * Appends a list of values to the description.
         */
        <T> Description appendValueList(String start, String separator, String end, Iterable<T> values);

        /**
         * Appends a list of {@link org.hamcrest.SelfDescribing} objects to the description.
         */
        Description appendList(String start, String separator, String end, Iterable<? extends SelfDescribing> values);
    }

    static class ArrayIterator implements Iterator<Object> {
        private final Object array;
        private int currentIndex = 0;

        public ArrayIterator(Object array) {
            if (!array.getClass().isArray()) {
                throw new IllegalArgumentException("not an array");
            }
            this.array = array;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < Array.getLength(array);
        }

        @Override
        public Object next() {
            return Array.get(array, currentIndex++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("cannot remove items from an array");
        }
    }

    static class SelfDescribingValueIterator<T> implements Iterator<SelfDescribing> {
        private Iterator<T> values;

        public SelfDescribingValueIterator(Iterator<T> values) {
            this.values = values;
        }

        @Override
        public boolean hasNext() {
            return values.hasNext();
        }

        @Override
        public SelfDescribing next() {
            return new SelfDescribingValue<T>(values.next());
        }

        @Override
        public void remove() {
            values.remove();
        }
    }

    static class SelfDescribingValue<T> implements SelfDescribing {
        private T value;

        public SelfDescribingValue(T value) {
            this.value = value;
        }

        @Override
        public void describeTo(Description description) {
            description.appendValue(value);
        }
    }

    /**
     * A {@link Description} that is stored as a string.
     */
    static abstract class BaseDescription implements Description {

        @Override
        public Description appendText(String text) {
            append(text);
            return this;
        }

        @Override
        public Description appendDescriptionOf(SelfDescribing value) {
            value.describeTo(this);
            return this;
        }

        @Override
        public Description appendValue(Object value) {
            if (value == null) {
                append("null");
            } else if (value instanceof String) {
                toJavaSyntax((String) value);
            } else if (value instanceof Character) {
                append('"');
                toJavaSyntax((Character) value);
                append('"');
            } else if (value instanceof Short) {
                append('<');
                append(descriptionOf(value));
                append("s>");
            } else if (value instanceof Long) {
                append('<');
                append(descriptionOf(value));
                append("L>");
            } else if (value instanceof Float) {
                append('<');
                append(descriptionOf(value));
                append("F>");
            } else if (value.getClass().isArray()) {
                appendValueList("[", ", ", "]", new ArrayIterator(value));
            } else {
                append('<');
                append(descriptionOf(value));
                append('>');
            }
            return this;
        }

        private String descriptionOf(Object value) {
            try {
                return valueOf(value);
            } catch (Exception e) {
                return value.getClass().getName() + "@" + Integer.toHexString(value.hashCode());
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> Description appendValueList(String start, String separator, String end, T... values) {
            return appendValueList(start, separator, end, Arrays.asList(values));
        }

        @Override
        public <T> Description appendValueList(String start, String separator, String end, Iterable<T> values) {
            return appendValueList(start, separator, end, values.iterator());
        }

        private <T> Description appendValueList(String start, String separator, String end, Iterator<T> values) {
            return appendList(start, separator, end, new SelfDescribingValueIterator<T>(values));
        }

        @Override
        public Description appendList(String start, String separator, String end, Iterable<? extends SelfDescribing> values) {
            return appendList(start, separator, end, values.iterator());
        }

        private Description appendList(String start, String separator, String end, Iterator<? extends SelfDescribing> i) {
            boolean separate = false;

            append(start);
            while (i.hasNext()) {
                if (separate)
                    append(separator);
                appendDescriptionOf(i.next());
                separate = true;
            }
            append(end);

            return this;
        }

        /**
         * Append the String <var>str</var> to the description. The default implementation passes every character to {@link #append(char)}. Override in subclasses to provide an efficient implementation.
         */
        protected void append(String str) {
            for (int i = 0; i < str.length(); i++) {
                append(str.charAt(i));
            }
        }

        /**
         * Append the char <var>c</var> to the description.
         */
        protected abstract void append(char c);

        private void toJavaSyntax(String unformatted) {
            append('"');
            for (int i = 0; i < unformatted.length(); i++) {
                toJavaSyntax(unformatted.charAt(i));
            }
            append('"');
        }

        private void toJavaSyntax(char ch) {
            switch (ch) {
                case '"':
                    append("\\\"");
                    break;
                case '\n':
                    append("\\n");
                    break;
                case '\r':
                    append("\\r");
                    break;
                case '\t':
                    append("\\t");
                    break;
                default:
                    append(ch);
            }
        }
    }

    /**
     * A {@link Description} that is stored as a string.
     */
    static class StringDescription extends BaseDescription {
        private final Appendable out;

        public StringDescription() {
            this(new StringBuilder());
        }

        public StringDescription(Appendable out) {
            this.out = out;
        }

        /**
         * Return the description of a {@link SelfDescribing} object as a String.
         * 
         * @param selfDescribing The object to be described.
         * @return The description of the object.
         */
        public static String toString(SelfDescribing selfDescribing) {
            return new StringDescription().appendDescriptionOf(selfDescribing).toString();
        }

        /**
         * Alias for {@link #toString(SelfDescribing)}.
         */
        public static String asString(SelfDescribing selfDescribing) {
            return toString(selfDescribing);
        }

        @Override
        protected void append(String str) {
            try {
                out.append(str);
            } catch (IOException e) {
                throw new RuntimeException("Could not write description", e);
            }
        }

        @Override
        protected void append(char c) {
            try {
                out.append(c);
            } catch (IOException e) {
                throw new RuntimeException("Could not write description", e);
            }
        }

        /**
         * Returns the description as a string.
         */
        @Override
        public String toString() {
            return out.toString();
        }
    }

    static class MatcherAssert {
        public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
            assertThat("", actual, matcher);
        }

        public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
            if (!matcher.matches(actual)) {
                Description description = new StringDescription();
                description.appendText(reason).appendText("\nExpected: ").appendDescriptionOf(matcher).appendText("\n     but: ");
                matcher.describeMismatch(actual, description);

                throw new AssertionError(description.toString());
            }
        }

        public static void assertThat(String reason, boolean assertion) {
            if (!assertion) {
                throw new AssertionError(reason);
            }
        }
    }

    /**
     * The ability of an object to describe itself.
     */
    public interface SelfDescribing {
        /**
         * Generates a description of the object. The description may be part of a a description of a larger object of which this is just a component, so it should be worded appropriately.
         * 
         * @param description The description to be built or appended to.
         */
        void describeTo(Description description);
    }

    /**
     * A matcher over acceptable values. A matcher is able to describe itself to give feedback when it fails.
     * <p/>
     * Matcher implementations should <b>NOT directly implement this interface</b>. Instead, <b>extend</b> the {@link BaseMatcher} abstract class, which will ensure that the Matcher API can grow to support new features and remain compatible with all Matcher implementations.
     * <p/>
     * For easy access to common Matcher implementations, use the static factory methods in {@link CoreMatchers}.
     * <p/>
     * N.B. Well designed matchers should be immutable.
     * 
     * @see CoreMatchers
     * @see BaseMatcher
     */
    public interface Matcher<T> extends SelfDescribing {

        /**
         * Evaluates the matcher for argument <var>item</var>.
         * <p/>
         * This method matches against Object, instead of the generic type T. This is because the caller of the Matcher does not know at runtime what the type is (because of type erasure with Java generics). It is down to the implementations to check the correct type.
         *
         * @param item the object against which the matcher is evaluated.
         * @return <code>true</code> if <var>item</var> matches, otherwise <code>false</code>.
         * @see BaseMatcher
         */
        boolean matches(Object item);

        /**
         * Generate a description of why the matcher has not accepted the item. The description will be part of a larger description of why a matching failed, so it should be concise. This method assumes that <code>matches(item)</code> is false, but will not check this.
         *
         * @param item The item that the Matcher has rejected.
         * @param mismatchDescription The description to be built or appended to.
         */
        void describeMismatch(Object item, Description mismatchDescription);

        /**
         * This method simply acts a friendly reminder not to implement Matcher directly and instead extend BaseMatcher. It's easy to ignore JavaDoc, but a bit harder to ignore compile errors .
         *
         * @see Matcher for reasons why.
         * @see BaseMatcher
         * @deprecated to make
         */
        @Deprecated
        void _dont_implement_Matcher___instead_extend_BaseMatcher_();
    }

    /*
     * End of hamcrest classes
     */

    /**
     * Protect constructor since it is a static only class
     */
    private InternalAssert() {
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an {@link AssertionError} with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param condition condition to be checked
     */
    static public void assertTrue(String message, boolean condition) {
        if (!condition) {
            fail(message);
        }
    }

    /**
     * Asserts that a condition is true. If it isn't it throws an {@link AssertionError} without a message.
     *
     * @param condition condition to be checked
     */
    static public void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an {@link AssertionError} with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param condition condition to be checked
     */
    static public void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    /**
     * Asserts that a condition is false. If it isn't it throws an {@link AssertionError} without a message.
     *
     * @param condition condition to be checked
     */
    static public void assertFalse(boolean condition) {
        assertFalse(null, condition);
    }

    /**
     * Fails a test with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @see AssertionError
     */
    static public void fail(String message) {
        if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError(message);
    }

    /**
     * Fails a test with no message.
     */
    static public void fail() {
        fail(null);
    }

    /**
     * Asserts that two objects are equal. If they are not, an {@link AssertionError} is thrown with the given message. If <code>expected</code> and <code>actual</code> are <code>null</code>, they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expected expected value
     * @param actual actual value
     */
    static public void assertEquals(String message, Object expected, Object actual) {
        if (equalsRegardingNull(expected, actual)) {
            return;
        } else if (expected instanceof String && actual instanceof String) {
            String cleanMessage = message == null ? "" : message;
            throw new InternalComparisonFailure(cleanMessage, (String) expected, (String) actual);
        } else {
            failNotEquals(message, expected, actual);
        }
    }

    private static boolean equalsRegardingNull(Object expected, Object actual) {
        if (expected == null) {
            return actual == null;
        }

        return isEquals(expected, actual);
    }

    private static boolean isEquals(Object expected, Object actual) {
        return expected.equals(actual);
    }

    /**
     * Asserts that two objects are equal. If they are not, an {@link AssertionError} without a message is thrown. If <code>expected</code> and <code>actual</code> are <code>null</code>, they are considered equal.
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     */
    static public void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an {@link AssertionError} is thrown with the given message. If <code>unexpected</code> and <code>actual</code> are <code>null</code>, they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param unexpected unexpected value to check
     * @param actual the value to check against <code>unexpected</code>
     */
    static public void assertNotEquals(String message, Object unexpected, Object actual) {
        if (equalsRegardingNull(unexpected, actual)) {
            failEquals(message, actual);
        }
    }

    /**
     * Asserts that two objects are <b>not</b> equals. If they are, an {@link AssertionError} without a message is thrown. If <code>unexpected</code> and <code>actual</code> are <code>null</code>, they are considered equal.
     *
     * @param unexpected unexpected value to check
     * @param actual the value to check against <code>unexpected</code>
     */
    static public void assertNotEquals(Object unexpected, Object actual) {
        assertNotEquals(null, unexpected, actual);
    }

    private static void failEquals(String message, Object actual) {
        String formatted = "Values should be different. ";
        if (message != null) {
            formatted = message + ". ";
        }

        formatted += "Actual: " + actual;
        fail(formatted);
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param unexpected unexpected value to check
     * @param actual the value to check against <code>unexpected</code>
     */
    static public void assertNotEquals(String message, long unexpected, long actual) {
        if (unexpected == actual) {
            failEquals(message, Long.valueOf(actual));
        }
    }

    /**
     * Asserts that two longs are <b>not</b> equals. If they are, an {@link AssertionError} without a message is thrown.
     *
     * @param unexpected unexpected value to check
     * @param actual the value to check against <code>unexpected</code>
     */
    static public void assertNotEquals(long unexpected, long actual) {
        assertNotEquals(null, unexpected, actual);
    }

    /**
     * Asserts that two doubles are <b>not</b> equal to within a positive delta. If they are, an {@link AssertionError} is thrown with the given message. If the unexpected value is infinity then the delta value is ignored. NaNs are considered equal: <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param unexpected unexpected value
     * @param actual the value to check against <code>unexpected</code>
     * @param delta the maximum delta between <code>unexpected</code> and <code>actual</code> for which both numbers are still considered equal.
     */
    static public void assertNotEquals(String message, double unexpected, double actual, double delta) {
        if (!doubleIsDifferent(unexpected, actual, delta)) {
            failEquals(message, Double.valueOf(actual));
        }
    }

    /**
     * Asserts that two doubles are <b>not</b> equal to within a positive delta. If they are, an {@link AssertionError} is thrown. If the unexpected value is infinity then the delta value is ignored.NaNs are considered equal: <code>assertNotEquals(Double.NaN, Double.NaN, *)</code> fails
     *
     * @param unexpected unexpected value
     * @param actual the value to check against <code>unexpected</code>
     * @param delta the maximum delta between <code>unexpected</code> and <code>actual</code> for which both numbers are still considered equal.
     */
    static public void assertNotEquals(double unexpected, double actual, double delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    /**
     * Asserts that two floats are <b>not</b> equal to within a positive delta. If they are, an {@link AssertionError} is thrown. If the unexpected value is infinity then the delta value is ignored.NaNs are considered equal: <code>assertNotEquals(Float.NaN, Float.NaN, *)</code> fails
     *
     * @param unexpected unexpected value
     * @param actual the value to check against <code>unexpected</code>
     * @param delta the maximum delta between <code>unexpected</code> and <code>actual</code> for which both numbers are still considered equal.
     */
    static public void assertNotEquals(float unexpected, float actual, float delta) {
        assertNotEquals(null, unexpected, actual, delta);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message. If <code>expecteds</code> and <code>actuals</code> are <code>null</code>, they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds Object array or array of arrays (multi-dimensional array) with expected values.
     * @param actuals Object array or array of arrays (multi-dimensional array) with actual values
     */
    public static void assertArrayEquals(String message, Object[] expecteds, Object[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an {@link AssertionError} is thrown. If <code>expected</code> and <code>actual</code> are <code>null</code>, they are considered equal.
     *
     * @param expecteds Object array or array of arrays (multi-dimensional array) with expected values
     * @param actuals Object array or array of arrays (multi-dimensional array) with actual values
     */
    public static void assertArrayEquals(Object[] expecteds, Object[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two boolean arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message. If <code>expecteds</code> and <code>actuals</code> are <code>null</code>, they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds boolean array with expected values.
     * @param actuals boolean array with expected values.
     */
    public static void assertArrayEquals(String message, boolean[] expecteds, boolean[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two boolean arrays are equal. If they are not, an {@link AssertionError} is thrown. If <code>expected</code> and <code>actual</code> are <code>null</code>, they are considered equal.
     *
     * @param expecteds boolean array with expected values.
     * @param actuals boolean array with expected values.
     */
    public static void assertArrayEquals(boolean[] expecteds, boolean[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two byte arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds byte array with expected values.
     * @param actuals byte array with actual values
     */
    public static void assertArrayEquals(String message, byte[] expecteds, byte[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two byte arrays are equal. If they are not, an {@link AssertionError} is thrown.
     *
     * @param expecteds byte array with expected values.
     * @param actuals byte array with actual values
     */
    public static void assertArrayEquals(byte[] expecteds, byte[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two char arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds char array with expected values.
     * @param actuals char array with actual values
     */
    public static void assertArrayEquals(String message, char[] expecteds, char[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two char arrays are equal. If they are not, an {@link AssertionError} is thrown.
     *
     * @param expecteds char array with expected values.
     * @param actuals char array with actual values
     */
    public static void assertArrayEquals(char[] expecteds, char[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two short arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds short array with expected values.
     * @param actuals short array with actual values
     */
    public static void assertArrayEquals(String message, short[] expecteds, short[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two short arrays are equal. If they are not, an {@link AssertionError} is thrown.
     *
     * @param expecteds short array with expected values.
     * @param actuals short array with actual values
     */
    public static void assertArrayEquals(short[] expecteds, short[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two int arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds int array with expected values.
     * @param actuals int array with actual values
     */
    public static void assertArrayEquals(String message, int[] expecteds, int[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two int arrays are equal. If they are not, an {@link AssertionError} is thrown.
     *
     * @param expecteds int array with expected values.
     * @param actuals int array with actual values
     */
    public static void assertArrayEquals(int[] expecteds, int[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two long arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds long array with expected values.
     * @param actuals long array with actual values
     */
    public static void assertArrayEquals(String message, long[] expecteds, long[] actuals) throws ArrayComparisonFailure {
        internalArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two long arrays are equal. If they are not, an {@link AssertionError} is thrown.
     *
     * @param expecteds long array with expected values.
     * @param actuals long array with actual values
     */
    public static void assertArrayEquals(long[] expecteds, long[] actuals) {
        assertArrayEquals(null, expecteds, actuals);
    }

    /**
     * Asserts that two double arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds double array with expected values.
     * @param actuals double array with actual values
     * @param delta the maximum delta between <code>expecteds[i]</code> and <code>actuals[i]</code> for which both numbers are still considered equal.
     */
    public static void assertArrayEquals(String message, double[] expecteds, double[] actuals, double delta) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(delta).arrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two double arrays are equal. If they are not, an {@link AssertionError} is thrown.
     *
     * @param expecteds double array with expected values.
     * @param actuals double array with actual values
     * @param delta the maximum delta between <code>expecteds[i]</code> and <code>actuals[i]</code> for which both numbers are still considered equal.
     */
    public static void assertArrayEquals(double[] expecteds, double[] actuals, double delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }

    /**
     * Asserts that two float arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds float array with expected values.
     * @param actuals float array with actual values
     * @param delta the maximum delta between <code>expecteds[i]</code> and <code>actuals[i]</code> for which both numbers are still considered equal.
     */
    public static void assertArrayEquals(String message, float[] expecteds, float[] actuals, float delta) throws ArrayComparisonFailure {
        new InexactComparisonCriteria(delta).arrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two float arrays are equal. If they are not, an {@link AssertionError} is thrown.
     *
     * @param expecteds float array with expected values.
     * @param actuals float array with actual values
     * @param delta the maximum delta between <code>expecteds[i]</code> and <code>actuals[i]</code> for which both numbers are still considered equal.
     */
    public static void assertArrayEquals(float[] expecteds, float[] actuals, float delta) {
        assertArrayEquals(null, expecteds, actuals, delta);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message. If <code>expecteds</code> and <code>actuals</code> are <code>null</code>, they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds Object array or array of arrays (multi-dimensional array) with expected values.
     * @param actuals Object array or array of arrays (multi-dimensional array) with actual values
     */
    private static void internalArrayEquals(String message, Object expecteds, Object actuals) throws ArrayComparisonFailure {
        new ExactComparisonCriteria().arrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two doubles are equal to within a positive delta. If they are not, an {@link AssertionError} is thrown with the given message. If the expected value is infinity then the delta value is ignored. NaNs are considered equal: <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and <code>actual</code> for which both numbers are still considered equal.
     */
    static public void assertEquals(String message, double expected, double actual, double delta) {
        if (doubleIsDifferent(expected, actual, delta)) {
            failNotEquals(message, Double.valueOf(expected), Double.valueOf(actual));
        }
    }

    /**
     * Asserts that two floats are equal to within a positive delta. If they are not, an {@link AssertionError} is thrown with the given message. If the expected value is infinity then the delta value is ignored. NaNs are considered equal: <code>assertEquals(Float.NaN, Float.NaN, *)</code> passes
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and <code>actual</code> for which both numbers are still considered equal.
     */
    static public void assertEquals(String message, float expected, float actual, float delta) {
        if (floatIsDifferent(expected, actual, delta)) {
            failNotEquals(message, Float.valueOf(expected), Float.valueOf(actual));
        }
    }

    /**
     * Asserts that two floats are <b>not</b> equal to within a positive delta. If they are, an {@link AssertionError} is thrown with the given message. If the unexpected value is infinity then the delta value is ignored. NaNs are considered equal: <code>assertNotEquals(Float.NaN, Float.NaN, *)</code> fails
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param unexpected unexpected value
     * @param actual the value to check against <code>unexpected</code>
     * @param delta the maximum delta between <code>unexpected</code> and <code>actual</code> for which both numbers are still considered equal.
     */
    static public void assertNotEquals(String message, float unexpected, float actual, float delta) {
        if (!floatIsDifferent(unexpected, actual, delta)) {
            failEquals(message, Float.valueOf(actual));
        }
    }

    static private boolean doubleIsDifferent(double d1, double d2, double delta) {
        if (Double.compare(d1, d2) == 0) {
            return false;
        }
        if ((Math.abs(d1 - d2) <= delta)) {
            return false;
        }

        return true;
    }

    static private boolean floatIsDifferent(float f1, float f2, float delta) {
        if (Float.compare(f1, f2) == 0) {
            return false;
        }
        if ((Math.abs(f1 - f2) <= delta)) {
            return false;
        }

        return true;
    }

    /**
     * Asserts that two longs are equal. If they are not, an {@link AssertionError} is thrown.
     *
     * @param expected expected long value.
     * @param actual actual long value
     */
    static public void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * Asserts that two longs are equal. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expected long expected value.
     * @param actual long actual value
     */
    static public void assertEquals(String message, long expected, long actual) {
        if (expected != actual) {
            failNotEquals(message, Long.valueOf(expected), Long.valueOf(actual));
        }
    }

    /**
     * @deprecated Use <code>assertEquals(double expected, double actual, double delta)</code> instead
     */
    @Deprecated
    static public void assertEquals(double expected, double actual) {
        assertEquals(null, expected, actual);
    }

    /**
     * @deprecated Use <code>assertEquals(String message, double expected, double actual, double delta)</code> instead
     */
    @SuppressWarnings("unused")
    @Deprecated
    static public void assertEquals(String message, double expected, double actual) {
        fail("Use assertEquals(expected, actual, delta) to compare floating-point numbers");
    }

    /**
     * Asserts that two doubles are equal to within a positive delta. If they are not, an {@link AssertionError} is thrown. If the expected value is infinity then the delta value is ignored.NaNs are considered equal: <code>assertEquals(Double.NaN, Double.NaN, *)</code> passes
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and <code>actual</code> for which both numbers are still considered equal.
     */
    static public void assertEquals(double expected, double actual, double delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that two floats are equal to within a positive delta. If they are not, an {@link AssertionError} is thrown. If the expected value is infinity then the delta value is ignored. NaNs are considered equal: <code>assertEquals(Float.NaN, Float.NaN, *)</code> passes
     *
     * @param expected expected value
     * @param actual the value to check against <code>expected</code>
     * @param delta the maximum delta between <code>expected</code> and <code>actual</code> for which both numbers are still considered equal.
     */

    static public void assertEquals(float expected, float actual, float delta) {
        assertEquals(null, expected, actual, delta);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param object Object to check or <code>null</code>
     */
    static public void assertNotNull(String message, Object object) {
        assertTrue(message, object != null);
    }

    /**
     * Asserts that an object isn't null. If it is an {@link AssertionError} is thrown.
     *
     * @param object Object to check or <code>null</code>
     */
    static public void assertNotNull(Object object) {
        assertNotNull(null, object);
    }

    /**
     * Asserts that an object is null. If it is not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param object Object to check or <code>null</code>
     */
    static public void assertNull(String message, Object object) {
        if (object == null) {
            return;
        }
        failNotNull(message, object);
    }

    /**
     * Asserts that an object is null. If it isn't an {@link AssertionError} is thrown.
     *
     * @param object Object to check or <code>null</code>
     */
    static public void assertNull(Object object) {
        assertNull(null, object);
    }

    static private void failNotNull(String message, Object actual) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected null, but was:<" + actual + ">");
    }

    /**
     * Asserts that two objects refer to the same object. If they are not, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expected the expected object
     * @param actual the object to compare to <code>expected</code>
     */
    static public void assertSame(String message, Object expected, Object actual) {
        if (expected == actual) {
            return;
        }
        failNotSame(message, expected, actual);
    }

    /**
     * Asserts that two objects refer to the same object. If they are not the same, an {@link AssertionError} without a message is thrown.
     *
     * @param expected the expected object
     * @param actual the object to compare to <code>expected</code>
     */
    static public void assertSame(Object expected, Object actual) {
        assertSame(null, expected, actual);
    }

    /**
     * Asserts that two objects do not refer to the same object. If they do refer to the same object, an {@link AssertionError} is thrown with the given message.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param unexpected the object you don't expect
     * @param actual the object to compare to <code>unexpected</code>
     */
    static public void assertNotSame(String message, Object unexpected, Object actual) {
        if (unexpected == actual) {
            failSame(message);
        }
    }

    /**
     * Asserts that two objects do not refer to the same object. If they do refer to the same object, an {@link AssertionError} without a message is thrown.
     *
     * @param unexpected the object you don't expect
     * @param actual the object to compare to <code>unexpected</code>
     */
    static public void assertNotSame(Object unexpected, Object actual) {
        assertNotSame(null, unexpected, actual);
    }

    static private void failSame(String message) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected not same");
    }

    static private void failNotSame(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null) {
            formatted = message + " ";
        }
        fail(formatted + "expected same:<" + expected + "> was not:<" + actual + ">");
    }

    static private void failNotEquals(String message, Object expected, Object actual) {
        fail(format(message, expected, actual));
    }

    static String format(String message, Object expected, Object actual) {
        String formatted = "";
        if (message != null && !message.equals("")) {
            formatted = message + " ";
        }
        String expectedString = String.valueOf(expected);
        String actualString = String.valueOf(actual);
        if (expectedString.equals(actualString)) {
            return formatted + "expected: " + formatClassAndValue(expected, expectedString) + " but was: " + formatClassAndValue(actual, actualString);
        } else {
            return formatted + "expected:<" + expectedString + "> but was:<" + actualString + ">";
        }
    }

    private static String formatClassAndValue(Object value, String valueString) {
        String className = value == null ? "null" : value.getClass().getName();
        return className + "<" + valueString + ">";
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an {@link AssertionError} is thrown with the given message. If <code>expecteds</code> and <code>actuals</code> are <code>null</code>, they are considered equal.
     *
     * @param message the identifying message for the {@link AssertionError} (<code>null</code> okay)
     * @param expecteds Object array or array of arrays (multi-dimensional array) with expected values.
     * @param actuals Object array or array of arrays (multi-dimensional array) with actual values
     * @deprecated use assertArrayEquals
     */
    @Deprecated
    public static void assertEquals(String message, Object[] expecteds, Object[] actuals) {
        assertArrayEquals(message, expecteds, actuals);
    }

    /**
     * Asserts that two object arrays are equal. If they are not, an {@link AssertionError} is thrown. If <code>expected</code> and <code>actual</code> are <code>null</code>, they are considered equal.
     *
     * @param expecteds Object array or array of arrays (multi-dimensional array) with expected values
     * @param actuals Object array or array of arrays (multi-dimensional array) with actual values
     * @deprecated use assertArrayEquals
     */
    @Deprecated
    public static void assertEquals(Object[] expecteds, Object[] actuals) {
        assertArrayEquals(expecteds, actuals);
    }

    /**
     * Asserts that <code>actual</code> satisfies the condition specified by <code>matcher</code>. If not, an {@link AssertionError} is thrown with information about the matcher and failing value. Example:
     *
     * <pre>
     *   assertThat(0, is(1)); // fails:
     *     // failure message:
     *     // expected: is &lt;1&gt;
     *     // got value: &lt;0&gt;
     *   assertThat(0, is(not(1))) // passes
     * </pre>
     *
     * <code>org.hamcrest.Matcher</code> does not currently document the meaning of its type parameter <code>T</code>. This method assumes that a matcher typed as <code>Matcher&lt;T&gt;</code> can be meaningfully applied only to values that could be assigned to a variable of type <code>T</code>.
     *
     * @param <T> the static type accepted by the matcher (this can flag obvious compile-time problems such as {@code assertThat(1, is("a"))}
     * @param actual the computed value being compared
     * @param matcher an expression, built of {@link Matcher}s, specifying allowed values
     * @see org.hamcrest.CoreMatchers
     * @see org.hamcrest.MatcherAssert
     */
    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    /**
     * Asserts that <code>actual</code> satisfies the condition specified by <code>matcher</code>. If not, an {@link AssertionError} is thrown with the reason and information about the matcher and failing value. Example:
     *
     * <pre>
     *   assertThat(&quot;Help! Integers don't work&quot;, 0, is(1)); // fails:
     *     // failure message:
     *     // Help! Integers don't work
     *     // expected: is &lt;1&gt;
     *     // got value: &lt;0&gt;
     *   assertThat(&quot;Zero is one&quot;, 0, is(not(1))) // passes
     * </pre>
     *
     * <code>org.hamcrest.Matcher</code> does not currently document the meaning of its type parameter <code>T</code>. This method assumes that a matcher typed as <code>Matcher&lt;T&gt;</code> can be meaningfully applied only to values that could be assigned to a variable of type <code>T</code>.
     *
     * @param reason additional information about the error
     * @param <T> the static type accepted by the matcher (this can flag obvious compile-time problems such as {@code assertThat(1, is("a"))}
     * @param actual the computed value being compared
     * @param matcher an expression, built of {@link Matcher}s, specifying allowed values
     * @see org.hamcrest.CoreMatchers
     * @see org.hamcrest.MatcherAssert
     */
    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(reason, actual, matcher);
    }
}
