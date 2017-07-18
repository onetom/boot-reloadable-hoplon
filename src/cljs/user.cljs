(ns cljs.user
  (:require [page.index :as idx]))

(def x 123)

(comment
  (in-ns 'page.index)
  @data)
