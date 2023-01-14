(ns horarios-fc.screens.pick-major.views
  (:require
   [re-frame.core :as rf]
   [react-native :as rn]))

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

(defn major-card [{:keys [major url]}]
  [rn/touchable-highlight {:style          {:border-radius 12}
                           :active-opacity 0.7
                           :underlay-color "#DDDDDD"
                           :on-press       #(prn url)}
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

(defn screen []
  (let [majors (rf/subscribe [:majors-list])]
    (fn []
      [rn/view {:style {:flex             1
                        :background-color "#FAFAFA"}}
       [rn/view {:style {:flex 1, :row-gap 12}}
        [rn/text {:style {:color "#101010"}}
         "Selecciona la carrera a consultar"]

        [rn/scroll-view
         [rn/view {:style {:flex               1
                           :flex-direction     :row
                           :justify-content    :center
                           :flex-wrap          :wrap
                           :gap                16
                           :padding-horizontal 16
                           :padding-vertical   12}}
          (map #(with-meta [major-card %] {:key (str %)})
               @majors)]]]])))
