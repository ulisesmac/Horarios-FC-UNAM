(ns horarios-fc.events
  (:require
   [horarios-fc.parser.events :as p]
   [re-frame.core :as rf]))

(defn shown-semester []
  (let [this-year-august (. (js/Date.) (setMonth 6))
        now              (js/Date.)]
    (if (>= now this-year-august)
      (-> now (.getFullYear) (inc) (str "-" 1))
      (-> now (.getFullYear) (str "-" 2)))))

(rf/reg-event-fx
 :initialize-db
 (fn [_ _]
   {:db            {:app-loading? true}
    ::p/get-majors {:semester   (shown-semester)
                    :on-success #(rf/dispatch [::store-majors %])
                    :on-failure (fn [r]
                                  (prn r))}}))

(rf/reg-event-fx
 ::store-majors
 (fn [{db :db} [_ response]]
   {:db (assoc db :schedule response)
    :fx [[:dispatch [::set-app-loaded]]]}))

(rf/reg-event-db
 ::set-app-loaded
 (fn [db _]
   (assoc db :app-loading? false)))

