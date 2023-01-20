(ns horarios-fc.screens.pick-subject.views
  (:require
   [horarios-fc.colors :refer [alpha theme]]
   [horarios-fc.screens.pick-subject.subs :as subs]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn subject [{:keys [subject url]}]
  [rn/touchable-highlight {:style          {:border-radius     18
                                            :margin-horizontal 4}
                           :underlay-color (alpha (theme :primary-100) 80)
                           :on-press       #(prn url)}
   [rn/view {:style {:border-radius      18
                     :padding-vertical   14
                     :padding-horizontal 18}}
    [rn/text {:style {:font-size 16
                      :color     (theme :primary-800)}}
     (str subject)]]])

(defn semester-w-subjects [{:keys [semester-num subjects]}]
  (let [opened? (r/atom false)]
    (fn []
      [rn/view
       [rn/touchable-highlight {:style    {:border-radius 16}
                                :on-press #(swap! opened? not)}
        [rn/view {:style {:background-color   (theme :secondary-600)
                          :border-radius      16
                          :padding-vertical   12
                          :padding-horizontal 18}}
         [rn/view {:style {:flex-direction  :row
                           :justify-content :space-between
                           :align-items     :center}}
          [rn/text {:style {:font-size   18
                            :font-weight "500"
                            :color       (theme :basic-100)}}
           (str semester-num)]

          [rn/text {:style {:font-size   20
                            :font-weight "800"
                            :color       (theme :basic-100)}}
           (if @opened? "-" "+")]]]]
       (when @opened?
         [rn/view
          (map (fn [{:keys [url] :as props}]
                 ^{:key url} [subject props])
               subjects)])])))

(defn top-panel []
  [rn/view {:style {:flex                1
                    :justify-content     :center
                    :border-bottom-width 0.6
                    :border-bottom-color (theme :basic-300)}}
   [rn/text {:style {:text-align  :center
                     :font-size   26
                     :font-weight "600"
                     :color       (theme :secondary-600)}}
    (str "Elige un semestre y materia")]])

(defn bottom-panel []
  (let [semesters (rf/subscribe [::subs/semesters-w-subjects-list])]
    (fn []
      [rn/view {:style {:flex 1}}
       [rn/scroll-view {:content-container-style {:row-gap            20
                                                  :padding-top        12
                                                  :padding-bottom     32
                                                  :padding-horizontal 8
                                                  :justify-content    :center}}
        (map (fn [{:keys [semester-num] :as props}]
               ^{:key semester-num} [semester-w-subjects props])
             @semesters)]])))

(defn screen* []
  [rn/view {:style {:flex               1
                    :padding-horizontal 16
                    :padding-top        12
                    :justify-content    :center
                    :background-color   (theme :basic-100)}}
   [top-panel]
   [bottom-panel]])

(defn screen []
  (r/as-element [screen*]))
