package com.pp.config;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component
public class AuditingDateTimeProvider implements DateTimeProvider {
    private final Clock clock;

    AuditingDateTimeProvider(Clock clock) { this.clock = clock; }

    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(Instant.now(clock));
    }
}
