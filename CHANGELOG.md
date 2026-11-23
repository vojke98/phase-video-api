# Changelog: Phase video API

What changed in each release of the video API (`io.github.vojke98:phase-video-api`). The newest release is at the top.
Format: [Keep a Changelog](https://keepachangelog.com/en/1.1.0/); versions: [Semantic Versioning](https://semver.org/) - a
release that a plugin built against an older one would not work with is a MAJOR version, and raises `VideoApi.VERSION`.

## [Unreleased]

## [1.0.0]

### Added

- The first release: `VideoSourceProvider` (a kind of video source: its settings, `create`) and `VideoSource` (one source: its
  cameras, live video, a picture, moving a camera, recordings and playback), with `Camera`, `Capabilities`, `LiveFormat`,
  `Media`, `Ptz`, `PtzCommand` and `Recording`. `VideoApi.VERSION` is 1.
