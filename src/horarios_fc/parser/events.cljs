(ns horarios-fc.parser.events
  (:require
   [horarios-fc.parser.groups :as pg]
   [horarios-fc.parser.majors :as pm]
   [horarios-fc.parser.presentation :as pp]
   [horarios-fc.parser.subjects :as ps]
   [re-frame.core :as rf]))

(defn today-at-6 []
  (.setHours (js/Date.) 6))

(rf/reg-event-fx
 ::get-majors
 (fn [{db :db} [_ {:keys [semester on-success-evt on-failure-evt]}]]
   (let [stored-time (get-in db [:schedule semester :date])
         stored-data (get-in db [:schedule semester :data])]
     ;; TODO: replicate in other places
     (if (or (empty? stored-data)
             (< stored-time (today-at-6)))
       {:http-request {:method     :GET
                       :url        (pm/create-url semester)
                       :on-success #(rf/dispatch [::store-majors
                                                  semester
                                                  on-success-evt
                                                  (pm/parse-majors-w-plans %)])
                       :on-failure #(rf/dispatch on-failure-evt)}}
       {:fx [[:dispatch on-success-evt]]}))))

(rf/reg-event-fx
 ::store-majors
 (fn [{db :db} [_ semester on-success-evt response]]
   {:db (-> db
            (assoc-in [:schedule semester :url] semester)
            (assoc-in [:schedule semester :data] response)
            (assoc-in [:schedule semester :date] (js/Date.)))
    :fx [[:dispatch on-success-evt]]}))

(rf/reg-event-fx
 ::get-subjects
 (fn [{db :db} [_ {:keys [plan-url on-success-evt on-failure-evt]}]]
   (let [{:keys [semester major plan]} (:schedule-shown-content db)
         stored-time (get-in db [:schedule semester :data major :data plan :date])]
     (if (< stored-time (today-at-6))
       {:http-request {:method     :GET
                       :url        (ps/create-url plan-url)
                       :on-success #(rf/dispatch
                                     [::store-subjects on-success-evt (ps/parse-subjects %)])
                       :on-failure #(rf/dispatch on-failure-evt)}}
       {:fx [[:dispatch on-success-evt]]}))))

(rf/reg-event-fx
 ::store-subjects
 (fn [{db :db} [_ on-success-evt response]]
   (let [{:keys [semester major plan]} (:schedule-shown-content db)]
     {:db (-> db
              (assoc-in [:schedule semester :data major :data plan :data] response)
              (assoc-in [:schedule semester :data major :data plan :date] (js/Date.)))
      :fx [[:dispatch on-success-evt]]})))

(rf/reg-event-fx
 ::get-groups
 (fn [{db :db} [_ {:keys [subject-url on-success-evt on-failure-evt]}]]
   (let [{:keys [semester major plan semester-num subject]} (:schedule-shown-content db)
         stored-time (get-in db [:schedule semester :data major :data plan :data
                                 semester-num :data subject :date])]
     (if (< stored-time (today-at-6))
       {:http-request {:method     :GET
                       :url        (pg/create-url subject-url)
                       :on-success #(rf/dispatch
                                     [::store-groups on-success-evt (pg/parse-classes-details %)])
                       :on-failure #(rf/dispatch on-failure-evt)}}
       {:fx [[:dispatch on-success-evt]]}))))

(rf/reg-event-fx
 ::store-groups
 (fn [{db :db} [_ on-success-evt response]]
   (let [{:keys [semester major plan semester-num subject]} (:schedule-shown-content db)
         path [:schedule semester :data major :data plan :data semester-num :data subject]]
     {:db (-> db
              (assoc-in (conj path :data) response)
              (assoc-in (conj path :date) (js/Date.)))
      :fx [[:dispatch on-success-evt]]})))

(rf/reg-event-fx
 ::get-presentation
 (fn [_ [_ {:keys [presentation-url on-success-evt on-failure-evt]}]]
   {:http-request {:method     :GET
                   :url        (pp/create-url presentation-url)
                   :on-success #(rf/dispatch
                                 [::store-presentation on-success-evt (pp/parse-presentation %)])
                   :on-failure #(rf/dispatch on-failure-evt)}}))

(rf/reg-event-fx
 ::store-presentation
 (fn [{db :db} [_ on-success-evt parsed-presentation]]
   {:db (assoc db :shown-presentation parsed-presentation)
    :fx [[:dispatch on-success-evt]]}))
