package rainer.pawel.elevator.system.infrastructure.controller.document.outbound;

import java.util.List;

import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.elevator.ElevatorInfo;
import rainer.pawel.elevator.system.domain.building.elevator.ElevatorState;

public record ElevatorDocument(
        Id id,
        Floor currentFloor,
        List<Floor> targetFloors,
        ElevatorState state
) {
    public static ElevatorDocument from(ElevatorInfo elevatorInfo) {
        return new ElevatorDocument(
                elevatorInfo.id(),
                elevatorInfo.currentFloor(),
                elevatorInfo.targetFloors(),
                elevatorInfo.state()
        );
    }
}
