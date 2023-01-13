(ns horarios-fc.app
  (:require
   ["react-native" :as rn]
   [horarios-fc.events]
   [horarios-fc.subs]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [shadow.react-native :refer [render-root]]))

(def styles
  ^js (-> {:container
           {:flex            1
            :backgroundColor "#fff"
            :alignItems      "center"
            :justifyContent  "center"}
           :title
           {:fontWeight "bold"
            :fontSize   24
            :color      "blue"}}
          (clj->js)
          (rn/StyleSheet.create)))

(defn root []
  (let [loading? (rf/subscribe [:app-loading?])]
    (fn []
      [:> rn/View {:style (.-container styles)}
       [:> rn/Text {:style (.-title styles)}
        (if @loading?
          "Loading app"
          "Hi!")]])))

(defn ^:dev/after-load start []
  (rf/clear-subscription-cache!)
  (render-root "HorariosFCUNAM" (r/as-element [root])))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (start))
