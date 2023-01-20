(ns horarios-fc.screens.pick-plan.subs
  (:require
   [horarios-fc.screens.pick-major.subs :as pick-major.subs]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::majors-by-semester
 :<- [:schedule]
 :<- [:semester-selected]
 (fn [[schedule semester]]
   (get-in schedule [semester :data])))

(rf/reg-sub
 ::plans-list
 :<- [::majors-by-semester]
 :<- [::pick-major.subs/major-selected]
 (fn [[majors major-selected]]
   (as-> majors $
     (get-in $ [major-selected :data])
     (sort-by (fn [_plan {:keys [idx]}] idx) $)
     (map (fn [[plan data-map]]
            {:plan plan, :url (:url data-map)})
          $))))
