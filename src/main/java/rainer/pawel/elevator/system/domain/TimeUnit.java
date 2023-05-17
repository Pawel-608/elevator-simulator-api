package rainer.pawel.elevator.system.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;


import static rainer.pawel.elevator.system.domain.util.EnsureUtils.ensureThat;

@ToString
@EqualsAndHashCode
public class TimeUnit implements Comparable<TimeUnit> {

    public static final TimeUnit ZERO = TimeUnit.of(0);

    private final int amountOfTime;

    public TimeUnit(int amountOfTime) {
        ensureThat(amountOfTime >= 0, () -> new ElevatorSystemException("amount of time cannot be negative"));

        this.amountOfTime = amountOfTime;
    }

    public static TimeUnit of(int amountOfTime) {
        return new TimeUnit(amountOfTime);
    }

    public TimeUnit add(TimeUnit other) {
        return new TimeUnit(amountOfTime + other.amountOfTime);
    }

    public TimeUnit increment() {
        return new TimeUnit(amountOfTime + 1);
    }

    public TimeUnit multiplyBy(int multiplier) {
        return new TimeUnit(amountOfTime * multiplier);
    }

    public boolean isGrater(TimeUnit other) {
        return amountOfTime > other.amountOfTime;
    }

    @Override
    public int compareTo(TimeUnit o) {
        return Integer.compare(amountOfTime, o.amountOfTime);
    }
}
