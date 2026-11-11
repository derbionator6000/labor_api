package de.dhbw.shipwatch.exception;

public class TrackedShipNotFoundException extends RuntimeException {
    public TrackedShipNotFoundException(Long id) {
        super("TrackedShip with id " + id + " not found");
    }
}
