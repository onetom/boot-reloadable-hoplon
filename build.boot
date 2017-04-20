(require '[boot.core :refer :all]                           ; IntelliJ "integration"
         '[boot.task.built-in :refer :all])

(task-options!
  pom {:project     'boot-reloadable-hoplon
       :version     "1.0"
       :description "Boot reload js-onlode issue"
       :license     {"EPL" "http://www.eclipse.org/legal/epl-v10.html"}})

(set-env!
  :dependencies '[[org.clojure/clojure "1.9.0-alpha15" :scope "compile"]
                  [org.clojure/clojurescript "1.9.521" :scope "compile"]
                  [onetom/boot-lein-generate "0.1.3" :scope "test"]
                  [boot/core "2.7.1" :scope "compile"]
                  [hoplon "7.0.0-SNAPSHOT" :scope "compile"]
                  [adzerk/boot-cljs "2.0.0" :scope "compile"]
                  [adzerk/boot-reload "0.5.1" :scope "compile"]
                  [tailrecursion/boot-static "0.1.0"]
                  [binaryage/devtools "RELEASE"]]
  :source-paths #{"src"})

(require 'boot.lein)
(boot.lein/generate)

(require
  '[adzerk.boot-reload :refer [reload]]
  '[hoplon.boot-hoplon :refer [hoplon prerender]]
  '[adzerk.boot-cljs :refer [cljs]]
  '[tailrecursion.boot-static :refer [serve]])

(deftask dev []
  (comp
    (watch)
    (hoplon)
    (reload :on-jsload 'page.index/reload)
    (cljs :compiler-options {:parallel-build true})
    (serve :port 8000)))
