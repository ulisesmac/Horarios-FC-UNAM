(ns react-native-clipboard.clipboard
  (:require
   ["@react-native-clipboard/clipboard" :default Clipboard]))

(def set-string (.-setString Clipboard))
(def get-string (.-getString Clipboard))

