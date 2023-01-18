(ns horarios-fc.screens
  (:require
   [horarios-fc.screens.pick-major.views :as pick-major]
   [react-navigation.native-stack :refer [create-native-stack-navigator]]
   [reagent.core :as r]))

(defn create-stack-navigator [{:keys [native-stack-navigator screens]}]
  (let [{stack-screen    :stack/screen
         stack-navigator :stack/navigator} native-stack-navigator]
    [stack-navigator
     (map (fn [{:keys [name] :as screen-props}]
            ^{:key (str name)} [stack-screen screen-props])
          screens)]))

(defonce schedules-stack (create-native-stack-navigator))
(def schedules-stack-screen
  (r/reactify-component
   (fn []
     [create-stack-navigator
      {:native-stack-navigator schedules-stack
       :screens                [{:name      :schedule-major
                                 :component pick-major/screen}]}])))

(def tab-screens
  [{:name      :schedule-tab
    :show-name "Horarios"
    :component schedules-stack-screen}
   {:name      :my-schedule
    :show-name "Mi horario"
    :component pick-major/screen}])
