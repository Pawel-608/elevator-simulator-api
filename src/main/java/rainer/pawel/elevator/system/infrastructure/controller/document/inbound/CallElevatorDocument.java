package rainer.pawel.elevator.system.infrastructure.controller.document.inbound;

public record CallElevatorDocument(
        int fromFloor,
        int toFloor
) {

}
