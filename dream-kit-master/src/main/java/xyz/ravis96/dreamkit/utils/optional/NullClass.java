package xyz.ravis96.dreamkit.utils.optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@NoArgsConstructor
@AllArgsConstructor
public class NullClass<T> {

    protected T value;

    public T get() {
        if(value == null) {
            throw new PluginNullPointerException();
        }
        return this.value;
    }

    public T getOrNull() {
        if(value == null) {
            return null;
        }
        return this.value;
    }

    public boolean isPresent() {
        return value != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    public static final class PluginNullPointerException extends IllegalStateException {
        private static final List<String> exceptionMessage = Arrays.asList(
                "ERR_API: Plugin nie moze pobrac informacji!",
                " ",
                "Funkcje pluginu sa wylaczone, lub jeszcze nie zaladowane.",
                " ",
                "Jezeli jest to blad spowodowany bez uzycia zewn. pluginu,",
                "to zglos istniejacy problem autor'owi (dc).",
                " "
        );

        PluginNullPointerException() {
            super(StringUtils.join(exceptionMessage, System.lineSeparator()));
        }
    }
}
