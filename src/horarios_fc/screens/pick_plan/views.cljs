(ns horarios-fc.screens.pick-plan.views
  (:require
   [clojure.string :as string]
   [horarios-fc.components.major-icons :as mi]
   [horarios-fc.components.requesting-data :refer [requesting-data]]
   [horarios-fc.screens.pick-major.subs :as pick-major.subs]
   [horarios-fc.screens.pick-plan.events :as events]
   [horarios-fc.screens.pick-plan.style :as style]
   [horarios-fc.screens.pick-plan.subs :as subs]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn plan-button [{:keys [plan url]}]
  [rn/touchable-opacity {:style    style/plan-button-radius
                         :on-press #(rf/dispatch
                                     [::events/get-subjects-by-plan plan url])}
   [rn/view {:style (style/plan-button)}
    [rn/text {:style (style/plan-button-text)}
     (some-> plan (string/capitalize))]]])

(defn top-panel []
  (let [major-selected (rf/subscribe [::pick-major.subs/major-selected])]
    (fn []
      [rn/view {:style (style/top-panel)}
       (when-let [image-source (mi/major-icon @major-selected)]
         [rn/image {:style  style/major-image
                    :source image-source}])
       [rn/text {:style (style/major-text)}
        (str @major-selected)]])))

(defn screen* []
  (let [plans-list (rf/subscribe [::subs/plans-list])]
    (fn []
      [rn/view {:style (style/container)}
       [requesting-data]
       [top-panel]
       [rn/view {:style style/bottom-panel}
        [rn/scroll-view {:content-container-style style/plan-listing}
         (map (fn [{:keys [plan] :as props}]
                ^{:key plan} [plan-button props])
              @plans-list)]]])))

(defn screen []
  (r/as-element [screen*]))
