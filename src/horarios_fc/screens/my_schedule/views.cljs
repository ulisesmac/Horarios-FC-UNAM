(ns horarios-fc.screens.my-schedule.views
  (:require
   [horarios-fc.colors :refer [theme-color]]
   [react-native :as rn]
   [reagent.core :as r]
   ["react-native-paper" :refer [Button TextInput]]))

(defn screen* []
  [rn/view {:style {:flex             1
                    :row-gap 12
                    :justify-content  :center
                    :align-items      :center
                    :background-color (theme-color :basic-100 :basic-1000)}}

   [:> Button {:icon     :camera
               :mode     :contained
               :on-press #(js/alert "hola")}
    "Press me"]

   [:> TextInput {:style         {:width 300}
                  :outlineStyle {:border-radius 12}
                  :mode          :flat
                  :label         "Username"
                  :default-value ""}]

   [:> TextInput {:style         {:width         300}
                  :outlineStyle {:border-radius 12}
                  :mode          :outlined
                  :label         "Password"
                  :default-value ""}]

   [:> TextInput {:style         {:width 400
                                  :min-height 100}
                  :outlineStyle {:border-radius 12}
                  :mode          :outlined
                  :multiline     true
                  :label         "What are you thinking? ..."
                  :default-value ""}]

   [rn/text {:style {:font-size   24
                     :font-weight "600"
                     :color       (theme-color :warning-600 :warning-700)
                     :text-align  :center}}
    "⚠ 💻 En progreso ... 🛠 ⚠"]])

(defn screen []
  (r/as-element [screen*]))
