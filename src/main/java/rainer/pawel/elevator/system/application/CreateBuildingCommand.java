package rainer.pawel.elevator.system.application;

public record CreateBuildingCommand(
        int floorsNumber,
        int elevatorsNumber,
        int elevatorBaseFloorNumber,
        int elevatorMaxTimeToBeInactive

) {

}
