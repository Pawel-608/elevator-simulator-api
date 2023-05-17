Feature: Elevator can handle calls from corridor panel

  Background:
    Given There is a building with 100 floors and 1 elevators

  Scenario: Person in a corridor below elevator wants to go up
    Given Elevator no. 1 is on floor 0
    When There is a call to go from 3 to 5 floor
    And Make 3 steps
    Then Elevator no. 1 should be on floor 3 in the WAITING_ON_FLOOR state
    And Wait on floor
    And Make 2 steps
    Then Elevator no. 1 should be on floor 5 in the WAITING_ON_FLOOR state

  Scenario: Person in a corridor above elevator wants to go up
    Given Elevator no. 1 is on floor 10
    When There is a call to go from 3 to 5 floor
    And Make 7 steps
    Then Elevator no. 1 should be on floor 3 in the WAITING_ON_FLOOR state
    And Wait on floor
    And Make 2 steps
    Then Elevator no. 1 should be on floor 5 in the WAITING_ON_FLOOR state


  Scenario: Person in a corridor below elevator wants to go down
    Given Elevator no. 1 is on floor 2
    When There is a call to go from 10 to 5 floor
    And Make 8 steps
    Then Elevator no. 1 should be on floor 10 in the WAITING_ON_FLOOR state
    And Wait on floor
    And Make 5 steps
    Then Elevator no. 1 should be on floor 5 in the WAITING_ON_FLOOR state

  Scenario: Person in a corridor above elevator wants to go down
    Given Elevator no. 1 is on floor 13
    When There is a call to go from 10 to 5 floor
    And Make 3 steps
    Then Elevator no. 1 should be on floor 10 in the WAITING_ON_FLOOR state
    And Wait on floor
    And Make 5 steps
    Then Elevator no. 1 should be on floor 5 in the WAITING_ON_FLOOR state



