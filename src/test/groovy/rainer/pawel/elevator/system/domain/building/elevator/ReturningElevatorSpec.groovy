package rainer.pawel.elevator.system.domain.building.elevator

import spock.lang.Specification

import rainer.pawel.elevator.system.domain.TimeUnit
import rainer.pawel.elevator.system.domain.building.Building

class ReturningElevatorSpec extends Specification {

    Building building = Mock()

    def "should call to return to a base floor after being inactive given amount of time"() {
        given:
            TimeUnit maxTimeToBeInactive = TimeUnit.of(2)
            int baseFloor = 5

            ReturningElevator elevator = new ReturningElevator(baseFloor, maxTimeToBeInactive)

            elevator.assignToBuilding(building)
        when:
            makeSteps(elevator, 3)
        then:
            building.callElevator(elevator.getId(), baseFloor)
    }

    void makeSteps(Elevator elevator, int steps) {
        for (int i = 0; i < steps; i++) {
            elevator.makeStep()
        }
    }

}
