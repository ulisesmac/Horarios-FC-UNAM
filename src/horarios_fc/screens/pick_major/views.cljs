(ns horarios-fc.screens.pick-major.views
  (:require
   [horarios-fc.screens.pick-major.events :as events]
   [horarios-fc.util :as util]
   [horarios-fc.colors :refer [theme alpha]]
   [horarios-fc.screens.pick-major.subs :as subs]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(def major-icon
  {"Actuaría"                             ["\uD83D\uDCB9" "📊"]
   "Biología"                             ["\uD83E\uDDEB" "🧬"]
   "Ciencias de la Computación"           ["\uD83C\uDF10" "\uD83D\uDC68\u200D\uD83D\uDCBB"]
   "Ciencias de la Tierra"                ["\uD83C\uDF0B" "\uD83D\uDDFA"]
   "Física"                               ["⚛️" "\uD83C\uDF0C"]
   "Física Biomédica"                     ["⚙️" "⚕️"]
   "Manejo Sustentable de Zonas Costeras" ["\uD83C\uDF0A " "\uD83C\uDFDD"]
   "Matemáticas Aplicadas"                ["❔" "🧮"]
   "Matemáticas"                          ["\uD83D\uDD22" "➕️"]})

(defn major-card [major]
  [rn/touchable-highlight {:style          {:border-radius 12}
                           :active-opacity 0.7
                           :underlay-color "#DDDDDD"
                           :on-press       #(rf/dispatch [::events/choose-major major])}
   [rn/view {:style {:background-color "#F2F2F2"
                     :border-radius    12
                     :border-width     0.6
                     :border-color     "#7e7e7e"
                     :height           170
                     :width            170
                     :padding          4}}
    [rn/view {:style {:position          :relative
                      :flex              1
                      :margin-vertical   6
                      :margin-horizontal 15}}
     [rn/text {:style {:top       0
                       :left      0
                       :font-size 70
                       :color     "#101010"}}
      (first (major-icon major "❔"))]

     [rn/text {:style {:position  :absolute
                       :bottom    0
                       :right     0
                       :font-size 70
                       :color     "#101010"}}
      (second (major-icon major "❔"))]]
    ;; Divider
    [rn/view {:style {:height 0.8, :background-color "#7e7e7e"}}]
    ;; Major name
    [rn/view {:style {:justify-content :center
                      :height          40
                      :align-items     :center}}
     [rn/text {:style {:color       "#101010"
                       :line-height 18}}
      major]]]])

(defn header []
  (let [selected-semester (rf/subscribe [:semester-selected])]
    (fn []
      [rn/view {:style {:height              52
                        :justify-content     :center
                        :align-items         :center
                        :border-bottom-width 1
                        :margin-horizontal   -15
                        :border-bottom-color (theme :basic-300)}}
       [rn/text {:style {:font-size   22
                         :font-weight "500"
                         :color       (theme :primary-600)}}
        "Horarios "
        [rn/text {:style {:font-weight "600"
                          :color       (theme :secondary-600)}}
         @selected-semester]]])))

(defn semester-picker [semester]
  (let [selected-semester (rf/subscribe [:semester-selected])]
    (fn []
      [rn/touchable-highlight {:style    {:border-radius 16}
                               :on-press (when-not (= @selected-semester semester)
                                           (fn []
                                             (prn semester)))}
       [rn/view {:style {:padding-horizontal 14
                         :padding-vertical   6
                         :border-width       1
                         :border-color       (if (= @selected-semester semester)
                                               (theme :secondary-600)
                                               (theme :primary-700))
                         :border-radius      12
                         :background-color   (if (= @selected-semester semester)
                                               (theme :secondary-100)
                                               (theme :primary-100))
                         :justify-content    :center
                         :align-items        :center}}
        [rn/view {:style {:height          18
                          :justify-content :center}}
         [rn/text {:style {:color       (if (= @selected-semester semester)
                                          (theme :secondary-600)
                                          (theme :primary-700))
                           :font-weight "500"}}
          semester]]]])))

(defn semester-options []
  [rn/scroll-view {:horizontal true}
   [rn/view {:style {:flex-direction     :row
                     :align-items        :center
                     :padding-vertical   6
                     :padding-horizontal 2
                     :column-gap         12}}
    (map (fn [semester]
           ^{:key semester}
           [semester-picker semester])
         util/selectable-semesters-range)]])

(defn screen* [] ;; TODO: set semester as a value in re-frame db
  (let [majors (rf/subscribe [::subs/majors-list-by-semester "2023-2"])]
    (fn []
      [rn/view {:style {:flex               1
                        :background-color   (theme :basic-100)
                        :padding-horizontal 15
                        :row-gap            2}}
       [header]
       [rn/view {:style {:flex 1, :row-gap 12}}
        [semester-options]
        [rn/text {:style {:color "#101010"}}
         "Selecciona la carrera a consultar"]
        [rn/scroll-view
         [rn/view {:style {:flex-direction     :row
                           :justify-content    :center
                           :flex-wrap          :wrap
                           :gap                16
                           :padding-horizontal 16
                           :padding-vertical   12}}
          (map #(with-meta [major-card %] {:key (str %)})
               @majors)]]]])))

(defn screen []
  (r/as-element [screen*]))
