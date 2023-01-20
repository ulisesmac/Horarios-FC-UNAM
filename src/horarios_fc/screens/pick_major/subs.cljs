(ns horarios-fc.screens.pick-major.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::majors-list-by-semester
 :<- [:schedule]
 (fn [schedule [_ semester]]
   (->> (get-in schedule [semester :data])
        (sort-by (fn [[_major {:keys [idx]}]] idx))
        (map first))))

(rf/reg-sub
 ::major-selected
 :<- [:schedule-shown-content]
 (fn [schedule-shown-content]
   (:major schedule-shown-content)))
