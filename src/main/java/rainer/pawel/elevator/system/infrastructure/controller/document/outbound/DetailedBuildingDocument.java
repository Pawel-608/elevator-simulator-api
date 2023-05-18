package rainer.pawel.elevator.system.infrastructure.controller.document.outbound;

import java.util.List;

import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.ElevatorsInfo;

public record DetailedBuildingDocument(
        Id buildingId,
        List<ElevatorDocument> elevatorsInfo
) {

    public static DetailedBuildingDocument from(Id buildingId, ElevatorsInfo elevatorsInfo) {
        return new DetailedBuildingDocument(
                buildingId,
                elevatorsInfo.elevatorsInfo()
                        .stream()
                        .map(ElevatorDocument::from)
                        .toList()
        );
    }
}
