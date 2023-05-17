Feature: Elevator have a state

  Background:
    Given There is a building with 100 floors and 1 elevators

  Scenario: Elevator is idle
    When Nothing is happening
    Then Elevator no. 1 should be on floor 0 in the IDLE state
    Then Make 3 steps
    Then Elevator no. 1 should be on floor 0 in the IDLE state

  Scenario: Elevator is idle after completing request
    Given There is a call to elevator no. 1 to go to floor 10
    Then Make 10 steps
    Then Elevator no. 1 should be in the WAITING_ON_FLOOR state
    Then Wait on floor
    Then Elevator no. 1 should be in the IDLE state
    
  Scenario: Elevator is busy after receiving a call
    When There is a call to elevator no. 1 to go to floor 2
    Then Elevator no. 1 should be in the BUSY state

  Scenario: Elevator is busy while completing request
    When There is a call to elevator no. 1 to go to floor 10
    Then Make 5 steps
    Then Elevator no. 1 should be in the BUSY state

  Scenario: Elevator is waiting on floor after receiving a call to current floor
    When There is a call to elevator no. 1 to go to floor 0
    When Elevator no. 1 should be in the WAITING_ON_FLOOR state

  Scenario: Elevator is waiting on floor after receiving a call to a current floor
    Given Elevator no. 1 is on floor 10
    And There is a call to elevator no. 1 to go to floor 10
    Then Elevator no. 1 should be in the WAITING_ON_FLOOR state
