(ns horarios-fc.splash-screen
  (:require
   ["react" :refer [useRef]]
   [horarios-fc.colors :refer [theme-color]]
   [re-frame.core :as rf]
   [react-native :as rn]))

(rf/reg-event-db
 ::unmount-splash-screen
 (fn [db]
   (assoc db :splash-screen-mounted? false)))

(rf/reg-sub
 :splash-screen-mounted?
 (fn [db]
   (get db :splash-screen-mounted? true)))

(defonce splash-image (js/require "../resources/icons/app/splash.png"))

(defn splash [loading?]
  (let [app-ready?   (not loading?)
        anim         (.-current (useRef (rn/animated-value 0)))
        start-anim!  #(rn/start-animated-timing anim {:toValue         100
                                                      :duration        350
                                                      :useNativeDriver false})
        bottom-style (.interpolate anim #js{:inputRange  #js[0 100]
                                            :outputRange #js["0%" "100%"]})
        top-style    (.interpolate anim #js{:inputRange  #js[0 100]
                                            :outputRange #js["0%" "-100%"]})
        _            (when app-ready?
                       (js/setTimeout start-anim! 650)
                       (js/setTimeout #(rf/dispatch [::unmount-splash-screen]) 1400))]
    [rn/animated-view {:style {:flex               1
                               :z-index            1
                               :position           :absolute
                               :width              "100%"
                               :background-color   (theme-color :basic-100 :basic-1100)
                               :justify-content    :center
                               :align-items        :center
                               :top                top-style
                               :bottom             bottom-style
                               :padding-horizontal 15}}
     [rn/image {:style  {:flex        1
                         :resize-mode :contain
                         :width       "100%"}
                :source splash-image}]]))
