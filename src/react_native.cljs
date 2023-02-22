(ns react-native
  (:require
   ["react-native" :as rn]
   [reagent.core :as r]))

(def view (r/adapt-react-class rn/View))
(def scroll-view (r/adapt-react-class rn/ScrollView))
(def flat-list (r/adapt-react-class rn/FlatList))

(def text (r/adapt-react-class rn/Text))
(def image (r/adapt-react-class rn/Image))

(def touchable-highlight (r/adapt-react-class rn/TouchableHighlight))
(def touchable-opacity (r/adapt-react-class rn/TouchableOpacity))

(def style-sheet rn/StyleSheet.create)

(def activity-indicator (r/adapt-react-class rn/ActivityIndicator))

;; Logs
(defn ignore-logs [v-strs]
  (.ignoreLogs rn/LogBox (clj->js v-strs)))

;; Animated
(def animated rn/Animated)

(def animated-view (r/adapt-react-class (.-View animated)))

(defn animated-value
  "new Animated.Value(initial-value)"
  [initial-value]
  (new (.-Value animated) initial-value))

(defn animated-timing
  "Animated.timing(animation-ref, anim-params)"
  [animation-ref anim-params]
  (let [js-anim-params (clj->js anim-params)]
    (. animated (timing animation-ref js-anim-params))))

(defn start-animated-timing
  "Animated.timing(animation-ref, anim-params).start"
  [animation-ref anim-params]
  (.start (animated-timing animation-ref anim-params)))

;; Theme
(def appearance rn/Appearance)
(def use-color-scheme rn/useColorScheme)
(defn get-color-scheme []
  (keyword (.getColorScheme appearance)))
