(ns ^:dev/always horarios-fc.navigation.navigation
  (:require
   [horarios-fc.colors :refer [theme]]
   [horarios-fc.navigation.screens :as screens]
   [horarios-fc.navigation.utils :as nav-utils]
   [re-frame.core :as rf]
   [react-navigation.bottom-tabs :refer [create-bottom-tab-navigator]]
   [react-navigation.native :refer [create-navigation-container-ref navigation-container]]))

(defonce bottom-tabs (create-bottom-tab-navigator))

(defn app-navigator []
  (let [{tab-screen :tab/screen, tab-navigator :tab/navigator} bottom-tabs]
    [navigation-container {:ref             nav-utils/navigation-ref
                           :initial-state   @(rf/subscribe [:navigation-state])
                           :on-state-change #(rf/dispatch-sync
                                              [:store-navigation-&-state %])}
     [tab-navigator {:screen-options {:header-shown              false
                                      :tab-bar-active-tint-color (theme :primary-600)
                                      :tab-bar-label-style       {:font-size     12
                                                                  :margin-bottom 4
                                                                  :font-weight   "600"}}}
      (map (fn [{:keys [props component] :as _tab-screen}]
             ^{:key (str (:name props))}
             [tab-screen props component])
           (screens/tab-screens))]]))
