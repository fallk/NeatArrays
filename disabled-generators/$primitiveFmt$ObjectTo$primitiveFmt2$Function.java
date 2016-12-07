/**
 * Represents a function that accepts two arguments, a $primitive$ and a generic, and produces a
 * $primitive2$ result.  This is the {@code $primitive$}-consuming-to-{@code $primitive2$} primitive specialization for
 * {@link java.util.function.BiFunction}.
 *
 * @param <T> the type of the generic function argument
 *
 * @see java.util.function.BiFunction
 */
@FunctionalInterface
public interface $primitiveFmt$ObjectTo$primitiveFmt2$Function<T> {

    /**
     * Applies this function to the given arguments.
     *
     * @param v1 the {@code $primitive$} argument
     * @param v2 the generic argument
     * @return the function result
     */
    $primitive2$ apply($primitive$ v1, T v2);
}
