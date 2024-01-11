package cc.dreamcode.generator.features.function;

@FunctionalInterface
public interface ToBooleanFunction<T> {
    Boolean applyAsBoolean(T value);
}
