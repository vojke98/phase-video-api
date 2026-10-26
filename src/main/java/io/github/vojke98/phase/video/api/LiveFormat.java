package io.github.vojke98.phase.video.api;

/** How live video can reach a browser - every browser plays these (HLS through the web app's player). */
public enum LiveFormat {
    /** HTTP Live Streaming: a playlist of short segments - works everywhere, a few seconds behind. */
    HLS,
    /** Motion JPEG ({@code multipart/x-mixed-replace}): a picture after picture - no delay, more data. */
    MJPEG,
    /** A video file or an endless fragmented MP4 stream ({@code video/mp4}, {@code video/webm}). */
    MP4,
    /** A still picture, asked for again every few seconds - for sources that offer nothing else. */
    JPEG
}
