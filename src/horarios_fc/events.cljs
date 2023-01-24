(ns horarios-fc.events
  (:require
   [horarios-fc.parser.events :as p]
   [horarios-fc.util :as util]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 :initialize-db
 (fn [_ _]
   {:db        {:app-loading? true}
    :read-data [:db-state #(rf/dispatch [::load-app-state %])]}))

(rf/reg-event-fx
 ::load-app-state
 (fn [_ [_ state]]
   {:db (assoc state :app-loading? true)
    :fx [[:dispatch [::p/get-majors {:semester       util/current-semester
                                     :on-success-evt [::set-app-loaded]}]]]}))

(rf/reg-event-db
 ::set-app-loaded
 (fn [db _]
   (-> db
       (assoc :app-loading? false)
       (assoc-in [:schedule-shown-content :semester] util/current-semester))))

(rf/reg-event-fx
 :stop-requesting-data
 (fn [{db :db} _]
   (let [new-db (assoc db :requesting-data? false)]
     {:db         new-db
      :store-data [:db-state new-db]})))
