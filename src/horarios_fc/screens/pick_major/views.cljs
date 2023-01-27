(ns horarios-fc.screens.pick-major.views
  (:require
   [horarios-fc.components.major-icons :as mi]
   [horarios-fc.components.requesting-data :refer [requesting-data]]
   [horarios-fc.screens.pick-major.events :as events]
   [horarios-fc.screens.pick-major.style :as style]
   [horarios-fc.screens.pick-major.subs :as subs]
   [horarios-fc.util :as util]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn major-text [text]
  [rn/text {:android_hyphenationFrequency :normal
            :style                        (style/major-text)}
   text])

(defn major-card [major]
  [rn/touchable-opacity {:on-press #(rf/dispatch [::events/choose-major major])}
   [rn/view {:style (style/major)}
    [rn/view {:style style/major-image-container}
     (if-let [major-image-source (mi/major-icon major)]
       [rn/image {:style  style/major-image
                  :source major-image-source}]
       [rn/text {:style style/major-anon-text}
        "❔"])]
    [rn/view {:style style/major-text-container}
     (if (= major "Ciencias de la Computación")
       [:<>
        [major-text "Ciencias de la"]
        [major-text "Computación"]]
       [major-text major])]]])

(defn header []
  (let [selected-semester (rf/subscribe [:semester-selected])]
    (fn []
      [rn/view {:style (style/header)}
       [rn/text {:style style/header-text-1}
        "Horarios "
        [rn/text {:style style/header-text-2}
         @selected-semester]]])))

(defn semester-picker [semester]
  (let [selected-semester (rf/subscribe [:semester-selected])]
    (fn []
      (let [active-button? (= @selected-semester semester)]
        [rn/touchable-highlight {:style    style/semester-button-radius
                                 :on-press (when-not (= @selected-semester semester)
                                             #(rf/dispatch
                                               [::events/choose-semester semester]))}
         [rn/view {:style (style/semester-button active-button?)}
          [rn/view {:style style/semester-button-text-container}
           [rn/text {:style (style/semester-button-text active-button?)}
            semester]]]]))))

(defn subtitle-text [text]
  [rn/text {:style (style/subtitle-text)}
   text])

(defn semester-options []
  [rn/view {:style style/semester-picker}
   [subtitle-text "Semestres"]
   [rn/scroll-view {:horizontal true}
    [rn/view {:style style/semester-container}
     (map (fn [semester]
            ^{:key semester} [semester-picker semester])
          util/selectable-semesters-range)]]])

(defn major-selector []
  (let [selected-semester (rf/subscribe [:semester-selected])]
    (fn []
      (let [majors @(rf/subscribe [::subs/majors-list-by-semester @selected-semester])]
        [rn/view {:style style/majors-container}
         [rn/view {:style style/major-title-container}
          [subtitle-text "Licenciaturas"]]
         [rn/scroll-view {:content-container-style style/majors-listing-container}
          (map #(with-meta [major-card %] {:key (str %)})
               majors)]]))))

(defn screen* []
  [rn/view {:style (style/container)}
   [requesting-data]
   [header]
   [rn/view {:style style/body-container}
    [semester-options]
    [major-selector]]])

(defn screen []
  (r/as-element [screen*]))
