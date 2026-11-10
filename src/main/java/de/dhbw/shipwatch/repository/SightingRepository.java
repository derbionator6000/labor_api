package de.dhbw.shipwatch.repository;

import de.dhbw.shipwatch.model.Sighting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SightingRepository extends JpaRepository<Sighting, Long> {

    List<Sighting> findByTrackedShipIdOrderByObservedAtDesc(Long trackedShipId);
}
