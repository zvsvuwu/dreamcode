package cc.dreamcode.restarts.features.function;

@FunctionalInterface
public interface ToStringFunction<T> {
    String applyAsString(T value);
}
