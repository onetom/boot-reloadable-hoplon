(ns ^{:hoplon/page "index.html"} page.index
  (:require
    [javelin.core :as j :refer [cell] :refer-macros [cell= defc defc=]]
    [hoplon.core :as h :refer [defelem when-tpl if-tpl case-tpl for-tpl]]
    [hoplon.jquery]))

(defc data [1 "2" :3])

(defn app []
  (list
    (h/h1 "Reloadable page")
    (h/p (cell= (pr-str data)))))

(defonce
  _first_load_
  (do
    (js/console.log "First load...")
    (h/html
      (h/head
        (h/html-meta :charset "utf-8")
        (h/html-meta :name "viewport"
                     :content "width=device-width, initial-scale=1"))
      (h/body (app)))))

(defn reload []
  (js/console.log "After reloading...")
  ; h/body is a singleton DOM element constructor, hence
  ; multiple calls to it simply replace its children
  (h/body (app)))
