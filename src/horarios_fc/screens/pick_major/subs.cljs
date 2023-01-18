(ns horarios-fc.screens.pick-major.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::majors-list
 :<- [:schedule]
 (fn [schedule]
   (mapv (fn [[major plans]]
           {:major major
            :plans plans})
         schedule)))

(rf/reg-sub
 ::major-selected
 :<- [:schedule-shown-content]
 (fn [schedule-shown-content]
   (:major schedule-shown-content)))
