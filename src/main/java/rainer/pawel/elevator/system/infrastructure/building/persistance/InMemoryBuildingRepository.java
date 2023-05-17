package rainer.pawel.elevator.system.infrastructure.building.persistance;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.BuildingRepository;

@Repository
public class InMemoryBuildingRepository implements BuildingRepository {

    private final HashMap<Id, Building> buildings = new HashMap<>();

    @Override
    public void save(Building building) {
        buildings.put(building.getId(), building);
    }

    @Override
    public Optional<Building> find(Id id) {
        return Optional.ofNullable(
                buildings.get(id)
        );
    }

    @Override
    public List<Building> findAll() {
        return buildings.values()
                .stream()
                .toList();
    }
}
