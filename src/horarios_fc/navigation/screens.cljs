(ns horarios-fc.navigation.screens
  (:require
   [horarios-fc.navigation.utils :as nav-utils]
   [horarios-fc.screens.pick-major.views :as pick-major]
   [horarios-fc.screens.pick-plan.views :as pick-plan]
   [horarios-fc.screens.pick-subject.views :as pick-subject]
   [react-native :as rn]
   [react-navigation.native-stack :refer [create-native-stack-navigator]]
   [reagent.core :as r]))

(defonce schedules-stack (create-native-stack-navigator))
(defn schedules-stack-screen []
  [nav-utils/create-stack-navigator
   {:native-stack-navigator schedules-stack
    :screens                [{:component pick-major/screen
                              :props     {:name    :schedule-major
                                          :options {:title "Consulta de horarios"}}}
                             {:component pick-plan/screen
                              :props     {:name :schedule-plan}}
                             {:component pick-subject/screen
                              :props     {:name    :schedule-subject
                                          :options {:header-transparent true
                                                    :header-title       ""}}}]}])


(defn browse-schedules-icon [js-params]
  (let [{:keys [focused color size]} (js->clj js-params :keywordize-keys true)]
    (r/as-element
     [rn/view
      [rn/text {:style (merge {:font-size 24}
                              (when focused {:color color}))}
       "🧭"]])))

(defn my-schedule-icon [js-params]
  (let [{:keys [focused color size]} (js->clj js-params :keywordize-keys true)]
    (r/as-element
     [rn/view
      [rn/text {:style (merge {:font-size 24}
                              (when focused {:color color}))}
       "🕰"]])))

(defn tab-screens []
  [{:props     {:name    :schedule-tab
                :options {:title        "Navegar horarios"
                          :tab-bar-icon browse-schedules-icon}}
    :component #(r/as-element [schedules-stack-screen])}
   {:props     {:name    :my-schedule
                :options {:title "Mi horario"
                          :tab-bar-icon my-schedule-icon}}
    :component pick-major/screen}])
