package io.github.vojke98.phase.video.api;

/** What a person asked a camera to do. A move goes on until {@link Stop} (the screen sends it when the button is let go). */
public sealed interface PtzCommand {

    /**
     * Keep moving at these speeds, each from -1 to 1 (0: not that way): pan -1 left, 1 right; tilt -1 down, 1 up; zoom -1 out,
     * 1 in. A source that knows no speeds moves the way the sign says.
     */
    record Move(double pan, double tilt, double zoom) implements PtzCommand {
        public Move {
            check("pan", pan);
            check("tilt", tilt);
            check("zoom", zoom);
        }

        private static void check(String what, double speed) {
            if (!(speed >= -1 && speed <= 1)) throw new IllegalArgumentException(what + " must be from -1 to 1, not " + speed);
        }
    }

    /** Stop moving. */
    record Stop() implements PtzCommand {
    }

    /** Go to a saved position ({@link Ptz.Preset#id()}). */
    record GoTo(String preset) implements PtzCommand {
        public GoTo {
            if (preset == null || preset.isBlank()) throw new IllegalArgumentException("Which preset?");
        }
    }
}
