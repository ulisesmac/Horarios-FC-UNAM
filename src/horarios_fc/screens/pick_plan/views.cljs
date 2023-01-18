(ns horarios-fc.screens.pick-plan.views
  (:require
   [horarios-fc.screens.pick-plan.subs :as subs]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn plan-button [{:keys [text url]}]
  [rn/touchable-highlight {:on-press (fn []
                                       (println url))}
   [rn/view {:style {:background-color   :green
                     :justify-content    :center
                     :align-items        :center
                     :padding-horizontal 24
                     :padding-vertical   24}}
    [rn/text text]]])

(defn screen* []
  (let [plans-list (rf/subscribe [::subs/plans-list])]
    (fn []
      [rn/view {:style {:flex               1
                        :padding-horizontal 16
                        :padding-vertical   12}}
       [rn/view {:style {:flex               1
                         :row-gap            18
                         :padding-horizontal 12
                         :justify-content    :center}}
        (map (fn [{:keys [plan url]}]
               ^{:key plan} [plan-button {:text plan
                                          :url  url}])
             @plans-list)]])))

(defn screen []
  (r/as-element [screen*]))
