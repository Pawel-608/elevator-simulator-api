package rainer.pawel.elevator.system.domain.building.elevator.controller


import rainer.pawel.elevator.system.domain.Floor
import rainer.pawel.elevator.system.domain.Id
import rainer.pawel.elevator.system.domain.TimeUnit
import rainer.pawel.elevator.system.domain.building.Building
import rainer.pawel.elevator.system.domain.building.elevator.Elevator
import rainer.pawel.elevator.system.domain.building.elevator.call.ExternalElevatorCall
import rainer.pawel.elevator.system.domain.building.elevator.call.InternalElevatorCall
import spock.lang.Specification

class SchedulingElevatorControllerTest extends Specification {

    Elevator elevator1 = getElevatorMock()
    Elevator elevator2 = getElevatorMock()

    List<Elevator> elevators = [elevator1, elevator2]

    SchedulingElevatorController schedulingElevatorController = new SchedulingElevatorController(elevators)

    def "should assign elevators to a building"() {
        given:
            Building building = Mock()
        when:
            schedulingElevatorController.assignToBuilding(building)
        then:
            1 * elevator1.assignToBuilding(building)
            1 * elevator2.assignToBuilding(building)
    }

    def "should pass make step to each elevator"() {
        when:
            schedulingElevatorController.makeStep()
        then:
            1 * elevator1.makeStep()
            1 * elevator2.makeStep()
    }

    def "should pass external call to most efficient elevator"() {
        given:
            ExternalElevatorCall elevatorCall = new ExternalElevatorCall(Floor.of(1), Floor.of(5))

            elevator1.canHandleCall(elevatorCall) >> true
            elevator2.canHandleCall(elevatorCall) >> true

            elevator1.calculateCallCost(elevatorCall) >> TimeUnit.of(10)
            elevator2.calculateCallCost(elevatorCall) >> TimeUnit.of(5)
        when:
            schedulingElevatorController.registerCall(elevatorCall)
        then:
            0 * elevator1.handleCall(elevatorCall)
            1 * elevator2.handleCall(elevatorCall)
    }

    def "should pass internal call to elevator if it can handle it"() {
        given:
            Id elevatorId = elevator1.getId()
            Floor targetFloor = Floor.of(5)

            InternalElevatorCall elevatorCall = new InternalElevatorCall(elevatorId, targetFloor)

            elevator1.canHandleCall(targetFloor) >> canHandleCall
        when:
            schedulingElevatorController.registerCall(elevatorCall)
        then:
            numberOfInvocations * elevator1.handleCall(targetFloor)
        and:
            0 * elevator2.handleCall(targetFloor)
        where:
            canHandleCall | numberOfInvocations
            true          | 1
            false         | 0
    }


    def "should schedule external call while making step if it initially failed"() {
        given:
            ExternalElevatorCall elevatorCall = new ExternalElevatorCall(Floor.of(1), Floor.of(5))

            boolean canHandleCall = false

            elevator1.canHandleCall(elevatorCall) >> { args -> canHandleCall }
        when:
            schedulingElevatorController.registerCall(elevatorCall)

            0 * elevator1.handleCall(elevatorCall)

            canHandleCall = true

            schedulingElevatorController.makeStep()
        then:
            1 * elevator1.handleCall(elevatorCall)
    }

    def "should schedule internal call while making step if it initially failed"() {
        given:
            Id elevatorId = elevator1.getId()
            Floor targetFloor = Floor.of(5)

            InternalElevatorCall elevatorCall = new InternalElevatorCall(elevatorId, targetFloor)

            boolean canHandleCall = false

            elevator1.canHandleCall(targetFloor) >> { args -> canHandleCall }
        when:
            schedulingElevatorController.registerCall(elevatorCall)

            0 * elevator1.handleCall(targetFloor)

            canHandleCall = true

            schedulingElevatorController.makeStep()
        then:
            1 * elevator1.handleCall(targetFloor)
    }

    Elevator getElevatorMock() {
        Elevator elevator = Mock()

        elevator.getId() >> Id.unique()

        return elevator
    }
}
