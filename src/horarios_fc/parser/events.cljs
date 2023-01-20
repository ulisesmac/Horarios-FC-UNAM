(ns horarios-fc.parser.events
  (:require
   [horarios-fc.parser.majors :as pm]
   [horarios-fc.parser.groups :refer [groups-by-subject!] :as pg]
   [horarios-fc.parser.subjects :refer [subjects-by-plan!] :as ps]
   [re-frame.core :as rf]))

(rf/reg-event-fx ::get-subjects subjects-by-plan!)
(rf/reg-event-fx ::get-groups groups-by-subject!)

(rf/reg-event-fx
 ::get-majors
 (fn [{db :db} [_ {:keys [semester on-success-evt]}]]
   {:db           db
    :http-request {:method     :GET
                   :url        (pm/create-url semester)
                   :on-success #(rf/dispatch-sync [::store-majors
                                                   semester
                                                   on-success-evt
                                                   (pm/parse-majors-w-plans %)])
                   :on-failure #(js/console.error %)}}))

(rf/reg-event-fx
 ::store-majors
 (fn [{db :db} [_ semester on-success-evt response]]
   {:db (assoc-in db [:schedule semester] {:url  semester
                                           :data response})
    :fx [[:dispatch on-success-evt]]}))

