package rainer.pawel.elevator.system.domain.building.elevator.call;

import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;

/**
 * Elevator call from internal elevator panel
 */
public record InternalElevatorCall(
        Id elevatorId,
        Floor toFloor
) {

}
