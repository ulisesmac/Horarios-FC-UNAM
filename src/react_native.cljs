(ns react-native
  (:require
   ["react-native" :as rn]
   [reagent.core :as r]))

(def view (r/adapt-react-class rn/View))
(def text (r/adapt-react-class rn/Text))

(def style-sheet-create rn/StyleSheet.create)
