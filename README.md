# Building elevator simulator

This application allows you to create building elevators simulations

## How to run application locally

Just run:
`mvn spring-boot:run`

and go to `http://localhost:8080` (api is documented using swagger)


**For now (without ui) probably the best way to interact with application is to write your own use-case-test ;)**

## About

### Design

After asking some additional questions I came with design like this:

- There is a building with `1-16` elevators
- On each floor of this building there is elevator control panel that allows you to request to go to x floor
- In each elevator there is additional panel that allows you to go to chosen floor
- When elevator stops on a floor it shows direction it will go to (`UP`/`DOWN`)

### Interface:

Building:
- Create building
- Get info about building (states of all elevators) | args: `building_id`

Elevators:
- Make a request from corridor panel | args: `building_id`, `from_floor`, `to_floor`
- Make a request from elevator panel | args: `building_id`, `elevator_id`, `to_floor`

Simulation:
- Make a step of simulation | args: building id

Detailed description of api is available after running an app and going to http://localhost:8080

### Algorithm

Elevators are working based on algorithm (simplified version):

- We have an elevator
- Elevator is going up
- It cannot handle requests to go down until it will complete all current requests
- It can handle calls to go up if current elevator floor <= floor/floors from request
- Elevator to complete request is chosen by calculating cost of a request
- Requests that cannot be completed by any elevator are stored in a common pool and the system will try to assign them to an elevator before executing each step

### System "low level" details

Now we keep all simulations states in memory what was the simplest approach, but the way system is designed allows to save
state in db without bigger problems (probably document-based no-sql db would be the best fit here as there is a lot of
dynamically changing many-to-one relations so sql would not be very handy here). Other way of storing state, that is
worth considering is event sourcing as it would allow to replay simulation to some point in time

Application is using onion architecture and domain is well-tested with use of unit and use-case tests. Unfortunately I had no
time to write integration tests so there is ony one "smoke test". There is also test for application architecture.

### Comment after some hours of sleep ;)

I think that idea behind my design was really idealistic - I wanted to focus on elevator "customer"
and allow him to go from floor x to y in the shortest possible time - that's why "corridor panel"
isn't just a simple go `up`/`down` button

It turned out that I did not take the time factor into account and actual algorithm I was able to create is far from ideal -
I think I probably should stay with much simpler `up`/`down` panel as it would allow me to deliver 100% complete system

Also, application is not thread safe (no thread safety + web interface = problem) what could cause moving simulation
to invalid state

### What I would like to do to improve this system

Request from corridor should look like: `building_id`, `from_floor`, `direction`

Elevator should be referenced by its "index" in building, not its uuid

To provide thread safety and persistence I would use message queue like kafka:
- Receive http request
- Produce event to kafka with key `building_id`
- Consume events (one for building at the time)
- Save event to db (here sql might work)

But it is not trivial topic ;) Kafka probably would be overkill here, so I would need to check simpler
solutions that would allow to process one event for building at the time (Maybe Spring events + synchronous listener? 
but it will process one event at the time not one for building)