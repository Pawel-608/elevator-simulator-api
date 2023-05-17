package rainer.pawel.elevator.system.infrastructure.building.controller.document.outbound;

import rainer.pawel.elevator.system.domain.Id;
import java.util.List;

public record BuildingDocument(
        Id buildingId,
        List<Id> elevatorIds
) {

}
