package rainer.pawel.elevator.system.infrastructure.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import rainer.pawel.elevator.system.application.BuildingService;
import rainer.pawel.elevator.system.application.command.CallElevatorCommand;
import rainer.pawel.elevator.system.application.command.CallSpecificElevatorCommand;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.BuildingRepository;

@Service
public class ThreadSafeBuildingService extends BuildingService {

    private final Map<Id, Object> buildingIdToLock = new ConcurrentHashMap<>();

    public ThreadSafeBuildingService(BuildingRepository buildingRepository) {
        super(buildingRepository);
    }

    @Override
    public void callElevator(CallElevatorCommand command) {
        synchronized (getLock(command.buildingId())) {
            super.callElevator(command);
        }
    }

    @Override
    public void callElevator(CallSpecificElevatorCommand command) {
        synchronized (getLock(command.buildingId())) {
            super.callElevator(command);
        }
    }

    @Override
    public void makeStep(Id buildingId) {
        synchronized (getLock(buildingId)) {
            super.makeStep(buildingId);
        }
    }

    private Object getLock(Id buildingId) {
        return buildingIdToLock.computeIfAbsent(buildingId, (k) -> new Object());
    }
}
