# Elevator simulator

This application allows you to simulate the behaviour of several elevators in a building

### How to run application locally

Just run:
`mvn spring-boot:run`

and go to `http://localhost:8080` (api is documented with use of swagger)

### About

Application is developed with use of onion architecture, and tested using different approaches - unit tests, use-case tests, integration-tests (unfinished ;)) and architecture tests.
For simplicity and time savings I decided to keep all simulations in memory, but domain part is designed in way that storing it in db should not be a problem
(Two approaches are interesting for me: no-sql document db (sql will not work good here, because of a lot of one-to-many relations that are changing really dynamically),
or event sourcing (it would allow to replay simulation))

Algorithm itself works like this:
- Elevator is going in one direction until it will complete all requests in that direction
- You can ask elevator how big is cost of a request
  - It is calculated based on distance from floor and number of stops between you and your target floor
- There is "elevators controller" that assigns request to "cheapest elevator"