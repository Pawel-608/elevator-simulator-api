package rainer.pawel.elevator.system.infrastructure.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import rainer.pawel.elevator.system.application.BuildingService;
import rainer.pawel.elevator.system.domain.Id;


import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@Tag(name = "Simulation controller")
public class SimulationController {

    private final BuildingService buildingService;

    @Operation(summary = "Make a step")
    @PostMapping("/step/{building-id}")
    @ResponseStatus(NO_CONTENT)
    void makeStep(@PathVariable("building-id") Id buildingId) {
        buildingService.makeStep(buildingId);
    }
}
