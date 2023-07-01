(ns horarios-fc.app
  (:require
   [horarios-fc.events]
   [horarios-fc.navigation.navigation :as nav]
   [horarios-fc.splash-screen :refer [splash]]
   [horarios-fc.subs]
   [re-frame.core :as rf]
   [react-native :as rn]
   [react-native-async-storage.async-storage]
   [reagent.core :as r]
   [shadow.react-native :refer [render-root]]
   ["react-native-paper" :refer [PaperProvider]]))


(defn root []
  (let [loading?        (rf/subscribe [:app-loading?])
        mounted-splash? (rf/subscribe [:splash-screen-mounted?])]
    (fn []
      [rn/view {:style {:flex 1, :position :relative}}
       (when @mounted-splash?
         [:f> splash @loading?])
       (when-not @loading?
         [:f> nav/app-navigator])])))

(defn ^:dev/after-load start []
  (rf/clear-subscription-cache!)
  (render-root "HorariosFCUNAM"
               (r/as-element [:> PaperProvider [root]])))

(defn init []
  (rn/ignore-logs ["Got a component with the name 'cmp'"
                   "re-frame: overwriting"])
  (rf/dispatch-sync [:initialize-db])
  (start))
