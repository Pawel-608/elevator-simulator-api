package rainer.pawel.elevator.system.domain.building.elevator;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import rainer.pawel.elevator.system.domain.Direction;
import rainer.pawel.elevator.system.domain.Floor;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.TimeUnit;
import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall;
import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;


import static rainer.pawel.elevator.system.domain.Direction.DOWN;
import static rainer.pawel.elevator.system.domain.Direction.UP;
import static rainer.pawel.elevator.system.domain.Floor.GROUND_FLOOR;
import static rainer.pawel.elevator.system.domain.building.elevator.ElevatorState.BUSY;
import static rainer.pawel.elevator.system.domain.building.elevator.ElevatorState.IDLE;
import static rainer.pawel.elevator.system.domain.building.elevator.ElevatorState.WAITING_ON_FLOOR;
import static rainer.pawel.elevator.system.domain.util.EnsureUtils.ensureThat;

public class DirectionBasedElevator implements Elevator {

    private static final TimeUnit TIME_TO_REACH_NEXT_FLOOR = TimeUnit.of(1);

    private static final TimeUnit TIME_TO_WAIT_ON_FLOOR = TimeUnit.of(1);

    private final Id id = Id.unique();

    protected Building building;

    private ElevatorState state = IDLE;

    private Direction direction = UP;

    protected Floor currentFloor = GROUND_FLOOR;

    private final TreeSet<Floor> floorsToVisit = new TreeSet<>();

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public ElevatorInfo getInfo() {
        return new ElevatorInfo(
                id,
                state,
                currentFloor,
                List.copyOf(floorsToVisit)
        );
    }

    @Override
    public void assignToBuilding(Building building) {
        this.building = building;
    }

    @Override
    public void makeStep() {
        if (isIdle()) {
            state = IDLE;
            return;
        }

        goToNextFloor();
        updateState();
    }

    private void goToNextFloor() {
        Floor nextFloorToVisit = getNextFloorToVisit();

        if (isCurrentFloor(nextFloorToVisit)) {
            markFloorAsVisited(nextFloorToVisit);
        } else if (nextFloorToVisit.isHigher(currentFloor)) {
            goUp();
        } else {
            goDown();
        }
    }

    private Floor getNextFloorToVisit() {
        if (direction == UP) {
            return floorsToVisit.first();
        } else {
            return floorsToVisit.last();
        }
    }

    private void markFloorAsVisited(Floor floor) {
        floorsToVisit.remove(floor);
    }

    private void goUp() {
        currentFloor = building.getHigherFloor(currentFloor);
    }

    private void goDown() {
        currentFloor = building.getLowerFloor(currentFloor);
    }

    @Override
    public boolean canHandleCall(Floor targetFloor) {
        return isIdle() || matchesDirection(targetFloor) || canChangeDirection();
    }

    private boolean canChangeDirection() {
        return floorsToVisit.size() == 1 && isCurrentFloor(floorsToVisit.first());
    }

    @Override
    public boolean canHandleCall(ExternalElevatorCall call) {
        return isIdle() || matchesDirection(call);
    }

    private boolean matchesDirection(ExternalElevatorCall call) {
        if (direction != call.getDirection()) {
            return false;
        }

        return matchesDirection(call.fromFloor());
    }

    private boolean matchesDirection(Floor floor) {
        if (calculateActualDirection() == UP) {
            return floor.isHigherOrEqual(currentFloor);
        } else {
            return floor.isLowerOrEqual(currentFloor);
        }
    }

    private Direction calculateActualDirection() {
        return floorsToVisit.headSet(currentFloor).isEmpty() ? UP : DOWN;
    }

    @Override
    public void handleCall(Floor targetFloor) {
        ensureThat(canHandleCall(targetFloor),
                () -> ElevatorSystemException.of("Cannot handle call to a floor %s elevator state: %s", targetFloor, getInfo()));

        updateDirection(targetFloor);

        floorsToVisit.add(targetFloor);

        updateState();
    }

    private void updateDirection(Floor targetFloor) {
        direction = pretendDirectionIfWantToStopOnFloor(targetFloor);
    }

    @Override
    public void handleCall(ExternalElevatorCall elevatorCall) {
        ensureThat(canHandleCall(elevatorCall),
                () -> ElevatorSystemException.of("Cannot handle call %s elevator state: %s", elevatorCall, getInfo()));

        updateDirection(elevatorCall);

        floorsToVisit.add(elevatorCall.fromFloor());
        floorsToVisit.add(elevatorCall.toFloor());

        updateState();
    }

    private void updateDirection(ExternalElevatorCall elevatorCall) {
        if (isIdle()) {
            direction = elevatorCall.getDirection();
        }
    }

    private void updateState() {
        if (isIdle()) {
            state = IDLE;
        } else if (floorsToVisit.contains(currentFloor)) {
            state = WAITING_ON_FLOOR;
        } else {
            state = BUSY;
        }
    }

    @Override
    public TimeUnit calculateCallCost(ExternalElevatorCall call) {
        SortedSet<Floor> floorsInBetween = getFloorsInBetweenOfCurrentAnd(call.fromFloor());

        int floorsToCross = currentFloor.difference(call.fromFloor());
        int floorsToVisitCount = floorsInBetween.size();

        if (!willStopOnFloor(call.fromFloor())) {
            floorsToVisitCount++;
        } else {
            floorsToVisitCount--;
        }

        if (!willStopOnFloor(call.toFloor())) {
            floorsToVisitCount++;
        }

        TimeUnit runningTime = TIME_TO_REACH_NEXT_FLOOR.multiplyBy(floorsToCross);
        TimeUnit waitingTime = TIME_TO_WAIT_ON_FLOOR.multiplyBy(floorsToVisitCount);

        return runningTime.add(waitingTime);
    }

    private SortedSet<Floor> getFloorsInBetweenOfCurrentAnd(Floor floor) {
        if (pretendDirectionIfWantToStopOnFloor(floor) == UP) {
            return floorsToVisit.headSet(floor, true);
        } else {
            return floorsToVisit.tailSet(floor, true);
        }
    }

    private Direction pretendDirectionIfWantToStopOnFloor(Floor floor) {
        if (floorsToVisit.size() > 1) {
            return direction;
        }

        if (theOnlyFloorToVisitIsNotCurrentFloor()) {
            return direction;
        }

        return floor.isLower(currentFloor) ? DOWN : UP;
    }

    private boolean theOnlyFloorToVisitIsNotCurrentFloor() {
        return floorsToVisit.size() == 1 && !isCurrentFloor(floorsToVisit.first());
    }

    private boolean willStopOnFloor(Floor floor) {
        return currentFloor == floor || floorsToVisit.contains(floor);
    }

    private boolean isCurrentFloor(Floor floor) {
        return floor.equals(currentFloor);
    }

    @Override
    public boolean isIdle() {
        return floorsToVisit.isEmpty();
    }
}
