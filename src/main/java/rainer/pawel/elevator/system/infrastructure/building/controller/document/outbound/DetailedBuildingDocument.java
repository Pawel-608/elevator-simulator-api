package rainer.pawel.elevator.system.infrastructure.building.controller.document.outbound;

import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.ElevatorsInfo;
import java.util.List;

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
