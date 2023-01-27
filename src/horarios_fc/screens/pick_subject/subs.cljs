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

(rf/reg-sub
 ::groups-by-subject
 :<- [::semesters-w-subjects]
 :<- [:semester-num-selected]
 :<- [:subject-selected]
 (fn [[semesters semester-num subject]]
   (get-in semesters [semester-num :data subject :data])))

(rf/reg-sub
 ::groups-by-subject-list
 :<- [::groups-by-subject]
 (fn [groups]
   (->> groups
        (sort-by (fn [[_semester-num {:keys [idx]}]]
                   idx))
        (mapv (fn [[group-id {:keys [places students schedule presentation-url description]}]]
                (assoc schedule :group-id group-id
                                :places places
                                :students students
                                :description description
                                :presentation-url presentation-url))))))
