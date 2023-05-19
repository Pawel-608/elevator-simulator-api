package rainer.pawel.elevator.system.infrastructure.persistance;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import rainer.pawel.elevator.system.domain.Id;
import rainer.pawel.elevator.system.domain.building.Building;
import rainer.pawel.elevator.system.domain.building.BuildingRepository;

@Repository
public class InMemoryBuildingRepository implements BuildingRepository {

    private final Map<Id, Building> buildings = new ConcurrentHashMap<>();

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
