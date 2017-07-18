# Reloadable code with Hoplon

Implementing https://github.com/bhauman/lein-figwheel#writing-reloadable-code
with Hoplon and Javelin cells using [boot-reload](https://github.com/adzerk-oss/boot-reload).

Run with
```
boot dev
```

**NOTE**: You have to pick either `dirac` or `cljs-repl` by commenting them out from the `dev` boot task in `build.boot`, because they are somehow incompatible!

## ClojureScript console in the browser using Dirac

The [Dirac 1.2.14](https://github.com/binaryage/dirac/releases/tag/v1.2.14) page links to a compatible
[Chromium 61.0.3159.0 for Mac](http://commondatastorage.googleapis.com/chromium-browser-snapshots/Mac/486995/chrome-mac.zip)
under the "Rolling DevTools" section and also links to the
[dirac-1.2.14.zip](https://github.com/binaryage/dirac/releases/download/v1.2.14/dirac-1.2.14.zip)
extension, which you can install after unzipping.

This setup ensures that neither the browser nor the extension gets updated automatically,
leaving you with system warning about version mismatches or a setup which does not work at all.



## ClojureScript console from Cursive

1. Create a Run configuration to a Remote nREPL to `localhost`, port `9009`

2. `boot dev`

3. Run the REPL config

4. Excecute `(start-repl)` in the REPL window.

The output will look something like this:

```
(start-repl)
<< started Weasel server on ws://127.0.0.1:64549 >>
<< waiting for client to connect ... Connection is ws://localhost:64549
Writing boot_cljs_repl.cljs...
```

and it should trigger a recompilation which you can see in the terminal:

```
Writing HTML files...
• index.html
Writing adzerk/boot_reload/index.cljs to connect to ws://localhost:63858...
Adding :require adzerk.boot-reload.index to index.html.cljs.edn...
Adding :require adzerk.boot-cljs-repl to index.html.cljs.edn...
Adding :preloads devtools.preload to index.html.cljs.edn...
Compiling ClojureScript...
• index.html.js
Elapsed time: 0.786 sec
```

5. Reload `http://localhost:8000` in your browser where you should see:

```
Installing CLJS DevTools 0.9.4 and enabling features :formatters :hints :async
First load...
Opened Websocket REPL connection
Reload websocket connected.
```

and in the Cursive REPL:

```
(start-repl)
<< started Weasel server on ws://127.0.0.1:64549 >>
<< waiting for client to connect ... Connection is ws://localhost:64549
Writing boot_cljs_repl.cljs...
 connected! >>
To quit, type: :cljs/quit
=> nil
```


6. You might want to use the `Switch REPL NS to current file` action, or send expressions from the [src/cljs/user.cljs](src/cljs/user.cljs) file; I'm not sure yet, what's the best approach.



## Startup times

`boot dev` reports an `Elapsed time: xxx.xxx sec` at the end of the CLJS compilation.

When only `cljs-repl` is enabled, the value will be around `18.8s`.

When only `dirac` is enabled, it will be around `26.5s`.

`cljs-devtools` can be enabled in both cases.

The JVM options I use are:

```
> echo $BOOT_JVM_OPTIONS
-Xverify:none -XX:+TieredCompilation -XX:TieredStopAtLevel=1
```

The machine these runtimes came from are an [iMac (Retina 5K, 27-inch, Late 2015), 3.2 GHz Intel Core i5, 24 GB 1867 MHz DDR3](http://www.everymac.com/systems/apple/imac/specs/imac-core-i5-3.3-27-inch-aluminum-retina-5k-late-2015-specs.html).
