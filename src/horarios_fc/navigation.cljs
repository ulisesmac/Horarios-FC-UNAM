(ns horarios-fc.navigation
  (:require
   [react-native :as rn]
   [react-navigation.native :refer [navigation-container]]
   [react-navigation.bottom-tabs :refer [create-bottom-tab-navigator]]
   [horarios-fc.screens :as screens]
   [reagent.core :as r]))

(defonce bottom-tabs (create-bottom-tab-navigator))
(defn tmp-tab-icon [focused color size]
  (r/as-element [rn/view {:style {:background-color color}}
                 [rn/text "i"]]))

(defn app-navigator []
  (let [{tab-screen :tab/screen, tab-navigator :tab/navigator} bottom-tabs]
    [navigation-container
     [tab-navigator {:screen-options {:header-shown              false
                                      :tab-bar-active-tint-color "green"
                                      :tab-bar-icon              tmp-tab-icon}}
      (map (fn [{:keys [name] :as tab-screen-props}]
             ^{:key (str name)} [tab-screen tab-screen-props])
           screens/tab-screens)]]))
