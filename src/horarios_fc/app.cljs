(ns horarios-fc.app
  (:require
   [horarios-fc.events]
   [horarios-fc.navigation.navigation :as nav]
   [horarios-fc.subs]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]
   [shadow.react-native :refer [render-root]]))

(defn root []
  (let [loading? (rf/subscribe [:app-loading?])]
    (fn []
      (if false ;@loading?
        [rn/text {:style {:color "#101010"}}
         "Loading app"]
        [:f> nav/app-navigator]))))

(defn ^:dev/after-load start []
  (rf/clear-subscription-cache!)
  (render-root "HorariosFCUNAM" (r/as-element [root])))

(defn init []
  (rn/ignore-logs ["Got a component with the name 'cmp'"])
  (rf/dispatch-sync [:initialize-db])
  (start))
