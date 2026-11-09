package io.github.vojke98.phase.video.api;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.List;

/**
 * One video source: a recorder, a video management system, a camera - with the cameras it has. Unlike a device driver, it is
 * called from many threads at once (every person watching): it must be thread-safe.
 * <p>
 * Video reaches the browser through the Phase server, never straight from the source: the source's address and its credentials
 * stay on the server, and a person sees only the cameras they may see. What a source returns is therefore a {@link Media} the
 * server passes on. A playlist ({@code application/vnd.apple.mpegurl}) names further resources; the server asks for each through
 * {@link #fetch(String, URI)} - with its absolute URI, resolved against {@link Media#location()}.
 * <p>
 * What a camera can do is in its {@link Capabilities}; the methods for what it cannot do are never called. An {@link IOException}
 * means the source did not answer; {@link IllegalArgumentException} that it answered, and refused (a camera it does not have, a time
 * it has no recording of) - the message is shown.
 */
public interface VideoSource extends AutoCloseable {

    /** The cameras there are, with what each can do. The server asks again now and then (and on request): cameras come and go. */
    List<Camera> cameras() throws IOException;

    /** The camera's live video, in one of the formats its {@link Capabilities#live()} lists. */
    Media live(String camera, LiveFormat format) throws IOException;

    /**
     * The camera's live video from one of its streams: {@link Stream#SUB} is asked for only when {@link Capabilities#substream()}.
     * Without one, the full stream ({@link #live(String, LiveFormat)}).
     */
    default Media live(String camera, LiveFormat format, Stream stream) throws IOException {
        return live(camera, format);
    }

    /**
     * A resource a playlist of this source named (a segment, another playlist). Fetch only what belongs to this source - an URI on
     * another server is refused ({@link IllegalArgumentException}): the server would otherwise fetch anything for anybody.
     */
    default Media fetch(String camera, URI uri) throws IOException {
        throw new UnsupportedOperationException("This source has no playlists");
    }

    /** A still picture of the camera now (JPEG). Only when {@link Capabilities#snapshot()}. */
    default Media snapshot(String camera) throws IOException {
        throw new UnsupportedOperationException("This source has no snapshots");
    }

    /** Moves the camera. Only when {@link Capabilities#ptz()} is there - and only what it says (pan/tilt, zoom, presets). */
    default void ptz(String camera, PtzCommand command) throws IOException {
        throw new UnsupportedOperationException("This camera cannot move");
    }

    /** What is recorded of the camera between {@code from} and {@code to}, earliest first. Only when {@link Capabilities#playback()}. */
    default List<Recording> recordings(String camera, Instant from, Instant to) throws IOException {
        throw new UnsupportedOperationException("This source has no recordings");
    }

    /** The recording from {@code from} to {@code to}, as an HLS playlist. Only when {@link Capabilities#playback()}. */
    default Media playback(String camera, Instant from, Instant to) throws IOException {
        throw new UnsupportedOperationException("This source has no recordings");
    }

    /** The source is no longer used (it was changed, removed, or its plugin replaced). Never throws. */
    @Override
    void close();
}
