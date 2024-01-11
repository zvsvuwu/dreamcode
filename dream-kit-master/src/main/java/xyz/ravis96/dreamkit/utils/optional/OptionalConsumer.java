package xyz.ravis96.dreamkit.utils.optional;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class OptionalConsumer<T> {

    private final Optional<T> optional;

    public static <T> OptionalConsumer<T> of(Optional<T> optional) {
        return new OptionalConsumer<>(optional);
    }

    public void ifPresentOrElse(Consumer<T> c, Runnable r) {
        if(!this.optional.isPresent()) r.run();
        this.optional.ifPresent(c);
    }

}
