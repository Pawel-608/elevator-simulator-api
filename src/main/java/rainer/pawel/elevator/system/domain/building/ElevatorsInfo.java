package rainer.pawel.elevator.system.domain.building;

import java.util.List;

import rainer.pawel.elevator.system.domain.building.elevator.ElevatorInfo;
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall;
import rainer.pawel.elevator.system.domain.building.elevator.call.InternalElevatorCall;

public record ElevatorsInfo(
        List<ElevatorInfo> elevatorsInfo,
        List<ExternalElevatorCall> externalCallsToBeScheduled,
        List<InternalElevatorCall> internalCallsToBeScheduled

) {

}
