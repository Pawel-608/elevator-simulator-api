package rainer.pawel.elevator.system.application;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import rainer.pawel.elevator.system.application.command.CallElevatorCommand;
import rainer.pawel.elevator.system.application.command.CallSpecificElevatorCommand;
import rainer.pawel.elevator.system.application.command.CreateBuildingCommand;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.TimeUnit;
import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.BuildingRepository;
import rainer.pawel.elevator.system.domain.building.elevator.Elevator;
import rainer.pawel.elevator.system.domain.building.elevator.ReturningElevator;
import rainer.pawel.elevator.system.domain.building.elevator.controller.ElevatorController;
import rainer.pawel.elevator.system.domain.building.elevator.controller.SchedulingElevatorController;
import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public void createBuilding(CreateBuildingCommand command) {
        List<? extends Elevator> elevators = Stream.generate(() -> 0)
                .limit(command.elevatorsNumber())
                .map(elevator -> new ReturningElevator(command.elevatorBaseFloorNumber(), TimeUnit.of(command.elevatorMaxTimeToBeInactive())))
                .toList();

        ElevatorController elevatorController = new SchedulingElevatorController(elevators);

        Building building = new Building(elevatorController, command.floorsNumber());

        buildingRepository.save(building);
    }

    public void callElevator(CallElevatorCommand command) {
        getBuilding(command.buildingId())
                .callElevator(command.fromFloor(), command.toFloor());
    }

    public void callElevator(CallSpecificElevatorCommand command) {
        getBuilding(command.buildingId())
                .callElevator(command.elevatorId(), command.toFloor());
    }

    public void makeStep(Id buildingId) {
        getBuilding(buildingId)
                .makeStep();
    }

    private Building getBuilding(Id buildingId) {
        return buildingRepository.find(buildingId)
                .orElseThrow(() -> ElevatorSystemException.of("Cannot find building %s", buildingId));
    }
}
