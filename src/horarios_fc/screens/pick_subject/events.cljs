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
 (fn [{db :db} [_ {:keys [group-id places students presentation-url description] :as group-data}]]
   {:db (-> db
            (update :schedule-shown-content assoc
                    :group-details {:group-id    group-id
                                    :places      places
                                    :students    students
                                    :description description
                                    :group-roles (dissoc group-data :presentation-url :places :group-id :description :students)})
            (assoc :requesting-data? true))
    :fx [[:dispatch [::p/get-presentation {:presentation-url presentation-url
                                           :on-success-evt   [::navigate-to-presentation]}]]]}))

(rf/reg-event-fx
 ::navigate-to-presentation
 (fn [{db :db} _]
   {:db       (assoc db :requesting-data? false)
    :navigate {:route-name :schedule-presentation}}))
