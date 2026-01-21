package sbp.util;

import java.util.random.RandomGenerator;


public class Generator {

    private static final RandomGenerator random = RandomGenerator.getDefault();

    private Generator() {
    }

    public static int getDirection() {
        return random.nextInt(7);
    }

    public static int getValue(int bound) {
        return random.nextInt(bound);
    }

}