(ns horarios-fc.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :app-loading?
 (fn [db _]
   (:app-loading? db true)))

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

(rf/reg-sub
 :plan-selected
 :<- [:schedule-shown-content]
 (fn [schedule-shown-content]
    (:plan schedule-shown-content)))

(rf/reg-sub
 :semester-num-selected
 :<- [:schedule-shown-content]
 (fn [schedule-shown-content]
   (:semester-num schedule-shown-content)))

(rf/reg-sub
 :subject-selected
 :<- [:schedule-shown-content]
 (fn [schedule-shown-content]
   (:subject schedule-shown-content)))

(rf/reg-sub
  :group-selected-id
  :<- [:schedule-shown-content]
  (fn [schedule-shown-content]
    (:group-id schedule-shown-content)))

(rf/reg-sub
  :group-selected-roles
  :<- [:schedule-shown-content]
  (fn [schedule-shown-content]
    (:group-roles schedule-shown-content)))

(rf/reg-sub
 :requesting-data?
 (fn [db]
   (:requesting-data? db)))
