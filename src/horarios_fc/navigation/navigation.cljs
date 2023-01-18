(ns horarios-fc.navigation.navigation
  (:require
   [horarios-fc.navigation.screens :as screens]
   [react-native :as rn]
   [horarios-fc.navigation.utils :as nav-utils]
   [react-navigation.bottom-tabs :refer [create-bottom-tab-navigator]]
   [react-navigation.native :refer [create-navigation-container-ref navigation-container]]
   [reagent.core :as r]))

(defonce bottom-tabs (create-bottom-tab-navigator))
(defn tmp-tab-icon [focused color size]
  (r/as-element [rn/view {:style {:background-color color}}
                 [rn/text "i"]]))

(defn ^:dev/after-load app-navigator []
  (let [{tab-screen :tab/screen, tab-navigator :tab/navigator} bottom-tabs]
    [navigation-container {:ref nav-utils/navigation-ref}
     [tab-navigator {:screen-options {:header-shown              false
                                      :tab-bar-active-tint-color "green"
                                      :tab-bar-icon              tmp-tab-icon}}
      (map (fn [{:keys [props component] :as _tab-screen}]
             ^{:key (str (:name props))}
             [tab-screen props component])
           (screens/tab-screens))]]))
