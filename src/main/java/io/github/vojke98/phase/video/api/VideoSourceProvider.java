package io.github.vojke98.phase.video.api;

import io.github.vojke98.phase.driver.api.SettingsSchema;

import java.util.Map;

/**
 * A kind of video source, as a plugin brings it - a recorder such as Frigate, a video management system, a camera brand. A plugin
 * is a jar the server finds in its plugins directory: it names its providers in
 * {@code META-INF/services/io.github.vojke98.phase.video.api.VideoSourceProvider}, is built against this API alone (scope provided)
 * and carries its own libraries inside; it gets a class loader of its own, so they never clash with the server's or another
 * plugin's. One jar may bring protocol drivers too (a recorder's status as tags) - they are named in the driver API's file beside.
 */
public interface VideoSourceProvider {

    /** Return {@link VideoApi#VERSION}: compiled in, it tells the server which version of this API the plugin was built against. */
    int apiVersion();

    /** The id video sources store: {@link VideoApi#KIND_ID_PATTERN}, e.g. {@code FRIGATE}. */
    String kind();

    /** The name people see, e.g. "Frigate NVR". */
    String displayName();

    /**
     * The JSON Schema of a source's settings, built with {@link SettingsSchema} (the driver API's): the server checks the settings
     * against it and fills in the defaults; clients build their form from it. A setting marked {@code writeOnly} (a password) is
     * never shown again once saved.
     */
    Map<String, Object> settingsSchema();

    /**
     * The source for these settings. Nothing is asked of the recorder yet - the first call does it.
     *
     * @param settings checked against {@link #settingsSchema()}, with the defaults of those left out filled in: text is a String, an
     *                 integer an Integer (or a Long), a number a Double, a boolean a Boolean
     * @throws IllegalArgumentException when the settings cannot work (an address that is not one, ...) - the message is shown
     */
    VideoSource create(Map<String, Object> settings);
}
