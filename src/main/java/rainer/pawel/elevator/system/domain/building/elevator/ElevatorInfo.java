package rainer.pawel.elevator.system.domain.building.elevator;

import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;
import java.util.List;

public record ElevatorInfo(
        Id id,
        ElevatorState state,
        Floor currentFloor,
        List<Floor> targetFloors
) {

}
