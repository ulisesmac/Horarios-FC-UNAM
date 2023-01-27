(ns horarios-fc.screens.pick-plan.style
  (:require
   [horarios-fc.colors :refer [alpha theme-color]]))

(defn container []
  {:position           :relative
   :flex               1
   :padding-horizontal 16
   :padding-top        12
   :justify-content    :center
   :background-color   (theme-color :basic-100 :basic-1000)})

;; top pannel
(defn top-panel []
  {:flex                1
   :justify-content     :center
   :align-items         :center
   :row-gap             6
   :border-bottom-width 0.6
   :border-bottom-color (theme-color :basic-400 :basic-700)})

(def major-image {:width 120, :height 120})
(defn major-text []
  {:text-align  :center
   :font-size   26
   :font-weight "500"
   :color       (theme-color :primary-700 :primary-600)})

;; bottom panel
(def bottom-panel {:flex 2})
(def plan-listing {:flex               1
                   :row-gap            18
                   :padding-vertical   12
                   :padding-horizontal 8
                   :justify-content    :center})

(def plan-button-radius {:border-radius 20})

(defn plan-button []
  {:background-color   (theme-color :primary-500 :primary-1000)
   :border-radius      16
   :justify-content    :center
   :align-items        :center
   :padding-horizontal 24
   :padding-vertical   18})

(defn plan-button-text []
  {:color       (theme-color :basic-100 :primary-100)
   :font-weight "500"
   :font-size   16})
