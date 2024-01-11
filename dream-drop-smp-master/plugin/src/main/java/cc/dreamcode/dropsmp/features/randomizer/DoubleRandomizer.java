package cc.dreamcode.dropsmp.features.randomizer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@RequiredArgsConstructor
public class DoubleRandomizer {

    private final double firstDouble;
    private final double secondDouble;

    public double randomize() {
        if (this.getFirstDouble() >= this.getSecondDouble()) {
            return this.getSecondDouble();
        }

        return new Random().nextDouble() * (this.getSecondDouble() - this.getFirstDouble()) + this.getFirstDouble();
    }

    public static double range(final double min, final double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

}
