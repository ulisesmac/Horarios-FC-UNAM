(ns react-navigation.native
  (:require
   ["@react-navigation/native" :refer [NavigationContainer createNavigationContainerRef]]
   [reagent.core :as r]))

(def navigation-container (r/adapt-react-class NavigationContainer))
(def create-navigation-container-ref createNavigationContainerRef)
