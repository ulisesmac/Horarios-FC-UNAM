(ns horarios-fc.screens.pick-plan
  (:require
   [reagent.core :as r]
   [react-native :as rn]))

(defn screen* []
  [rn/view
   [rn/text "a"]])

(defn screen []
  (r/as-element [screen*]))
