package rainer.pawel.elevator.system.application;

import rainer.pawel.elevator.system.domain.Id;

public record CallSpecificElevatorCommand(
        Id buildingId,
        Id elevatorId,
        int toFloor
) {

}
