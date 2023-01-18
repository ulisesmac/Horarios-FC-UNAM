(ns horarios-fc.screens.pick-plan.views
  (:require
   [react-native :as rn]
   [reagent.core :as r]))

(defn screen* []
  [rn/view {:style {:display         :flex
                    :align-items     :center
                    :justify-content :center}}
   [rn/text "a"]])

(defn screen []
  (r/as-element [screen*]))
