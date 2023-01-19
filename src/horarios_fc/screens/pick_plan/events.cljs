(ns horarios-fc.screens.pick-plan.events
  (:require
   [re-frame.core :as rf]
   [horarios-fc.parser.utils :as utils]
   [horarios-fc.parser.events :as p]))

(rf/reg-event-fx
 ::get-subjects-by-plan
 (fn [{db :db} [_ plan-selected plan-url]]
   {:db              (-> db
                         (update :schedule-shown-content assoc :plan plan-selected)
                         (assoc :requesting-data? true))

    ::p/get-subjects {:plan-resource-url plan-url
                      :on-success        #(rf/dispatch [::store-subjects plan-selected %])
                      :on-failure        #(js/console.error %)}}))

(rf/reg-event-fx
 ::store-subjects
 (fn [{db :db} [_ plan-selected response]]
   (let [selected-major (-> db :schedule-shown-content :major)
         major-idx      (->> selected-major
                             (map-indexed #(vector %1 %2))
                             (some (fn [[idx [major _]]]
                                     (when (= major "Física")
                                       idx))))]
     {:db (-> db
              (assoc :requesting-data? false)
              (assoc-in [:schedule major-idx plan-selected] response))
      ;:navigate {:route-name nil}
      })))
