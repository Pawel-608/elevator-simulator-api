package rainer.pawel.elevator.system.domain.building.elevator;

import java.util.List;

import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;

public record ElevatorInfo(
        Id id,
        ElevatorState state,
        Floor currentFloor,
        List<Floor> targetFloors
) {

}
