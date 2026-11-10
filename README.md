# ShipWatch – Labor API

Name: Robin von Bardeleben

Kleine Spring-Boot-Anwendung, mit der man eine Beobachtungsliste von Schiffen führt
und Sichtungen dazu erfasst. Live-Daten kommen aus der offenen Digitraffic-AIS-API.

## Starten

```
./mvnw spring-boot:run
```

- Frontend: http://localhost:8080
- H2-Konsole: http://localhost:8080/h2-console (JDBC-URL `jdbc:h2:mem:shipwatch`, User `sa`, kein Passwort)
- API-Tests: `src/test/resources/api-tests.http` (IntelliJ HTTP Client)

## Endpoints

| Methode | Pfad | Beschreibung |
|---|---|---|
| GET | `/api/tracked-ships` | alle Schiffe, optional `?dangerousCargo=true` |
| GET | `/api/tracked-ships/{id}` | ein Schiff |
| GET | `/api/tracked-ships/search?name=` | Suche nach Namen |
| GET | `/api/tracked-ships/large` | Schiffe länger als 200 m |
| POST | `/api/tracked-ships` | Schiff anlegen (201 + Location) |
| PUT | `/api/tracked-ships/{id}` | Schiff ersetzen |
| PATCH | `/api/tracked-ships/{id}/dangerous-cargo` | Gefahrgut umschalten |
| DELETE | `/api/tracked-ships/{id}` | Schiff samt Sichtungen löschen (204) |
| GET/POST | `/api/tracked-ships/{id}/sightings` | Sichtungen lesen / anlegen |
| GET | `/api/ships`, `/api/ships/{mmsi}`, `/api/ships/count` | Live-AIS-Daten (Digitraffic) |
| GET | `/api/health`, `/api/info` | Status |

## Aufbau

Controller → Service → Repository (Spring Data JPA) → H2. Die API arbeitet mit DTOs
(`dto/`), das Mapping auf die Entities liegt in `mapper/`.
