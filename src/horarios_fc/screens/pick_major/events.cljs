(ns horarios-fc.screens.pick-major.events
  (:require
   [horarios-fc.parser.events :as p]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 ::choose-major
 (fn [{db :db} [_ selected-major]]
   {:db       (assoc-in db [:schedule-shown-content :major] selected-major)
    :navigate {:route-name :schedule-plan}}))

(rf/reg-event-fx
 ::choose-semester
 (fn [{db :db} [_ semester]]
   {:db (-> db
            (assoc-in [:schedule-shown-content :semester] semester)
            (assoc :requesting-data? true))
    :fx [[:dispatch [::p/get-majors {:semester       semester
                                     :on-success-evt [:stop-requesting-data]}]]]}))
