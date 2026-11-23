package io.github.vojke98.phase.video.api;

import java.time.Instant;

/** A stretch of time a camera was recorded, without a gap. */
public record Recording(Instant start, Instant end) {

    public Recording {
        if (start == null || end == null || end.isBefore(start)) throw new IllegalArgumentException("A recording needs a start before its end");
    }
}
