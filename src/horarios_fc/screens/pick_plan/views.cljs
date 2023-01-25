(ns horarios-fc.screens.pick-plan.views
  (:require
   [clojure.string :as string]
   [horarios-fc.colors :refer [alpha theme]]
   [horarios-fc.components.major-icons :as mi]
   [horarios-fc.components.requesting-data :refer [requesting-data]]
   [horarios-fc.screens.pick-major.subs :as pick-major.subs]
   [horarios-fc.screens.pick-plan.events :as events]
   [horarios-fc.screens.pick-plan.subs :as subs]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn plan-button [{:keys [plan url]}]
  [rn/touchable-opacity {:style    {:border-radius 20}
                         :on-press #(rf/dispatch
                                     [::events/get-subjects-by-plan plan url])}
   [rn/view {:style {:background-color   (theme :primary-500)
                     :border-radius      16
                     :justify-content    :center
                     :align-items        :center
                     :padding-horizontal 24
                     :padding-vertical   18}}
    [rn/text {:style {:color       (theme :basic-100)
                      :font-weight "500"
                      :font-size   16}}
     (some-> plan (string/capitalize))]]])

(defn screen* []
  (let [plans-list     (rf/subscribe [::subs/plans-list])
        major-selected (rf/subscribe [::pick-major.subs/major-selected])]
    (fn []
      [rn/view {:style {:position           :relative
                        :flex               1
                        :padding-horizontal 16
                        :padding-top        12
                        :justify-content    :center
                        :background-color   (theme :basic-100)}}
       [requesting-data]
       [rn/view {:style {:flex                1
                         :justify-content     :center
                         :align-items         :center
                         :row-gap             6
                         :border-bottom-width 0.6
                         :border-bottom-color (theme :basic-400)}}
        (when-let [image-source (mi/major-icon @major-selected)]
          [rn/image {:style  {:width  120
                              :height 120}
                     :source image-source}])
        [rn/text {:style {:text-align  :center
                          :font-size   26
                          :font-weight "500"
                          :color       (theme :primary-700)}}
         (str @major-selected)]]

       [rn/view {:style {:flex 2}}
        [rn/scroll-view {:content-container-style {:flex               1
                                                   :row-gap            18
                                                   :padding-vertical   12
                                                   :padding-horizontal 8
                                                   :justify-content    :center}}
         (map (fn [{:keys [plan] :as props}]
                ^{:key plan} [plan-button props])
              @plans-list)]]])))

(defn screen []
  (r/as-element [screen*]))
