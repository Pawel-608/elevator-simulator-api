package rainer.pawel.elevator.system.domain.building;

import java.util.List;
import java.util.stream.IntStream;

import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall;
import rainer.pawel.elevator.system.domain.building.elevator.call.InternalElevatorCall;
import rainer.pawel.elevator.system.domain.building.elevator.controller.ElevatorController;
import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;


import static rainer.pawel.elevator.system.domain.util.EnsureUtils.ensureThat;

public class Building {

    private final Id id;

    private final ElevatorController elevatorController;

    private final List<Floor> floors;

    private final int numberOfFloors;

    public Building(ElevatorController elevatorController, int numberOfFloors) {
        this.id = Id.unique();

        this.numberOfFloors = numberOfFloors;
        this.floors = IntStream.range(0, this.numberOfFloors + 1)
                .boxed()
                .map(Floor::new)
                .toList();

        this.elevatorController = elevatorController;
        this.elevatorController.assignToBuilding(this);
    }

    public Id getId() {
        return id;
    }

    public Floor getFloor(int floor) {
        ensureFloorExist(floor);

        return floors.get(floor);
    }

    public ElevatorsInfo getElevatorsInfo() {
        return elevatorController.getElevatorsState();
    }

    public Floor getHigherFloor(Floor floor) {
        ensureFloorExist(floor.floorNumber());

        return floors.get(floor.floorNumber() + 1);
    }

    public Floor getLowerFloor(Floor floor) {
        ensureFloorExist(floor.floorNumber());

        return floors.get(floor.floorNumber() - 1);
    }

    private void ensureFloorExist(int floor) {
        ensureThat(floor >= 0 && floor <= numberOfFloors,
                () -> ElevatorSystemException.of("Invalid floor: %s - floors in building 0 - %s", floor, numberOfFloors));
    }

    public void callElevator(int fromFloor, int toFloor) {
        ExternalElevatorCall elevatorCall = new ExternalElevatorCall(getFloor(fromFloor), getFloor(toFloor));

        elevatorController.registerCall(elevatorCall);
    }

    public void callElevator(Id elevatorId, int toFloor) {
        InternalElevatorCall elevatorCall = new InternalElevatorCall(elevatorId, getFloor(toFloor));

        elevatorController.registerCall(elevatorCall);
    }

    public void makeStep() {
        elevatorController.makeStep();
    }

}
