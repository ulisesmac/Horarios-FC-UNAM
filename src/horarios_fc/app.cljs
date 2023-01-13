(ns horarios-fc.app
  (:require
   [react-native :as rn]
   [horarios-fc.events]
   [horarios-fc.subs]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [shadow.react-native :refer [render-root]]))

(defn root []
  (let [loading? (rf/subscribe [:app-loading?])]
    (fn []
      [rn/view {:style {:flex            1
                        :justify-content :center
                        :align-items     :center}}
       [rn/text
        (if @loading?
          "Loading app"
          "Hi!")]])))

(defn ^:dev/after-load start []
  (rf/clear-subscription-cache!)
  (render-root "HorariosFCUNAM" (r/as-element [root])))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (start))
