(ns horarios-fc.screens.pick-subject.subs
  (:require
   [horarios-fc.screens.pick-major.subs :as pick-major.subs]
   [horarios-fc.screens.pick-plan.subs :as pick-plan.subs]
   [re-frame.core :as rf]))

(rf/reg-sub
 ::semesters-w-subjects
 :<- [::pick-plan.subs/majors-by-semester]
 :<- [::pick-major.subs/major-selected]
 :<- [:plan-selected]
 (fn [[majors major-selected plan-selected]]
   (get-in majors [major-selected :data plan-selected :data])))

(rf/reg-sub
 ::semesters-w-subjects-list
 :<- [::semesters-w-subjects]
 (fn [semesters-w-subjects]
   (->> semesters-w-subjects
        (sort-by (fn [[_semester-num {:keys [idx]}]]
                   idx))
        (mapv (fn [[semester-num {subjects :data}]]
                {:semester-num semester-num
                 :subjects     (->> subjects
                                    (sort-by (fn [[_subject {:keys [idx]}]] idx))
                                    (mapv (fn [[subject {:keys [url _data]}]]
                                            {:subject subject
                                             :url     url})))})))))
