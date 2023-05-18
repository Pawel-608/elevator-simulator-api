package rainer.pawel.elevator.system.infrastructure.building.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import rainer.pawel.elevator.system.application.BuildingService;
import rainer.pawel.elevator.system.application.command.CreateBuildingCommand;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.BuildingRepository;
import rainer.pawel.elevator.system.domain.building.elevator.ElevatorInfo;
import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;
import rainer.pawel.elevator.system.infrastructure.building.controller.document.inbound.CreateBuildingDocument;
import rainer.pawel.elevator.system.infrastructure.building.controller.document.outbound.BuildingDocument;
import rainer.pawel.elevator.system.infrastructure.building.controller.document.outbound.DetailedBuildingDocument;


import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@Tag(name = "Building controller")
public class BuildingController {

    private final BuildingService buildingService;

    private final BuildingRepository buildingRepository;

    @Operation(summary = "Create a new building")
    @PostMapping("/buildings")
    @ResponseStatus(OK)
    Id createBuilding(@RequestBody CreateBuildingDocument createBuildingDocument) {
        CreateBuildingCommand command = createBuildingDocument.toCommand();

        return buildingService.createBuilding(command);
    }

    @Operation(summary = "Get all buildings ids")
    @GetMapping("/buildings")
    @ResponseStatus(OK)
    List<Id> getBuildings() {
        return buildingRepository.findAll()
                .stream()
                .map(Building::getId)
                .toList();
    }

    @Operation(summary = "Get basic building information")
    @GetMapping("/buildings/{building-id}/overview")
    @ResponseStatus(OK)
    BuildingDocument getBuilding(@PathVariable("building-id") Id buildingId) {
        Building building = buildingRepository.find(buildingId)
                .orElseThrow(() -> ElevatorSystemException.of("Cannot find building %s", buildingId));

        return new BuildingDocument(
                buildingId,
                building.getElevatorsInfo().elevatorsInfo().stream().map(ElevatorInfo::id).toList()
        );
    }

    @Operation(summary = "Get detailed building information")
    @GetMapping("/buildings/{building-id}/details")
    @ResponseStatus(OK)
    DetailedBuildingDocument getElevators(@PathVariable("building-id") Id buildingId) {
        return buildingRepository.find(buildingId)
                .map(Building::getElevatorsInfo)
                .map(elevatorsInfo -> DetailedBuildingDocument.from(buildingId, elevatorsInfo))
                .orElseThrow(() -> ElevatorSystemException.of("Cannot find building %s", buildingId));
    }
}
