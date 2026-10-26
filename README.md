# Phase video API

What a **video source plugin** for [Phase](https://github.com/vojke98/phase) (a SCADA server) implements - and nothing else of
the server. A video source is a recorder, a video management system or a camera brand; a plugin is one jar the server loads from
its plugins folder at run time, so a new kind of source needs no change to Phase itself. It may bring protocol drivers too (the
[driver API](https://github.com/vojke98/phase-driver-api)) - a recorder's state as tags.

- Coordinates: `io.github.vojke98:phase-video-api:1.0.0`, package `io.github.vojke98.phase.video.api`
- Depends on the driver API only (its `SettingsSchema`). Java 21 or newer.
- License: Apache License 2.0 - write and sell plugins of your own as you like.

## Getting it

Every release is in this repository's [Releases](https://github.com/vojke98/phase-video-api/releases), and in its `maven`
branch, laid out as a Maven repository:

```xml
<repositories>
    <repository>
        <id>phase-video-api</id>
        <url>https://raw.githubusercontent.com/vojke98/phase-video-api/maven</url>
    </repository>
    <repository>
        <id>phase-driver-api</id>
        <url>https://raw.githubusercontent.com/vojke98/phase-driver-api/maven</url>
    </repository>
</repositories>

<dependency>
    <groupId>io.github.vojke98</groupId>
    <artifactId>phase-video-api</artifactId>
    <version>1.0.0</version>
    <scope>provided</scope>
</dependency>
```

## Writing a video source

- **`VideoSourceProvider`** - one kind: its id (`A-Z 0-9 _`, e.g. `FRIGATE`), a name for people, the JSON Schema of a source's
  settings (built with the driver API's `SettingsSchema` - the server checks them, the web app builds the form), `apiVersion()`
  (return `VideoApi.VERSION`) and `create(settings)`.
- **`VideoSource`** - one source, called from many threads at once (everybody watching): `cameras()` with what each can do
  (`Capabilities`: live formats, a picture, `Ptz` - turning, zoom, presets -, recordings), `live(camera, format)`, `fetch(camera,
  uri)` for what a playlist names, `snapshot`, `ptz(camera, command)`, `recordings(camera, from, to)`, `playback(camera, from,
  to)` and `close()`. Only what a camera's capabilities say is ever called; the screen offers only that.

Video never goes straight from the source to the browser: the server passes each `Media` on, and turns the addresses a playlist
(HLS) names into its own - the source's address and credentials stay on the server. So `fetch` must refuse what is not the
source's own (`IllegalArgumentException`). An `IOException` means the source did not answer.

The providers are named in `META-INF/services/io.github.vojke98.phase.video.api.VideoSourceProvider`. The jar carries its own
libraries inside (`maven-shade-plugin`) and has `Implementation-Version` in its manifest, as a driver's does. The Frigate
plugin (phase-plugin-frigate) is a complete example.

**Installing it:** `sudo phase plugins add my-plugin.jar` on a Phase server. A plugin runs inside the server with its rights:
install only plugins you built or trust.

## Versions

The jar's version follows [Semantic Versioning](https://semver.org/). `VideoApi.VERSION` (now 1) is the API's generation: a
server refuses a plugin built for another one.

## Building

```bash
./mvnw verify
```

Releasing: [CHANGELOG.md](CHANGELOG.md) gets a section for the version, the version goes into `pom.xml`, then the tag
`v<version>` is pushed - GitHub builds it and publishes the release and the `maven` branch (`.github/workflows/release.yml`).
