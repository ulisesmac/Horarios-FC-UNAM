(ns horarios-fc.app
  (:require
   ["react-native" :as rn]
   [horarios-fc.networking :refer [http-request!]]
   [horarios-fc.parser :refer [parse-xml]]
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
  [:> rn/View {:style (.-container styles)}
   [:> rn/Text {:style (.-title styles)} "Hello!"]
   ])

(defn start
  {:dev/after-load true}
  []
  (render-root "HorariosFCUNAM" (r/as-element [root])))

(defn init []
  (start))
