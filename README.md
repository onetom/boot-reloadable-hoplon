# Reloadable code with Hoplon

Implementing https://github.com/bhauman/lein-figwheel#writing-reloadable-code
with Hoplon and Javelin cells using [boot-reload](https://github.com/adzerk-oss/boot-reload).

Run with
```
boot dev
```

## ClojureScript console in the browser using Dirac

The [Dirac 1.2.4](https://github.com/binaryage/dirac/releases/tag/v1.2.4) page links to a compatible
[Chromium 59.0.3066.0 for Mac](http://commondatastorage.googleapis.com/chromium-browser-snapshots/Mac/463122/chrome-mac.zip)
under the "Rolling DevTools" section and also links to the
[dirac-1.2.4.zip](https://github.com/binaryage/dirac/releases/download/v1.2.4/dirac-1.2.4.zip)
extension, which you can install after unzipping.

This setup ensures that neither the browser nor the extension gets updated automatically,
leaving you with system warning about version mismatches or a setup which does not work at all.