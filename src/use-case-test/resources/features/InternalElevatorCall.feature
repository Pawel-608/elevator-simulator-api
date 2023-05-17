Feature: Elevator can handle calls from inside panel

  Background:
    Given There is a building with 100 floors and 1 elevators

  Scenario: Person in elevator wants to go up
    When There is a call to elevator no. 1 to go to floor 5
    And Make 5 steps
    Then Elevator no. 1 should be on floor 5 in the WAITING_ON_FLOOR state

  Scenario: Person in elevator wants to go down
    Given Elevator no. 1 is on floor 5
    When There is a call to elevator no. 1 to go to floor 2
    And Make 3 steps
    Then Elevator no. 1 should be on floor 2 in the WAITING_ON_FLOOR state
