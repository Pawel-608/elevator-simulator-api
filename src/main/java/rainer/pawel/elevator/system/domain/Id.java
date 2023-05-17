package rainer.pawel.elevator.system.domain;

import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException;
import java.util.UUID;


import static rainer.pawel.elevator.system.domain.util.EnsureUtils.ensureThat;

public record Id(
        UUID id
) {

    public Id {
        ensureThat(id != null, () -> new ElevatorSystemException("id cannot be null"));
    }

    public static Id unique() {
        return new Id(UUID.randomUUID());
    }

    public static Id from(String id) {
        return new Id(UUID.fromString(id));
    }

    public String asString() {
        return id.toString();
    }
}
