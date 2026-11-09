package io.github.vojke98.phase.video.api;

/**
 * One camera of a source.
 *
 * @param id           the source's own id for it ({@link VideoApi#CAMERA_ID_PATTERN}): what Phase stores - keep it the same as long
 *                     as it is the same camera
 * @param name         the name people see
 * @param capabilities what it can do
 */
public record Camera(String id, String name, Capabilities capabilities) {

    public Camera {
        if (!VideoApi.isCameraId(id)) throw new IllegalArgumentException("'" + id + "' is not a camera id (" + VideoApi.CAMERA_ID_PATTERN + ")");
        if (name == null || name.isBlank()) name = id;
        if (capabilities == null) throw new IllegalArgumentException("Camera " + id + " needs its capabilities");
    }
}
