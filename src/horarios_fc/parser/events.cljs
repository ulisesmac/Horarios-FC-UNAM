(ns horarios-fc.parser.events
  (:require
   [horarios-fc.parser.majors :refer [majors-w-plans!]]
   [horarios-fc.parser.groups :refer [classes-by-subject!]]
   [horarios-fc.parser.subjects :refer [subjects-by-plan!]]
   [re-frame.core :as rf]))

(rf/reg-fx ::get-majors majors-w-plans!)
(rf/reg-event-fx ::get-subjects subjects-by-plan!)
(rf/reg-event-fx ::get-classes classes-by-subject!)
