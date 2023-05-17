package rainer.pawel.elevator.system.domain;

import java.util.List;

import lombok.Getter;

import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.elevator.ElevatorInfo;

@Getter
public class ElevatorSystemContext {

    private Building building;

    private List<Id> elevatorsIds;

    public void setBuilding(Building building) {
        this.building = building;

        elevatorsIds = building.getElevatorsInfo()
                .elevatorsInfo()
                .stream()
                .map(ElevatorInfo::id)
                .toList();
    }

    public Id getElevatorId(Integer elevatorId) {
        return elevatorsIds.get(elevatorId - 1);
    }
}
