(ns horarios-fc.parser.events
  (:require
   [horarios-fc.parser.groups :as pg]
   [horarios-fc.parser.majors :as pm]
   [horarios-fc.parser.subjects :as ps]
   [re-frame.core :as rf]))

(rf/reg-event-fx
 ::get-majors
 (fn [_ [_ {:keys [semester on-success-evt]}]]
   {:http-request {:method     :GET
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

(rf/reg-event-fx
 ::get-subjects
 (fn [_ [_ {:keys [plan-url on-success-evt]}]]
   {:http-request {:method     :GET
                   :url        (ps/create-url plan-url)
                   :on-success #(rf/dispatch-sync
                                 [::store-subjects on-success-evt (ps/parse-subjects %)])
                   :on-failure #(js/console.error %)}}))

(rf/reg-event-fx
 ::store-subjects
 (fn [{db :db} [_ on-success-evt response]]
   (let [{:keys [semester major plan]} (:schedule-shown-content db)]
     {:db (assoc-in db [:schedule semester :data major :data plan :data] response)
      :fx [[:dispatch on-success-evt]]})))

(rf/reg-event-fx
 ::get-groups
 (fn [_ [_ {:keys [subject-url on-success-evt]}]]
   {:http-request {:method     :GET
                   :url        (pg/create-url subject-url)
                   :on-success #(rf/dispatch
                                 [::store-groups on-success-evt (pg/parse-classes-details %)])
                   :on-failure #(js/console.error %)}}))

(rf/reg-event-fx
 ::store-groups
 (fn [{db :db} [_ on-success-evt response]]
   (let [{:keys [semester major plan semester-num subject]} (:schedule-shown-content db)]
     {:db (assoc-in db
                    [:schedule semester :data major :data plan :data semester-num :data subject :data]
                    response)
      :fx [[:dispatch on-success-evt]]})))
