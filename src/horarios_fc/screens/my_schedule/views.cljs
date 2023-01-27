(ns horarios-fc.screens.my-schedule.views
  (:require
   [horarios-fc.colors :refer [theme-color]]
   [react-native :as rn]
   [reagent.core :as r]))

(defn screen* []
  [rn/view {:style {:flex             1
                    :justify-content  :center
                    :align-items      :center
                    :background-color (theme-color :basic-100 :basic-1000)}}
   [rn/text {:style {:font-size   24
                     :font-weight "600"
                     :color       (theme-color :warning-600 :warning-700)
                     :text-align  :center}}
    "⚠ 💻 En progreso ... 🛠 ⚠"]])

(defn screen []
  (r/as-element [screen*]))
