package de.dhbw.shipwatch.service;

import de.dhbw.shipwatch.exception.TrackedShipNotFoundException;
import de.dhbw.shipwatch.model.TrackedShip;
import de.dhbw.shipwatch.repository.TrackedShipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackedShipService {

    // ab dieser Länge (Meter) gilt ein Schiff als "groß"
    public static final double LARGE_SHIP_LENGTH_M = 200.0;

    private final TrackedShipRepository repository;

    public TrackedShipService(TrackedShipRepository repository) {
        this.repository = repository;
    }

    public List<TrackedShip> findAll() {
        return repository.findAll();
    }

    public List<TrackedShip> findByDangerousCargo(boolean dangerousCargo) {
        return repository.findByDangerousCargo(dangerousCargo);
    }

    public List<TrackedShip> searchByName(String namePart) {
        return repository.findByNameContainingIgnoreCase(namePart);
    }

    public List<TrackedShip> findLarge() {
        return repository.findLongerThan(LARGE_SHIP_LENGTH_M);
    }

    public TrackedShip getOrThrow(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new TrackedShipNotFoundException(id));
    }

    public TrackedShip create(TrackedShip entity) {
        return repository.save(entity);
    }

    public TrackedShip update(Long id, TrackedShip updated) {
        TrackedShip existing = getOrThrow(id);
        existing.setMmsi(updated.getMmsi());
        existing.setName(updated.getName());
        existing.setShipType(updated.getShipType());
        existing.setLengthM(updated.getLengthM());
        existing.setDangerousCargo(updated.isDangerousCargo());
        existing.setDestination(updated.getDestination());
        return repository.save(existing);
    }

    public TrackedShip toggleDangerousCargo(Long id) {
        TrackedShip ship = getOrThrow(id);
        ship.setDangerousCargo(!ship.isDangerousCargo());
        return repository.save(ship);
    }

    // Sichtungen werden dank cascade = ALL / orphanRemoval mitgelöscht
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new TrackedShipNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
