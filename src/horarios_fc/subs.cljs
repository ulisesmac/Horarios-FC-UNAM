(ns horarios-fc.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :app-loading?
 (fn [db _]
   (:app-loading? db)))

(rf/reg-sub
 ::schedule
 (fn [db _]
   (:schedule db)))

(rf/reg-sub
 :majors-list
 :<- [::schedule]
 (fn [schedule]
   (mapv (fn [[major url]]
           {:major major
            :url      url})
         schedule)))
