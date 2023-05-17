package rainer.pawel.elevator.system.domain.building;

import io.cucumber.java.ParameterType;
import io.cucumber.java8.En;
import rainer.pawel.elevator.system.domain.ElevatorSystemContext;
import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.elevator.ElevatorInfo;
import rainer.pawel.elevator.system.domain.building.elevator.ElevatorState;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static rainer.pawel.elevator.system.domain.building.util.StepsUtils.makeNSteps;

public class ElevatorSteps implements En {

    public ElevatorSteps(ElevatorSystemContext context) {
        Given("Elevator no. {int} is on floor {int}",
                (Integer elevatorId, Integer floor) -> {
                    callElevator(context, elevatorId, floor);
                    makeNSteps(context, floor + 1);
                });

        When("There is a call to elevator no. {int} to go to floor {int}",
                (Integer elevatorId, Integer floor) -> {
                    callElevator(context, elevatorId, floor);
                }
        );

        When("There is a call to go from {int} to {int} floor",
                (Integer from, Integer to) -> {
                    context.getBuilding().callElevator(from, to);
                }
        );

        Then("Elevator no. {int} should be on floor {int}",
                (Integer elevatorId, Integer expectedFloor) -> {
                    Floor actualFloor = getElevatorInfo(context, elevatorId).currentFloor();

                    assertEquals(Floor.of(expectedFloor), actualFloor);
                }
        );

        Then("Elevator no. {int} should be on floor {int} in the {state} state",
                (Integer elevatorId, Integer expectedFloor, ElevatorState state) -> {
                    ElevatorInfo elevatorInfo = getElevatorInfo(context, elevatorId);

                    Floor actualFloor = elevatorInfo.currentFloor();
                    ElevatorState elevatorsState = elevatorInfo.state();

                    assertEquals(Floor.of(expectedFloor), actualFloor);
                    assertEquals(state, elevatorsState);
                }
        );

        Then("Elevator no. {int} should be in the {state} state",
                (Integer elevatorId, ElevatorState state) -> {
                    ElevatorInfo elevatorInfo = getElevatorInfo(context, elevatorId);

                    assertEquals(state, elevatorInfo.state());
                }
        );

        Then("Wait on floor",
                () -> {
                    makeNSteps(context, 1);
                }
        );

    }

    private ElevatorInfo getElevatorInfo(ElevatorSystemContext context, int elevatorId) {
        Id elevatorIdentifier = context.getElevatorId(elevatorId);

        return context.getBuilding().getElevatorsInfo()
                .elevatorsInfo()
                .stream()
                .filter(elevatorInfo -> elevatorInfo.id().equals(elevatorIdentifier))
                .findFirst()
                .orElseThrow();
    }

    private void callElevator(ElevatorSystemContext context, int elevatorId, int floor) {
        Id elevatorIdentifier = context.getElevatorId(elevatorId);

        context.getBuilding().callElevator(elevatorIdentifier, floor);
    }

    @ParameterType(".*")
    public ElevatorState state(String state) {
        return ElevatorState.valueOf(state);
    }
}
