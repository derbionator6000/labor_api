package de.dhbw.shipwatch.service;

import de.dhbw.shipwatch.model.TrackedShip;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TrackedShipService {

    private final ConcurrentHashMap<Long, TrackedShip> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public TrackedShipService() {
        // Beispieldaten für die Entwicklung
        create(new TrackedShip(null, 230629000L, "Viking Grace", "Passenger", 218.0, false, "Stockholm"));
        create(new TrackedShip(null, 230983000L, "Sten Ingrid", "Tanker", 144.0, true, "Porvoo"));
        create(new TrackedShip(null, 230145000L, "Finnmaid", "Ro-Ro Cargo", 219.0, false, "Travemünde"));
    }

    public List<TrackedShip> findAll() {
        return List.copyOf(store.values());
    }

    public List<TrackedShip> findByDangerousCargo(boolean dangerousCargo) {
        return store.values().stream()
            .filter(ship -> ship.isDangerousCargo() == dangerousCargo)
            .toList();
    }

    public Optional<TrackedShip> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public TrackedShip create(TrackedShip entity) {
        Long newId = idGenerator.getAndIncrement();
        entity.setId(newId);
        store.put(newId, entity);
        return entity;
    }

    public Optional<TrackedShip> update(Long id, TrackedShip entity) {
        if (!store.containsKey(id)) {
            return Optional.empty();
        }
        entity.setId(id);
        store.put(id, entity);
        return Optional.of(entity);
    }

    public Optional<TrackedShip> toggleDangerousCargo(Long id) {
        return findById(id).map(ship -> {
            ship.setDangerousCargo(!ship.isDangerousCargo());
            return ship;
        });
    }

    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}
