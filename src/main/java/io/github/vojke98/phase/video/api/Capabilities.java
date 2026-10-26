package io.github.vojke98.phase.video.api;

import java.util.List;

/**
 * What a camera can do - the screen offers only that: a camera that cannot move gets no arrows, one without recordings no timeline.
 *
 * @param live     the formats its live video comes in, the best first (none: it has no live video)
 * @param snapshot it gives a still picture ({@link VideoSource#snapshot(String)})
 * @param ptz      how it moves, or {@code null} when it cannot
 * @param playback it has recordings to watch ({@link VideoSource#recordings}, {@link VideoSource#playback})
 */
public record Capabilities(List<LiveFormat> live, boolean snapshot, Ptz ptz, boolean playback) {

    public Capabilities {
        live = live == null ? List.of() : List.copyOf(live);
    }

    /** Live video only, in these formats. */
    public static Capabilities liveOnly(LiveFormat... formats) {
        return new Capabilities(List.of(formats), false, null, false);
    }
}
