package io.github.vojke98.phase.video.api;

import java.util.List;

/**
 * How a camera moves (ONVIF PTZ, or what a recorder passes on of it).
 *
 * @param panTilt it turns left/right and up/down ({@link PtzCommand.Move} with pan and tilt)
 * @param zoom    it zooms ({@link PtzCommand.Move} with zoom)
 * @param presets the positions saved in it ({@link PtzCommand.GoTo}), in its order
 */
public record Ptz(boolean panTilt, boolean zoom, List<Preset> presets) {

    public Ptz {
        presets = presets == null ? List.of() : List.copyOf(presets);
    }

    /** A position saved in the camera: its own id, and the name people see. */
    public record Preset(String id, String name) {
    }
}
