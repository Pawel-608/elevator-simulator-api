package rainer.pawel.elevator.system.domain.building.elevator.controller;

import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.ElevatorsInfo;
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall;
import rainer.pawel.elevator.system.domain.building.elevator.call.InternalElevatorCall;

public interface ElevatorController {

    void registerCall(ExternalElevatorCall elevatorCall);

    void registerCall(InternalElevatorCall elevatorCall);

    void makeStep();

    ElevatorsInfo getElevatorsState();

    void assignToBuilding(Building building);

    Id getCorespondingBuildingId();
}
