package de.dhbw.shipwatch.service;

import de.dhbw.shipwatch.dto.digitraffic.Ship;
import de.dhbw.shipwatch.exception.ExternalApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Service
public class DigitrafficService {

    private static final Logger log = LoggerFactory.getLogger(DigitrafficService.class);
    private static final Duration CACHE_TTL = Duration.ofMinutes(5);

    private final RestClient digitraffic;

    // einfacher Cache: Antwort + Zeitpunkt, gültig für 5 Minuten
    private record CacheEntry(List<Ship> ships, Instant fetchedAt) {}
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    public DigitrafficService(RestClient digitrafficRestClient) {
        this.digitraffic = digitrafficRestClient;
    }

    // GET ist idempotent -> Retry ist gefahrlos (3 Versuche: 500 ms, dann 1 s Pause)
    @Retryable(retryFor = ExternalApiException.class, maxAttempts = 3,
               backoff = @Backoff(delay = 500, multiplier = 2.0))
    public List<Ship> fetchAll() {
        CacheEntry cached = cache.get("vessels");
        if (cached != null && cached.fetchedAt().plus(CACHE_TTL).isAfter(Instant.now())) {
            log.info("Digitraffic: vessels aus dem Cache");
            return cached.ships();
        }

        log.info("Digitraffic: GET /vessels");
        List<Ship> ships = call(() -> digitraffic.get()
            .uri("/vessels")
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, res) -> {
                throw new ExternalApiException("Digitraffic API error: " + res.getStatusCode());
            })
            .body(new ParameterizedTypeReference<List<Ship>>() {}));

        List<Ship> result = ships == null ? List.of() : ships;
        cache.put("vessels", new CacheEntry(result, Instant.now()));
        return result;
    }

    @Retryable(retryFor = ExternalApiException.class, maxAttempts = 3,
               backoff = @Backoff(delay = 500, multiplier = 2.0))
    public Ship fetchByMmsi(long mmsi) {
        log.info("Digitraffic: GET /vessels/{}", mmsi);
        return call(() -> digitraffic.get()
            .uri("/vessels/{mmsi}", mmsi)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, res) -> {
                throw new ExternalApiException("Digitraffic API error: " + res.getStatusCode());
            })
            .body(Ship.class));
    }

    // Netzwerkfehler (Timeout, DNS, ...) in unsere eigene Exception übersetzen
    private <T> T call(Supplier<T> request) {
        try {
            return request.get();
        } catch (RestClientException ex) {
            throw new ExternalApiException("Failed to call Digitraffic API: " + ex.getMessage(), ex);
        }
    }
}
