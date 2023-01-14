(ns horarios-fc.app
  (:require
   [react-native :as rn]
   [horarios-fc.events]
   [horarios-fc.subs]
   [re-frame.core :as rf]
   [reagent.core :as r]
   [shadow.react-native :refer [render-root]]
   ;; TMP
   [horarios-fc.screens.pick-major.views :as pick-major]
   ))

(defn root []
  (let [loading? (rf/subscribe [:app-loading?])]
    (fn []
      (if @loading?
        [rn/text {:style {:color "#101010"}}
         "Loading app"]
        [pick-major/screen]))))

(defn ^:dev/after-load start []
  (rf/clear-subscription-cache!)
  (render-root "HorariosFCUNAM" (r/as-element [root])))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (start))
