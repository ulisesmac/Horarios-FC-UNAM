(ns horarios-fc.screens.pick-major.views
  (:require
   [horarios-fc.colors :refer [alpha theme]]
   [horarios-fc.components.requesting-data :refer [requesting-data]]
   [horarios-fc.screens.pick-major.events :as events]
   [horarios-fc.components.major-icons :as mi]
   [horarios-fc.screens.pick-major.subs :as subs]
   [horarios-fc.util :as util]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn major-text [text]
  [rn/text {:android_hyphenationFrequency :normal
            :style                        {:font-size   17
                                           :font-weight "600"
                                           :color       (theme :primary-800)}}
   text])

(defn major-card [major]
  [rn/touchable-opacity {:on-press #(rf/dispatch [::events/choose-major major])}
   [rn/view {:style {:flex-direction     :row
                     :background-color   (alpha (theme :primary-100) 10)
                     :border-radius      16
                     :border-width       1
                     :border-color       (theme :primary-800)
                     :width              185
                     :height             120
                     :padding-vertical   12
                     :padding-horizontal 6
                     :column-gap         4}}
    [rn/view {:style {:justify-content :center}}
     (if-let [major-image-source (mi/major-icon major)]
       [rn/image {:style  {:width  59
                           :height 70}
                  :source major-image-source}]
       [rn/text {:style {:font-size 42
                         :color     (theme :basic-1000)}}
        "❔"])]
    [rn/view {:style {:flex            1
                      :justify-content :center}}
     (if (= major "Ciencias de la Computación")
       [:<>
        [major-text "Ciencias de la"]
        [major-text "Computación"]]
       [major-text major])]]])

(defn header []
  (let [selected-semester (rf/subscribe [:semester-selected])]
    (fn []
      [rn/view {:style {:height              54
                        :position            :relative
                        :justify-content     :center
                        :align-items         :center
                        :border-bottom-width 1
                        :margin-horizontal   -15
                        :border-bottom-color (theme :basic-400)}}
       [rn/text {:style {:font-size   24
                         :font-weight "600"
                         :color       (theme :primary-600)}}
        "Horarios "
        [rn/text {:style {:color (theme :secondary-600)}}
         @selected-semester]]])))

(defn semester-picker [semester]
  (let [selected-semester (rf/subscribe [:semester-selected])]
    (fn []
      [rn/touchable-highlight {:style    {:border-radius 16}
                               :on-press (when-not (= @selected-semester semester)
                                           #(rf/dispatch
                                             [::events/choose-semester semester]))}
       [rn/view {:style {:padding-horizontal 14
                         :padding-vertical   6
                         :border-width       1
                         :border-color       (if (= @selected-semester semester)
                                               (theme :secondary-700)
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
                                          (theme :secondary-700)
                                          (theme :primary-700))
                           :font-weight "500"}}
          semester]]]])))

(defn subtitle-text [text]
  [rn/text {:style {:font-size   18
                    :font-weight "500"
                    :color       (theme :primary-800)}}
   text])

(defn semester-options []
  [rn/view {:style {:padding-vertical 2}}
   [subtitle-text "Semestres"]
   [rn/scroll-view {:horizontal true}
    [rn/view {:style {:flex-direction     :row
                      :align-items        :center
                      :padding-vertical   6
                      :padding-horizontal 2
                      :column-gap         12}}
     (map (fn [semester]
            ^{:key semester}
            [semester-picker semester])
          util/selectable-semesters-range)]]])

(defn major-selector []
  (let [selected-semester (rf/subscribe [:semester-selected])]
    (fn []
      (let [majors (rf/subscribe [::subs/majors-list-by-semester @selected-semester])]
        [rn/view {:style {:flex              1
                          :padding-vertical  2
                          :margin-horizontal -11}}
         [rn/view {:style {:margin-horizontal 11}}
          [subtitle-text "Licenciaturas"]]
         [rn/scroll-view {:content-container-style {:flex-direction  :row
                                                    :justify-content :center
                                                    :flex-wrap       :wrap
                                                    :column-gap      12
                                                    :row-gap         16
                                                    :padding-top     12
                                                    :padding-bottom  16}}
          (map #(with-meta [major-card %] {:key (str %)})
               @majors)]]))))

(defn screen* []
  [rn/view {:style {:position           :relative
                    :flex               1
                    :background-color   (theme :basic-100)
                    :padding-horizontal 15
                    :row-gap            2}}
   [requesting-data]
   [header]
   [rn/view {:style {:row-gap 6, :flex 1}}
    [semester-options]
    [rn/view {:style {:height            1
                      :margin-horizontal -15
                      :background-color  (theme :basic-300)}}]
    [major-selector]]])

(defn screen []
  (r/as-element [screen*]))
