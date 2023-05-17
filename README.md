
Algorithm issues:
1. Consider
   - Elevator is on floor 0
   - Someone on floor 100 calls elevator to go to floor 99
   - Elevator goes up
   - Someone on floor 45 calls elevator to go to floor 60
   - Elevator will not be able to complete his request (it will go to 100 than 99 than 45 than 60)