(ns react-navigation.native
  (:require
   ["@react-navigation/native" :refer [NavigationContainer]]
   [reagent.core :as r]))

(def navigation-container (r/adapt-react-class NavigationContainer))
