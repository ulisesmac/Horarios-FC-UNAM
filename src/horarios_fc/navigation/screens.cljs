(ns horarios-fc.navigation.screens
  (:require
   [horarios-fc.navigation.utils :as nav-utils]
   [horarios-fc.screens.pick-plan :as pick-plan]
   [horarios-fc.screens.pick-major.views :as pick-major]
   [react-navigation.native-stack :refer [create-native-stack-navigator]]
   [reagent.core :as r]))

(defonce schedules-stack (create-native-stack-navigator))
(defn schedules-stack-screen []
  [nav-utils/create-stack-navigator
   {:native-stack-navigator schedules-stack
    :screens                [{:component pick-major/screen
                              :props     {:name :schedule-major}}
                             {:component pick-plan/screen
                              :props     {:name :schedule-plan}}]}])

(defn tab-screens []
  [{:props     {:name      :schedule-tab
                :show-name "Horarios"}
    :component #(r/as-element [schedules-stack-screen])}
   {:props     {:name      :my-schedule
                :show-name "Mi horario"}
    :component pick-major/screen}])
