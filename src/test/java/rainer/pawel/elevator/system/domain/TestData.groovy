package rainer.pawel.elevator.system.domain

import rainer.pawel.elevator.system.domain.Floor
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall

import lombok.NoArgsConstructor


import static lombok.AccessLevel.PRIVATE

@NoArgsConstructor(access = PRIVATE)
final class TestData {

    static ExternalElevatorCall getElevatorCall(int from, int to) {
        return new ExternalElevatorCall(Floor.of(from), Floor.of(to));
    }

    static Floor floor(int floorNumber) {
        return Floor.of(floorNumber);
    }
}
