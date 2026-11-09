package io.github.vojke98.phase.video.api;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Locale;

/**
 * Video, a picture or a playlist, as the server passes it on to the browser. The server closes it - also when the person stops
 * watching halfway through a stream that never ends (MJPEG).
 *
 * @param contentType e.g. {@code multipart/x-mixed-replace;boundary=frame}, {@code image/jpeg}, {@code video/mp4},
 *                    {@code application/vnd.apple.mpegurl}
 * @param body        what to pass on; for a stream, it goes on as long as the person watches
 * @param length      its length in bytes, or -1 when not known (a stream)
 * @param location    where it came from (absolute): the URIs a playlist names are resolved against it; {@code null} for anything
 *                    that is not a playlist
 */
public record Media(String contentType, InputStream body, long length, URI location) implements Closeable {

    public static final String HLS_PLAYLIST = "application/vnd.apple.mpegurl";

    public Media {
        if (contentType == null || contentType.isBlank()) throw new IllegalArgumentException("Media needs a content type");
        if (body == null) throw new IllegalArgumentException("Media needs a body");
    }

    public boolean isPlaylist() {
        String type = contentType.toLowerCase(Locale.ROOT);
        return type.startsWith(HLS_PLAYLIST) || type.startsWith("application/x-mpegurl") || type.startsWith("audio/mpegurl");
    }

    @Override
    public void close() throws IOException {
        body.close();
    }
}
