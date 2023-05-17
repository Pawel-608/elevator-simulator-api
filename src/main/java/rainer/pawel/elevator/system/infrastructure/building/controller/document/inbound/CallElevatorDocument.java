package rainer.pawel.elevator.system.infrastructure.building.controller.document.inbound;

public record CallElevatorDocument(
        int fromFloor,
        int toFloor
) {

}
