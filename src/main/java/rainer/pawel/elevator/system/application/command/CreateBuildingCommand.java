package rainer.pawel.elevator.system.application.command;

public record CreateBuildingCommand(
        int floorsNumber,
        int elevatorsNumber,
        int elevatorBaseFloorNumber,
        int elevatorMaxTimeToBeInactive

) {

}
