package wooteco.subway.domain.fare;

import java.util.Arrays;

public enum AgeDiscountPolicy {

    BABY(Age.MIN, Age.CHILD_MIN, 0, 1.00),
    CHILD(Age.CHILD_MIN, Age.ADOLESCENT_MIN, 350, 0.50),
    ADOLESCENT(Age.ADOLESCENT_MIN, Age.ADULT_MIN, 350, 0.20),
    ADULT(Age.ADULT_MIN, Age.ELDERLY_MIN, 0, 0.00),
    ELDERLY(Age.ELDERLY_MIN, Age.MAX, 0, 1.00),
    ;

    private static final String INVALID_AGE_RANGE_EXCEPTION =
            String.format("%d과 %d 사이의 연령만 입력가능합니다.", Age.MIN, Age.MAX);

    private final int startInclusive;
    private final int endExclusive;
    private final int discountAmount;
    private final double discountRatio;

    AgeDiscountPolicy(int startInclusive, int endExclusive, int discountAmount, double discountRatio) {
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.discountAmount = discountAmount;
        this.discountRatio = discountRatio;
    }

    public static AgeDiscountPolicy of(int value) {
        return Arrays.stream(values())
                .filter(age -> age.isAgeOf(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_AGE_RANGE_EXCEPTION));
    }

    private boolean isAgeOf(int value) {
        return value >= startInclusive && value < endExclusive;
    }

    public int applyDiscount(int fare) {
        return (int) ((fare - discountAmount) * (1 - discountRatio));
    }

    private static class Age {

        static final int MIN = 0;
        static final int CHILD_MIN = 6;
        static final int ADOLESCENT_MIN = 13;
        static final int ADULT_MIN = 19;
        static final int ELDERLY_MIN = 65;
        static final int MAX = 150;
    }
}
