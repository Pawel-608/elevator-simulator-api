package rainer.pawel.elevator.system.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import rainer.pawel.elevator.system.application.BuildingService;
import rainer.pawel.elevator.system.application.command.CallElevatorCommand;
import rainer.pawel.elevator.system.application.command.CallSpecificElevatorCommand;
import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.infrastructure.controller.document.inbound.CallElevatorDocument;
import rainer.pawel.elevator.system.infrastructure.controller.document.inbound.CallSpecificElevatorDocument;


import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@Tag(name = "Elevator controller")
public class ElevatorController {

    private final BuildingService buildingService;

    @Operation(summary = "Call elevator from corridor panel")
    @PostMapping("/buildings/{building-id}/elevators/call")
    @ResponseStatus(NO_CONTENT)
    void callElevator(
            @PathVariable("building-id") Id buildingId,
            @RequestBody CallElevatorDocument callElevatorDocument
    ) {
        CallElevatorCommand command = new CallElevatorCommand(buildingId, callElevatorDocument.fromFloor(), callElevatorDocument.toFloor());

        buildingService.callElevator(command);
    }

    @Operation(summary = "Call elevator from internal panel")
    @PostMapping("/buildings/{building-id}/elevators/{elevator-id}/call")
    @ResponseStatus(NO_CONTENT)
    void callElevator(
            @PathVariable("building-id") Id buildingId,
            @PathVariable("elevator-id") Id elevatorId,
            @RequestBody CallSpecificElevatorDocument callElevatorDocument) {
        CallSpecificElevatorCommand command = new CallSpecificElevatorCommand(buildingId, elevatorId, callElevatorDocument.toFloor());

        buildingService.callElevator(command);
    }
}
