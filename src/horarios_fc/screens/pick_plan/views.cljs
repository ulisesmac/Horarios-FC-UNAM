(ns horarios-fc.screens.pick-plan.views
  (:require
   [horarios-fc.colors :refer [theme]]
   [horarios-fc.screens.pick-major.subs :as pick-major.subs]
   [horarios-fc.screens.pick-plan.subs :as subs]
   [clojure.string :as string]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn plan-button [{:keys [plan url] :as m}]
  [rn/touchable-highlight {:style    {:border-radius 20}
                           :on-press (fn []
                                       (println url))}
   [rn/view {:style {:border-color       (theme :primary-600)
                     :border-width       1
                     :background-color   (theme :primary-100)
                     :border-radius      20
                     :justify-content    :center
                     :align-items        :center
                     :padding-horizontal 24
                     :padding-vertical   24}}
    [rn/text {:style {:color       (theme :primary-600)
                      :font-weight "500"
                      :font-size   16}}
     (string/capitalize plan)]]])

(defn screen* []
  (let [plans-list     (rf/subscribe [::subs/plans-list])
        major-selected (rf/subscribe [::pick-major.subs/major-selected])]
    (fn []
      [rn/view {:style {:flex               1
                        :padding-horizontal 16
                        :padding-top        12
                        :justify-content    :center
                        :background-color   (theme :color-basic-100)}}

       [rn/view {:style {:flex                1
                         :justify-content     :center
                         :border-bottom-width 0.6
                         :border-bottom-color (theme :color-basic-200)}}
        [rn/text {:style {:text-align  :center
                          :font-size   26
                          :font-weight "500"
                          :color       (theme :secondary-500)}}
         (str @major-selected)]]

       [rn/view {:style {:flex 3}}
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
