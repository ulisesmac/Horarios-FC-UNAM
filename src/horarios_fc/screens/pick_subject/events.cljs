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

(rf/reg-event-fx
 ::request-presentation
 (fn [{db :db} [_ group-id presentation-url group-roles]]
   {:db (-> db
            (update :schedule-shown-content assoc
                    :group-id               group-id
                    :group-roles            group-roles)
            (assoc :requesting-data? true))
    :fx [[:dispatch [::p/get-presentation {:presentation-url presentation-url
                                           :on-success-evt   [::navigate-to-presentation]}]]]}))

(rf/reg-event-fx
 ::navigate-to-presentation
 (fn [{db :db} _]
   {:db       (assoc db :requesting-data? false)
    :navigate {:route-name :schedule-presentation}}))
