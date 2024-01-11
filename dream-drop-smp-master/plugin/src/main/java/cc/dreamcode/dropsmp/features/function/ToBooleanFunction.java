package cc.dreamcode.dropsmp.features.function;

@FunctionalInterface
public interface ToBooleanFunction<T> {
    Boolean applyAsBoolean(T value);
}
