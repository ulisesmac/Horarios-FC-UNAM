(ns horarios-fc.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :app-loading?
 (fn [db _]
   (:app-loading? db)))

(rf/reg-sub
 :schedule
 (fn [db]
   (:schedule db)))

(rf/reg-sub
 :schedule-shown-content
 (fn [db]
   (:schedule-shown-content db)))

(rf/reg-sub
 :semester-selected
 :<- [:schedule-shown-content]
 (fn [schedule-shown-content]
   (:semester schedule-shown-content)))
