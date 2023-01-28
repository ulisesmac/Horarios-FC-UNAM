(ns horarios-fc.events
  (:require
   [horarios-fc.parser.events :as p]
   [horarios-fc.util :as util]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 :initialize-db
 (fn [_ _]
   {:db        {:app-loading? true, :requesting-data? true, :splash-screen-mounted? true}
    :read-data [:db-state #(rf/dispatch [::load-app-state %])]}))

(rf/reg-event-fx
 ::load-app-state
 (fn [_ [_ state]]
   {:db (assoc state :app-loading? true
                     :requesting-data? true
                     :splash-screen-mounted? true)
    :fx [[:dispatch [::p/get-majors {:semester       util/current-semester
                                     :on-success-evt [::request-subjects]
                                     :on-failure-evt [::set-app-loaded]}]]]}))

(rf/reg-event-fx
 ::request-subjects
 (fn [{db :db} _]
   (let [next-evt (if-let [plan (-> db :schedule-shown-content :plan)]
                    [::p/get-subjects {:plan-url       plan
                                       :on-success-evt [::request-groups]
                                       :on-failure-evt [::set-app-loaded]}]
                    [::set-app-loaded])]
     {:fx [[:dispatch next-evt]]})))

(rf/reg-event-fx
 ::request-groups
 (fn [{db :db} _]
   (let [app-loaded-evt [::set-app-loaded]
         next-evt       (if-let [subject (-> db :schedule-shown-content :subject)]
                          [::p/get-groups {:subject-url    subject
                                           :on-success-evt app-loaded-evt
                                           :on-failure-evt [::set-app-loaded]}]
                          app-loaded-evt)]
     {:fx [[:dispatch next-evt]]})))

(rf/reg-event-db
 ::set-app-loaded
 (fn [db _]
   (-> db
       (assoc :app-loading? false
              :requesting-data? false
              :splash-screen-mounted? true)
       (assoc-in [:schedule-shown-content :semester] util/current-semester))))

(rf/reg-event-fx
 :stop-requesting-data
 (fn [{db :db} _]
   (let [new-db (assoc db :requesting-data? false)]
     {:db         new-db
      :store-data [:db-state new-db]})))
