package rainer.pawel.elevator.system.domain.building;

import rainer.pawel.elevator.system.domain.building.elevator.ElevatorInfo;
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall;
import rainer.pawel.elevator.system.domain.building.elevator.call.InternalElevatorCall;
import java.util.List;

public record ElevatorsInfo(
        List<ElevatorInfo> elevatorsInfo,
        List<ExternalElevatorCall> externalCallsToBeScheduled,
        List<InternalElevatorCall> internalCallsToBeScheduled

) {

}
