(ns horarios-fc.screens.pick-major.events
  (:require
   [re-frame.core :as rf]))

(rf/reg-event-fx
 ::choose-major
 (fn [{db :db} [_ selected-major]]
   {:db       (assoc db :schedule-shown-content {:major selected-major})
    :navigate {:route-name :schedule-plan}}))
