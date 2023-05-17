package rainer.pawel.elevator.system.domain.building.elevator;

import lombok.RequiredArgsConstructor;

import rainer.pawel.elevator.system.domain.TimeUnit;


import static rainer.pawel.elevator.system.domain.TimeUnit.ZERO;

@RequiredArgsConstructor
public class ReturningElevator extends DirectionBasedElevator {

    private final int baseFloor;

    private final TimeUnit maxTimeToBeInactive;

    private TimeUnit idleTime = ZERO;

    @Override
    public void makeStep() {
        super.makeStep();

        if (isIdle()) {
            idleTime = idleTime.increment();

            if (idleTime.isGrater(maxTimeToBeInactive)) {
                idleTime = ZERO;

                callToBaseFloor();
            }
        }
    }

    private void callToBaseFloor() {
        building.callElevator(getId(), baseFloor);
    }
}
