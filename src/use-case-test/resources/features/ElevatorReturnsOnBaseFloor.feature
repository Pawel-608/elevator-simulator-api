Feature: Elevator will return to base floor if is idle for enough time

  Scenario: Elevator will automatically return on base floor
    Given There is a building with 100 floors and 1 elevators. Elevators base floor is 0 and max time to be inactive is 10
    And Elevator no. 1 is on floor 10
    And Elevator no. 1 should be in the IDLE state
    Then Make 10 steps
    Then Elevator no. 1 should be on floor 10 in the BUSY state
    Then Make 10 steps
    Then Elevator no. 1 should be on floor 0