package rainer.pawel.elevator.system.infrastructure.controller.document.outbound;

import java.util.List;

import rainer.pawel.elevator.system.domain.Id;

public record BuildingDocument(
        Id buildingId,
        List<Id> elevatorIds
) {

}
