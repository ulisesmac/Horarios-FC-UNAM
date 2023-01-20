(ns horarios-fc.events
  (:require
   [horarios-fc.parser.events :as p]
   [re-frame.core :as rf]))

(def current-semester
  (let [this-year-august (. (js/Date.) (setMonth 6))
        now              (js/Date.)]
    (if (>= now this-year-august)
      (-> now (.getFullYear) (inc) (str "-" 1))
      (-> now (.getFullYear) (str "-" 2)))))

(rf/reg-event-fx
 :initialize-db
 (fn [_ _]
   {:db {:app-loading? true}
    :fx [[:dispatch [::p/get-majors {:semester       current-semester
                                     :on-success-evt [::set-app-loaded]}]]]}))

(rf/reg-event-db
 ::set-app-loaded
 (fn [db _]
   (-> db
       (assoc :app-loading? false)
       (assoc-in [:schedule-shown-content :semester] current-semester))))

(rf/reg-event-db
 :stop-requesting-data
 (fn [db]
   (assoc db :requesting-data? false)))
