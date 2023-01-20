(ns horarios-fc.parser.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::majors-by-semester
 :<- [:schedule]
 (fn [schedule [_ semester]]
   (get-in schedule [semester :data])))
