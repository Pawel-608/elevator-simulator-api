package rainer.pawel.elevator.system.domain.building;

import io.cucumber.java8.En;
import java.util.List;
import java.util.stream.Stream;

import rainer.pawel.elevator.system.domain.ElevatorSystemContext;
import rainer.pawel.elevator.system.domain.TimeUnit;
import rainer.pawel.elevator.system.domain.building.elevator.Elevator;
import rainer.pawel.elevator.system.domain.building.elevator.ReturningElevator;
import rainer.pawel.elevator.system.domain.building.elevator.controller.ElevatorController;
import rainer.pawel.elevator.system.domain.building.elevator.controller.SchedulingElevatorController;


import static rainer.pawel.elevator.system.domain.building.util.StepsUtils.makeNSteps;

public class BuildingSteps implements En {

    public BuildingSteps(ElevatorSystemContext context) {
        Given("There is a building with {int} floors and {int} elevators",
                (Integer numberOfFloors, Integer elevatorCount) -> {
                    Building building = createBuilding(numberOfFloors, elevatorCount, 0, 10000);

                    context.setBuilding(building);
                });

        Given("There is a building with {int} floors and {int} elevators. Elevators base floor is {int} and max time to be inactive is {int}",
                (Integer numberOfFloors, Integer elevatorCount, Integer baseFloor, Integer maxTimeToBeInactive) -> {
                    Building building = createBuilding(numberOfFloors, elevatorCount, baseFloor, maxTimeToBeInactive);

                    context.setBuilding(building);
                });

        And("Make {int} steps",
                (Integer numberOfSteps) -> {
                    makeNSteps(context, numberOfSteps);
                }
        );

    }

    private Building createBuilding(int numberOfFloors, int elevatorCount, int baseFloor, int maxTimeToBeInactive) {
        List<? extends Elevator> elevators = Stream.generate(() -> 0)
                .limit(elevatorCount)
                .map(elevator -> new ReturningElevator(baseFloor, TimeUnit.of(maxTimeToBeInactive)))
                .toList();

        ElevatorController elevatorController = new SchedulingElevatorController(elevators);

        return new Building(elevatorController, numberOfFloors);
    }
}
