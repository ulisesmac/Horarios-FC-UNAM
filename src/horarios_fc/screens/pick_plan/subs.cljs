(ns horarios-fc.screens.pick-plan.subs
  (:require
   [clojure.string :as string]
   [horarios-fc.screens.pick-major.subs :as pick-major-subs]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::plans-list
 :<- [::pick-major-subs/majors-list]
 :<- [::pick-major-subs/major-selected]
 (fn [[majors-list major-selected]]
   (let [plans-found (some (fn [{:keys [major plans]}]
                             (when (= major major-selected)
                               plans))
                           majors-list)]
     (mapv (fn [[plan url]]
             {:plan (string/capitalize plan)
              :url  url})
           plans-found))))
