(ns horarios-fc.util
  (:require [clojure.string :as string]))

(def current-semester
  (let [this-year-august (. (js/Date.) (setMonth 6))
        now              (js/Date.)]
    (if (>= now this-year-august)
      (-> now (.getFullYear) (inc) (str "-" 1))
      (-> now (.getFullYear) (str "-" 2)))))

(defn previous-semester [semester]
  (let [year (int (re-find #"\d+" semester))]
    (if (string/ends-with? semester "2")
      (str year "-1")
      (str (dec year) "-2"))))

(def selectable-semesters-range
  (take 5 (iterate previous-semester current-semester)))
