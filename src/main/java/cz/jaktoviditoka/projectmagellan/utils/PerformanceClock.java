package cz.jaktoviditoka.projectmagellan.utils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class PerformanceClock {

    Instant check;

    public PerformanceClock() {
        check = Instant.now();
    }

    public Duration check() {
        Instant newCheck = Instant.now();
        Duration duration = Duration.between(check, newCheck);
        check = newCheck;
        return duration;
    }

}
