(ns ^:dev/always horarios-fc.navigation.navigation
  (:require
   [horarios-fc.colors :refer [theme-color]]
   [horarios-fc.navigation.screens :as screens]
   [horarios-fc.navigation.utils :as nav-utils]
   [re-frame.core :as rf]
   [react-native :as rn]
   [react-navigation.bottom-tabs :refer [create-bottom-tab-navigator]]
   [react-navigation.native :refer [create-navigation-container-ref navigation-container]]))

(defonce bottom-tabs (create-bottom-tab-navigator))

(defn app-navigator []
  (let [new-color-scheme    (rn/use-color-scheme)
        stored-theme-scheme @(rf/subscribe [:theme])
        {tab-screen :tab/screen, tab-navigator :tab/navigator} bottom-tabs]
    ;; Theme update
    (when (and (= stored-theme-scheme :auto))
      (rf/dispatch-sync [:set-theme (keyword new-color-scheme)]))
    ;;
    [navigation-container {:ref             nav-utils/navigation-ref
                           :initial-state   @(rf/subscribe [:navigation-state])
                           :on-state-change #(rf/dispatch-sync
                                              [:store-navigation-&-state %])}
     [tab-navigator
      {:screen-options
       {:header-shown              false
        :tab-bar-active-tint-color (theme-color :secondary-600)
        :tab-bar-label-style       {:font-size     12
                                    :margin-bottom 4
                                    :font-weight   "600"}
        :tab-bar-style             {:background-color (theme-color :basic-100 :basic-1000)}}}
      (map (fn [{:keys [props component] :as _tab-screen}]
             ^{:key (str (:name props))}
             [tab-screen props component])
           (screens/tab-screens))]]))
