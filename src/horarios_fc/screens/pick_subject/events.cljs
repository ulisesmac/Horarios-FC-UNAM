(ns horarios-fc.screens.pick-subject.events
  (:require
   [horarios-fc.parser.events :as p]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 ::get-group-details
 (fn [{db :db} [_ semester-num subject subject-url]]
   {:db (-> db
            (update :schedule-shown-content assoc
                    :semester-num semester-num, :subject subject)
            (assoc :requesting-data? true))
    :fx [[:dispatch [::p/get-groups {:subject-url    subject-url
                                     :on-success-evt [:stop-requesting-data]}]]]}))
