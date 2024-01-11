package cc.dreamcode.cobblex.features.function;

@FunctionalInterface
public interface ToStringFunction<T> {
    String applyAsString(T value);
}
