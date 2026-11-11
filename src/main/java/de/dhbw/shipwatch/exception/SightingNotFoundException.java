package de.dhbw.shipwatch.exception;

public class SightingNotFoundException extends RuntimeException {
    public SightingNotFoundException(Long id) {
        super("Sighting with id " + id + " not found");
    }
}
