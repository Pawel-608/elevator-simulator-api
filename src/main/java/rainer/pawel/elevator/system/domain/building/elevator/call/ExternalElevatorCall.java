package rainer.pawel.elevator.system.domain.building.elevator.call;

import rainer.pawel.elevator.system.domain.Direction;
import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;


import static rainer.pawel.elevator.system.domain.Direction.DOWN;
import static rainer.pawel.elevator.system.domain.Direction.UP;
import static rainer.pawel.elevator.system.domain.util.EnsureUtils.ensureThat;

/**
 * Elevator call from panel on corridor
 */
public record ExternalElevatorCall(
        Floor fromFloor,
        Floor toFloor
) {

    public ExternalElevatorCall {
        ensureThat(!fromFloor.equals(toFloor), () -> new ElevatorSystemException("from and to floors cannot be same"));
    }

    public Direction getDirection() {
        return fromFloor.isHigher(toFloor) ? DOWN : UP;
    }
}
