package rainer.pawel.elevator.system.infrastructure.building.controller.document.inbound;

import io.swagger.v3.oas.annotations.media.Schema;
import rainer.pawel.elevator.system.application.CreateBuildingCommand;

import lombok.Getter;
import lombok.Setter;


import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
public final class CreateBuildingDocument {

    @Schema(requiredMode = REQUIRED)
    private int floorsNumber;

    @Schema(requiredMode = REQUIRED)
    private int elevatorsNumber;

    @Schema(requiredMode = NOT_REQUIRED, description = "Elevators will return to this floor after some time")
    private int elevatorsBaseFloorNumber = 0;

    @Schema(requiredMode = NOT_REQUIRED, description = "If elevator will be inactive for more than this time, it will return to base floor")
    private int elevatorsMaxTimeToBeInactive = 10;

    public CreateBuildingCommand toCommand() {
        return new CreateBuildingCommand(floorsNumber, elevatorsNumber, elevatorsBaseFloorNumber, elevatorsMaxTimeToBeInactive);
    }
}
