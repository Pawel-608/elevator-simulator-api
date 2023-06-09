package rainer.pawel.elevator.system.domain.building.util;

import lombok.NoArgsConstructor;

import rainer.pawel.elevator.system.domain.ElevatorSystemContext;


import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class StepsUtils {

    public static void makeNSteps(ElevatorSystemContext context, int n) {
        for (int i = 0; i < n; i++) {
            context.getBuilding().makeStep();
        }
    }
}
