package rainer.pawel.elevator.system.domain.building.elevator;

import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.TimeUnit;
import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall;

public interface Elevator {

    Id getId();

    ElevatorInfo getInfo();

    void assignToBuilding(Building building);

    void makeStep();

    boolean canHandleCall(Floor targetFloor);

    boolean canHandleCall(ExternalElevatorCall call);

    boolean isIdle();

    void handleCall(Floor targetFloor);

    void handleCall(ExternalElevatorCall elevatorCall);

    TimeUnit calculateCallCost(ExternalElevatorCall call);
}
