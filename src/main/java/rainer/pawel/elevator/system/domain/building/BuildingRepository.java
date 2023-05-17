package rainer.pawel.elevator.system.domain.building;

import java.util.List;
import java.util.Optional;

import rainer.pawel.elevator.system.domain.Id;

public interface BuildingRepository {

    void save(Building building);

    Optional<Building> find(Id id);

    List<Building> findAll();
}
