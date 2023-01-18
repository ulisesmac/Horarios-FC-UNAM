(ns react-navigation.native-stack
  (:require
   ["@react-navigation/native-stack" :refer [createNativeStackNavigator]]
   [reagent.core :as r]))

(defn create-native-stack-navigator []
  (let [stack (createNativeStackNavigator)]
    #:stack{:navigator (r/adapt-react-class (.-Navigator stack))
            :screen    (r/adapt-react-class (.-Screen stack))}))
