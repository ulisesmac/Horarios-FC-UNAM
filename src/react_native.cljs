(ns react-native
  (:require
   ["react-native" :as rn]
   [reagent.core :as r]))

(def view (r/adapt-react-class rn/View))
(def scroll-view (r/adapt-react-class rn/ScrollView))

(def text (r/adapt-react-class rn/Text))
(def image (r/adapt-react-class rn/Image))

(def touchable-highlight (r/adapt-react-class rn/TouchableHighlight))

(def style-sheet rn/StyleSheet.create)


;; Logs
(defn ignore-logs [v-strs]
  (.ignoreLogs rn/LogBox (clj->js v-strs)))