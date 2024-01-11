package cc.dreamcode.dropsmp.features.function;

@FunctionalInterface
public interface ToStringFunction<T> {
    String applyAsString(T value);
}
