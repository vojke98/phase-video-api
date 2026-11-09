package io.github.vojke98.phase.video.api;

import java.util.regex.Pattern;

/**
 * The version of this API, and the rules every video source plugin follows.
 * <p>
 * A plugin returns {@link #VERSION} from {@link VideoSourceProvider#apiVersion()}: the number is compiled into the plugin, so the
 * server can tell a plugin built against another version of the API and refuse it with a clear message instead of failing on a
 * missing method later. Additions that old plugins can live with (a new default method) keep the version; anything else raises it.
 */
public final class VideoApi {

    /** This API's version. */
    public static final int VERSION = 1;

    /** What a kind id looks like: upper case letters, digits and underscores, starting with a letter - e.g. {@code FRIGATE}. */
    public static final String KIND_ID_PATTERN = "[A-Z][A-Z0-9_]{0,31}";
    private static final Pattern KIND_ID = Pattern.compile(KIND_ID_PATTERN);

    /** What a camera id looks like: letters, digits, {@code _ - .}, up to 64 - e.g. {@code front_door}. */
    public static final String CAMERA_ID_PATTERN = "[A-Za-z0-9_.-]{1,64}";
    private static final Pattern CAMERA_ID = Pattern.compile(CAMERA_ID_PATTERN);

    public static boolean isKindId(String id) {
        return id != null && KIND_ID.matcher(id).matches();
    }

    public static boolean isCameraId(String id) {
        return id != null && CAMERA_ID.matcher(id).matches();
    }

    private VideoApi() {
    }
}
