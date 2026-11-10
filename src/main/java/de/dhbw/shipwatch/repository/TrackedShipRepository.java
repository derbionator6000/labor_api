package de.dhbw.shipwatch.repository;

import de.dhbw.shipwatch.model.TrackedShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrackedShipRepository extends JpaRepository<TrackedShip, Long> {

    List<TrackedShip> findByDangerousCargo(boolean dangerousCargo);

    List<TrackedShip> findByNameContainingIgnoreCase(String namePart);

    @Query("SELECT s FROM TrackedShip s WHERE s.lengthM > :minLength")
    List<TrackedShip> findLongerThan(double minLength);
}
