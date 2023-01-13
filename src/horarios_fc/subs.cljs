(ns horarios-fc.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :app-loading?
 (fn [db _]
   (:app-loading? db)))

(rf/reg-sub
 :schedule
 (fn [db _]
   (:schedule db)))
