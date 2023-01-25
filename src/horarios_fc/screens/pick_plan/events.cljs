(ns horarios-fc.screens.pick-plan.events
  (:require
   [horarios-fc.parser.events :as p]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 ::get-subjects-by-plan
 (fn [{db :db} [_ plan-selected plan-url]]
   {:db (-> db
            (assoc-in [:schedule-shown-content :plan] plan-selected)
            (update :schedule-shown-content dissoc :semester-num :subject)
            (assoc :requesting-data? true))
    :fx [[:dispatch [::p/get-subjects {:plan-url       plan-url
                                       :on-success-evt [::data-aquired]}]]]}))

(rf/reg-event-fx
 ::data-aquired
 (fn [_ _]
   {:fx       [[:dispatch [:stop-requesting-data]]]
    :navigate {:route-name :schedule-subject}}))
