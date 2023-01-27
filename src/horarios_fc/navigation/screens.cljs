(ns horarios-fc.navigation.screens
  (:require
   [horarios-fc.components.navigation-icons :as nav-icons]
   [horarios-fc.navigation.utils :as nav-utils]
   [horarios-fc.screens.my-schedule.views :as my-schedule]
   [horarios-fc.screens.pick-major.views :as pick-major]
   [horarios-fc.screens.pick-plan.views :as pick-plan]
   [horarios-fc.screens.pick-subject.views :as pick-subject]
   [horarios-fc.screens.settings.views :as settings]
   [react-native :as rn]
   [react-navigation.native-stack :refer [create-native-stack-navigator]]
   [reagent.core :as r]))

(defonce schedules-stack (create-native-stack-navigator))
(defn schedules-stack-screen []
  [nav-utils/create-stack-navigator
   {:native-stack-navigator schedules-stack
    :screens                [{:props     {:name :schedule-major}
                              :component pick-major/screen}
                             {:props     {:name :schedule-plan}
                              :component pick-plan/screen}
                             {:props     {:name :schedule-subject}
                              :component pick-subject/screen}]}])

(defn nav-icon [{:keys [focused active inactive]}]
  [rn/view {:style {:margin-bottom -4}}
   [rn/image {:style  {:height 26
                       :width  26}
              :source (if focused active inactive)}]])


(defn browse-schedules-icon [js-params]
  (r/as-element
   [nav-icon {:focused  (.-focused js-params)
              :active   nav-icons/browse-active-icon
              :inactive nav-icons/browse-inactive-icon}]))

(defn my-schedule-icon [js-params]
  (r/as-element
   [nav-icon {:focused  (.-focused js-params)
              :active   nav-icons/schedule-active-icon
              :inactive nav-icons/schedule-inactive-icon}]))

(defn sttings-icon [js-params]
  (r/as-element
   [nav-icon {:focused  (.-focused js-params)
              :active   nav-icons/settings-active-icon
              :inactive nav-icons/settings-inactive-icon}]))

(defn tab-screens []
  [{:props     {:name    :schedule-tab
                :options {:title        "Navegar horarios"
                          :tab-bar-icon browse-schedules-icon}}
    :component #(r/as-element [schedules-stack-screen])}
   {:props     {:name    :my-schedule
                :options {:title        "Mi horario"
                          :tab-bar-icon my-schedule-icon}}
    :component my-schedule/screen}
   {:props     {:name    :settings
                :options {:title        "Configuración"
                          :tab-bar-icon sttings-icon}}
    :component settings/screen}])
