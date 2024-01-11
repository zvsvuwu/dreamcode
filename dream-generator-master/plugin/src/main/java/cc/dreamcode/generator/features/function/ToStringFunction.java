package cc.dreamcode.generator.features.function;

@FunctionalInterface
public interface ToStringFunction<T> {
    String applyAsString(T value);
}
