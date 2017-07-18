(require '[boot.core :refer :all]                           ; IntelliJ "integration"
         '[boot.task.built-in :refer :all])

(task-options!
  pom {:project     'boot-reloadable-hoplon
       :version     "1.0"
       :description "Boot reload js-onlode issue"
       :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(set-env!
  :dependencies '[[org.clojure/clojure "1.9.0-alpha17" :scope "compile"]
                  [org.clojure/clojurescript "1.9.671" :scope "compile"]
                  [onetom/boot-lein-generate "0.1.3" :scope "test"]
                  [boot/core "2.7.1" :scope "compile"]
                  [hoplon "7.0.2" :scope "compile"]
                  [adzerk/boot-cljs "2.0.0" :scope "compile"]
                  [adzerk/boot-reload "0.5.1" :scope "compile"]
                  [tailrecursion/boot-static "0.1.0"]

                  [binaryage/devtools "0.9.4" :scope "test"]
                  ; As per https://github.com/binaryage/dirac/releases/tag/v1.2.4
                  ; Dirac 1.2.4 needs tools.nrepl 0.2.13
                  [org.clojure/tools.nrepl "0.2.13"]
                  [binaryage/dirac "1.2.14" :scope "test"]
                  [powerlaces/boot-cljs-devtools "0.2.0" :scope "test"]

                  [adzerk/boot-cljs-repl "0.3.3"]           ;; latest release
                  [com.cemerick/piggieback "0.2.1" :scope "test"]
                  [weasel "0.7.0" :scope "test"]
                  [org.clojure/tools.nrepl "0.2.12" :scope "test"]

                  [philoskim/debux "0.2.1" :scope "compile"]]

  :source-paths #{"src"})

(require 'boot.lein)
(boot.lein/generate)

(require
  '[adzerk.boot-reload :refer [reload]]
  '[hoplon.boot-hoplon :refer [hoplon prerender]]
  '[adzerk.boot-cljs :refer [cljs]]
  '[tailrecursion.boot-static :refer [serve]]
  '[powerlaces.boot-cljs-devtools :refer [cljs-devtools dirac]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]])

(def devtools-config
  {:features-to-install           [:formatters :hints :async]

   ; When the dev console is closed on initial page load,
   ; the following warning message is printed in the console:
   ;    CLJS DevTools: some custom formatters were not rendered.
   ; The following flag can disable this:
   :dont-detect-custom-formatters true})

(def dirac-config
  {:install-check-total-time-limit (* 10 1000)})

(deftask dev []
  (task-options!
    cljs {:optimizations    :none
          :compiler-options {:parallel-build  true
                             ;:source-map-timestamp true
                             ;:verbose              false
                             :external-config {:devtools/config      devtools-config
                                               :dirac.runtime/config dirac-config}}})
  (comp
    (watch)
    (notify :audible true)
    (hoplon)
    (reload :on-jsload 'page.index/reload)
    (cljs-repl :nrepl-opts {:port 9009})
    (cljs-devtools)
    ;(dirac)
    (cljs)
    (serve :port 8000)))
