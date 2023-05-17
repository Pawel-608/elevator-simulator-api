package rainer.pawel.elevator.system.application;

import rainer.pawel.elevator.system.domain.Id;

public record CallElevatorCommand(
        Id buildingId,
        int fromFloor,
        int toFloor
) {

}
