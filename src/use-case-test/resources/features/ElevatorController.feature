Feature: Elevators are called on the basis of the cost of the call

  Background:
    Given There is a building with 100 floors and 2 elevators

  Scenario: Person on corridor elevator wants to go up
    Given Elevator no. 1 is on floor 10
    When There is a call to go from 15 to 20 floor
    When There is a call to go from 17 to 22 floor
    And Make 5 steps
    Then Elevator no. 1 should be on floor 15 in the WAITING_ON_FLOOR state
    Then Elevator no. 2 should be on floor 0 in the IDLE state

  Scenario: Person on corridor elevator wants to go down
    Given Elevator no. 1 is on floor 10
    When There is a call to go from 20 to 15 floor
    When There is a call to go from 22 to 17 floor
    And Make 12 steps
    Then Elevator no. 1 should be on floor 22 in the WAITING_ON_FLOOR state
    Then Elevator no. 2 should be on floor 0 in the IDLE state
    Then Elevator no. 2 should be on floor 10 in the IDLE state
