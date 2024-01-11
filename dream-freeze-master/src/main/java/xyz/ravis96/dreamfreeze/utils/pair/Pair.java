package xyz.ravis96.dreamfreeze.utils.pair;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;

import java.util.Random;

@Getter
@RequiredArgsConstructor
public class Pair<F, S> {

    private final F left;
    private final S right;

    public int getRandomBetweenIntValues() {
        Validate.isTrue(left instanceof Integer, "Left value must be Integer!");
        Validate.isTrue(right instanceof Integer, "Right value must be Integer!");

        int leftValue = (Integer) left;
        int rightValue = (Integer) right;

        if(leftValue == rightValue) {
            return leftValue;
        }

        Validate.isTrue(leftValue < rightValue, "Right must be bigger than left value!");
        return rightValue - new Random().nextInt(leftValue + 1);
    }

}
