package io.github.vojke98.phase.video.api;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VideoApiTest {

    @Test
    void idsFollowTheirPatterns() {
        assertTrue(VideoApi.isKindId("FRIGATE"));
        assertFalse(VideoApi.isKindId("frigate"));
        assertTrue(VideoApi.isCameraId("front_door-2.a"));
        assertFalse(VideoApi.isCameraId("front door"));
        assertFalse(VideoApi.isCameraId("a/b"));
        assertThrows(IllegalArgumentException.class, () -> new Camera("a/b", "x", Capabilities.liveOnly(LiveFormat.HLS)));
    }

    @Test
    void aCameraWithoutANameIsCalledByItsId() {
        assertEquals("gate", new Camera("gate", " ", Capabilities.liveOnly()).name());
    }

    @Test
    void movesAreSpeedsFromMinusOneToOne() {
        new PtzCommand.Move(-1, 1, 0);
        assertThrows(IllegalArgumentException.class, () -> new PtzCommand.Move(1.5, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new PtzCommand.Move(Double.NaN, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new PtzCommand.GoTo(""));
    }

    @Test
    void aPlaylistIsKnownByItsType() {
        assertTrue(media("application/vnd.apple.mpegurl").isPlaylist());
        assertTrue(media("application/x-mpegURL; charset=utf-8").isPlaylist());
        assertFalse(media("video/mp2t").isPlaylist());
    }

    @Test
    void capabilitiesAreCopied() {
        List<Ptz.Preset> presets = new java.util.ArrayList<>(List.of(new Ptz.Preset("1", "Gate")));
        Ptz ptz = new Ptz(true, false, presets);
        presets.clear();
        assertEquals(1, ptz.presets().size());
    }

    private static Media media(String type) {
        return new Media(type, new ByteArrayInputStream(new byte[0]), 0, null);
    }
}
