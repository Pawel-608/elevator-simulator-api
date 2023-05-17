package rainer.pawel.elevator.system.domain;

import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;


import static rainer.pawel.elevator.system.domain.util.EnsureUtils.ensureThat;

public record Floor(

        int floorNumber

) implements Comparable<Floor> {

    public static final Floor GROUND_FLOOR = Floor.of(0);

    public Floor {
        ensureThat(floorNumber >= 0, () -> ElevatorSystemException.of("floor number must be positive, actual: %s", floorNumber));
    }

    public static Floor of(int floorNumber) {
        return new Floor(floorNumber);
    }

    public boolean isLower(Floor other) {
        return floorNumber < other.floorNumber;
    }

    public boolean isLowerOrEqual(Floor other) {
        return floorNumber <= other.floorNumber;
    }

    public boolean isHigher(Floor other) {
        return floorNumber > other.floorNumber;
    }

    public boolean isHigherOrEqual(Floor other) {
        return floorNumber >= other.floorNumber;
    }

    public int difference(Floor other) {
        return Math.abs(floorNumber - other.floorNumber);
    }

    @Override
    public int compareTo(Floor o) {
        return Integer.compare(floorNumber, o.floorNumber);
    }
}
