package rainer.pawel.elevator.system.domain.building.elevator

import spock.lang.Specification

import rainer.pawel.elevator.system.domain.Floor
import rainer.pawel.elevator.system.domain.TimeUnit
import rainer.pawel.elevator.system.domain.building.Building
import rainer.pawel.elevator.system.domain.exception.ElevatorSystemException


import static rainer.pawel.elevator.system.domain.TestData.floor
import static rainer.pawel.elevator.system.domain.TestData.getElevatorCall

class DirectionBasedElevatorSpec extends Specification {

    Building building = Stub()

    Elevator elevator = new DirectionBasedElevator()

    void setup() {
        building.getFloor(_) >> { arguments -> return floor(arguments[0]) }
        building.getHigherFloor(_) >> { arguments -> return floor(((Floor) arguments[0]).floorNumber() + 1) }
        building.getLowerFloor(_) >> { arguments -> return floor(((Floor) arguments[0]).floorNumber() - 1) }

        elevator.assignToBuilding(building)
    }

    def "should calculate call cost"() {
        when:
            moveElevatorToFloor(elevatorFloor)

            calls.forEach {
                {
                    elevator.handleCall(it)
                }
            }
        then:
            expectedCost == elevator.calculateCallCost(callToCheckCostOf)
        where:
            elevatorFloor | calls                 | callToCheckCostOf      | expectedCost
            0             | []                    | getElevatorCall(0, 3)  | TimeUnit.of(2)
            0             | [floor(0)]            | getElevatorCall(0, 3)  | TimeUnit.of(1)
            0             | [floor(0), floor(3)]  | getElevatorCall(0, 3)  | TimeUnit.of(0)
            0             | []                    | getElevatorCall(2, 5)  | TimeUnit.of(4)
            0             | [floor(2)]            | getElevatorCall(2, 5)  | TimeUnit.of(3)
            0             | [floor(2), floor(5)]  | getElevatorCall(2, 5)  | TimeUnit.of(2)
            10            | []                    | getElevatorCall(10, 5) | TimeUnit.of(2)
            10            | [floor(10)]           | getElevatorCall(10, 5) | TimeUnit.of(1)
            10            | [floor(10), floor(5)] | getElevatorCall(10, 5) | TimeUnit.of(0)
            10            | []                    | getElevatorCall(8, 5)  | TimeUnit.of(4)
            10            | [floor(5)]            | getElevatorCall(8, 5)  | TimeUnit.of(3)
            10            | [floor(5), floor(8)]  | getElevatorCall(8, 5)  | TimeUnit.of(2)
    }

    def "should go up"() {
        when:
            moveElevatorToFloor(0)
            elevator.handleCall(floor(5))
            elevator.handleCall(getElevatorCall(1, 4))
        then:
            makeStep()
            assertElevatorIsOnFloor(1)
        and:
            makeStep()
            assertElevatorIsOnFloor(1)
        and:
            makeSteps(3)
            assertElevatorIsOnFloor(4)
        and:
            makeSteps(2)
            assertElevatorIsOnFloor(5)
        and:
            makeSteps(10)
            assertElevatorIsOnFloor(5)
    }

    def "should go down"() {
        when:
            moveElevatorToFloor(10)
            elevator.handleCall(floor(8))
            elevator.handleCall(getElevatorCall(7, 4))
        then:
            makeSteps(2)
            assertElevatorIsOnFloor(8)
        and:
            makeStep()
            assertElevatorIsOnFloor(8)
        and:
            makeStep()
            assertElevatorIsOnFloor(7)
        and:
            makeSteps(4)
            assertElevatorIsOnFloor(4)
        and:
            makeSteps(10)
            assertElevatorIsOnFloor(4)
    }

    def "should throw if cannot complete a call"() {
        when:
            moveElevatorToFloor(elevatorFloor)
            calls.forEach {
                {
                    elevator.handleCall(it)
                }
            }

            elevator.handleCall(invalidCall)
        then:
            thrown(ElevatorSystemException)
        where:
            elevatorFloor | calls                    | invalidCall
            0             | [getElevatorCall(1, 3)]  | getElevatorCall(5, 1)
            0             | [floor(10)]              | getElevatorCall(5, 1)
            5             | [getElevatorCall(7, 10)] | getElevatorCall(3, 4)
            5             | [floor(7)]               | getElevatorCall(3, 4)
            5             | [getElevatorCall(7, 10)] | floor(2)
            5             | [floor(7)]               | floor(2)
            0             | [getElevatorCall(10, 1)] | getElevatorCall(1, 2)
            5             | [floor(1)]               | getElevatorCall(1, 2)
            5             | [getElevatorCall(4, 2)]  | getElevatorCall(10, 7)
            5             | [floor(4)]               | getElevatorCall(10, 7)
            5             | [getElevatorCall(4, 2)]  | floor(10)
            5             | [floor(4)]               | floor(10)
    }

    void assertElevatorIsOnFloor(int floor) {
        assert elevator.getInfo().currentFloor() == Floor.of(floor)
    }

    void moveElevatorToFloor(int targetFloor) {
        if (elevator.getInfo().currentFloor() == Floor.of(targetFloor)) {
            return
        }

        elevator.handleCall(Floor.of(targetFloor))
        makeSteps(targetFloor + 1)
    }

    void makeStep() {
        makeSteps(1)
    }

    void makeSteps(int steps) {
        for (int i = 0; i < steps; i++) {
            elevator.makeStep()
        }
    }
}
