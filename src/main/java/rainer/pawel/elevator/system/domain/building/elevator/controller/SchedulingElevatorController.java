package rainer.pawel.elevator.system.domain.building.elevator.controller;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.ElevatorsInfo;
import rainer.pawel.elevator.system.domain.building.elevator.Elevator;
import rainer.pawel.elevator.system.domain.building.elevator.ElevatorInfo;
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall;
import rainer.pawel.elevator.system.domain.building.elevator.call.InternalElevatorCall;
import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;


import static java.util.stream.Collectors.toMap;
import static rainer.pawel.elevator.system.domain.util.EnsureUtils.ensureThat;

public class SchedulingElevatorController implements ElevatorController {

    private Id buildingId;

    private final Map<Id, Elevator> elevators;

    private final List<ExternalElevatorCall> externalCallsToBeScheduled = new LinkedList<>();

    private final List<InternalElevatorCall> internalCallsToBeScheduled = new LinkedList<>();

    public SchedulingElevatorController(List<? extends Elevator> elevators) {
        ensureThat(elevators.size() > 0, () -> new ElevatorSystemException("There must be at least 1 elevator in a building"));
        ensureThat(elevators.size() <= 16, () -> new ElevatorSystemException("Max numbers of elevators is 16"));

        this.elevators = elevators.stream()
                .collect(toMap(Elevator::getId, Function.identity()));
    }

    @Override
    public void registerCall(ExternalElevatorCall elevatorCall) {
        findMostEfficientElevator(elevatorCall)
                .ifPresentOrElse(
                        elevator -> {
                            elevator.handleCall(elevatorCall);
                        },
                        () -> externalCallsToBeScheduled.add(elevatorCall)
                );
    }

    @Override
    public void registerCall(InternalElevatorCall elevatorCall) {
        Elevator elevator = elevators.get(elevatorCall.elevatorId());

        if (elevator.canHandleCall(elevatorCall.toFloor())) {
            elevator.handleCall(elevatorCall.toFloor());
        } else {
            internalCallsToBeScheduled.add(elevatorCall);
        }
    }

    @Override
    public void makeStep() {
        scheduleInternalCalls();
        scheduleExternalCalls();

        elevators.values()
                .forEach(Elevator::makeStep);
    }

    private void scheduleInternalCalls() {
        List<InternalElevatorCall> scheduledCalls = new LinkedList<>();

        internalCallsToBeScheduled.forEach(
                call -> {
                    Elevator elevator = elevators.get(call.elevatorId());

                    if (elevator == null) {
                        throw ElevatorSystemException.of("Cannot find elevator %s in a building %s", call.elevatorId(), buildingId);
                    }

                    if (elevator.canHandleCall(call.toFloor())) {
                        elevator.handleCall(call.toFloor());

                        scheduledCalls.add(call);
                    }
                }
        );

        scheduledCalls.forEach(internalCallsToBeScheduled::remove);
    }

    private void scheduleExternalCalls() {
        List<ExternalElevatorCall> scheduledCalls = new LinkedList<>();

        externalCallsToBeScheduled.forEach(
                call -> {
                    findMostEfficientElevator(call).ifPresent(
                            elevator -> {
                                elevator.handleCall(call);
                                scheduledCalls.add(call);
                            }
                    );
                }
        );

        scheduledCalls.forEach(externalCallsToBeScheduled::remove);
    }

    public Optional<Elevator> findMostEfficientElevator(ExternalElevatorCall elevatorCall) {
        return elevators.values()
                .stream()
                .filter(elevator -> elevator.canHandleCall(elevatorCall))
                .min(Comparator.comparing(elevator -> elevator.calculateCallCost(elevatorCall)));
    }

    @Override
    public ElevatorsInfo getElevatorsState() {
        List<ElevatorInfo> elevatorsInfo = elevators.values()
                .stream()
                .map(Elevator::getInfo)
                .toList();

        return new ElevatorsInfo(
                elevatorsInfo,
                List.copyOf(externalCallsToBeScheduled),
                List.copyOf(internalCallsToBeScheduled)
        );
    }

    @Override
    public void assignToBuilding(Building building) {
        buildingId = building.getId();

        elevators.values()
                .forEach(elevator -> elevator.assignToBuilding(building));
    }
}
