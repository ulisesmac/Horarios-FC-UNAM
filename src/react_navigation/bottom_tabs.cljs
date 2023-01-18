(ns react-navigation.bottom-tabs
  (:require
   ["@react-navigation/bottom-tabs" :refer [createBottomTabNavigator]]
   [reagent.core :as r]))

(defn create-bottom-tab-navigator []
  (let [tab (createBottomTabNavigator)]
    #:tab{:navigator (r/adapt-react-class (.-Navigator tab))
          :screen    (r/adapt-react-class (.-Screen tab))}))
