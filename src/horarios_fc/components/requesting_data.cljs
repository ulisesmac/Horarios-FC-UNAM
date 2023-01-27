(ns horarios-fc.components.requesting-data
  (:require
   [horarios-fc.colors :refer [theme]]
   [re-frame.core :as rf]
   [react-native :as rn]))

(defn requesting-data []
  (let [requesting-data? (rf/subscribe [:requesting-data?])]
    (fn []
      (when @requesting-data?
        [rn/view {:style {:position :absolute
                          :right    12
                          :top      9}}
         [rn/activity-indicator {:size  :large
                                 :color (theme :success-500)}]]))))
