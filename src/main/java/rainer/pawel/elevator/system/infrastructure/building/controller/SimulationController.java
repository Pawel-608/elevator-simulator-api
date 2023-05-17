package rainer.pawel.elevator.system.infrastructure.building.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;


import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequiredArgsConstructor
@RestController
@Tag(name = "Simulation controller")
public class SimulationController {

    @Operation(summary = "Make a step")
    @PostMapping("/step")
    @ResponseStatus(NO_CONTENT)
    void makeStep() {

    }
}
