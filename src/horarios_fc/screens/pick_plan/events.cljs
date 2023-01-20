(ns horarios-fc.screens.pick-plan.events
  (:require
   [horarios-fc.parser.events :as p]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 ::get-subjects-by-plan
 (fn [{db :db} [_ plan-selected plan-url]]
   {:db (-> db
            (assoc-in [:schedule-shown-content :plan] plan-selected)
            (assoc :requesting-data? true))
    :fx [[:dispatch [::p/get-subjects {:plan-url       plan-url
                                       :on-success-evt [:stop-requesting-data]}]]]}))
