package de.dhbw.shipwatch.repository;

import de.dhbw.shipwatch.model.Sighting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SightingRepository extends JpaRepository<Sighting, Long> {

    List<Sighting> findByTrackedShipIdOrderByObservedAtDesc(Long trackedShipId);

    // nur finden, wenn die Sichtung auch zu diesem Schiff gehört
    Optional<Sighting> findByIdAndTrackedShipId(Long id, Long trackedShipId);
}
