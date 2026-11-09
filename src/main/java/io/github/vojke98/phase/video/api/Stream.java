package io.github.vojke98.phase.video.api;

/**
 * Which of a camera's streams: most cameras send a full one and a smaller one beside it. A screen of many small tiles plays the
 * small ones, a camera on the whole screen the full one - when the camera has both ({@link Capabilities#substream()}).
 */
public enum Stream {
    /** the full stream: all the camera's resolution */
    MAIN,
    /** the smaller one (the camera's sub or second stream): less to send, less to decode - enough for a tile */
    SUB
}
