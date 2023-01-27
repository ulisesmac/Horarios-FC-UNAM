(ns horarios-fc.splash-screen
  (:require
   ["react" :refer [useRef]]
   [horarios-fc.colors :refer [theme]]
   [re-frame.core :as rf]
   [react-native :as rn]))

(rf/reg-event-db
 ::unmount-splash-screen
 (fn [db]
   (prn "now it's not longer mounted")
   (assoc db :splash-screen-mounted? false)))

(rf/reg-sub
 :splash-screen-mounted?
 (fn [db]
   (:splash-screen-mounted? db true)))

(defn splash [loading?]
  (let [app-ready?   (not loading?)
        anim         (.-current (useRef (rn/animated-value 0)))
        start-anim!  (fn []
                       (rn/start-animated-timing anim {:toValue         100
                                                       :duration        350
                                                       :useNativeDriver false}))
        bottom-style (.interpolate anim #js{:inputRange  #js[0 100]
                                            :outputRange #js["0%" "100%"]})
        _            (when app-ready?
                       (js/setTimeout start-anim! 500)
                       (js/setTimeout #(rf/dispatch [::unmount-splash-screen]) 7000))]
    [rn/animated-view {:style {:flex             1
                               :z-index          1
                               :position         :absolute
                               :width            "100%"
                               ;:height           "100%"
                               :background-color (theme :primary-600)
                               :justify-content  :center
                               :align-items      :center
                               :bottom           bottom-style
                               :top              0}}
     [rn/text {:style {:color "#101010"}}
      "Cargando app"]]))
