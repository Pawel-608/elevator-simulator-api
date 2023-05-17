package rainer.pawel.elevator.system.domain.building;

import rainer.pawel.elevator.system.domain.Id;
import java.util.List;
import java.util.Optional;

public interface BuildingRepository {

    void save(Building building);

    Optional<Building> find(Id id);

    List<Building> findAll();
}
