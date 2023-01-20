(ns horarios-fc.parser.majors
  (:require
   [clojure.string :as string]
   [horarios-fc.networking :as n]
   [horarios-fc.parser.utils :refer [content-path parse-xml]]))

(def ^:private base-url (str n/domain "/docencia/horarios/indice/"))

(defn- get-plan-name [raw-plan-name]
  (string/replace (re-find #"\(.+\)" raw-plan-name)
                  #"\(|\)"
                  ""))

(defn- ->plans-map [html-plans]
  (let [plan-coll (cond-> html-plans
                    (map? html-plans) (vector))]
    (->> plan-coll
         (map-indexed #(vector %1 %2))
         (reduce (fn [m [idx {:attr/keys [text href] :as _html-plan}]]
                   (assoc m (get-plan-name text) {:url  href
                                                  :data nil
                                                  :idx  idx}))
                 {}))))

(defn- parse-majors-w-plans [raw-response]
  (let [parsed-response (parse-xml raw-response)
        content         (get-in parsed-response content-path)
        majors          (:h2 content)
        plans           (map #(->plans-map (:a %))
                             (next (:p content)))]
    (->> (interleave (range) majors plans)
         (partition 3)
         (reduce (fn [acc [idx major plans]]
                   (assoc acc major {:idx  idx
                                     :data plans}))
                 {}))))

(defn create-url [semester]
  (str base-url (string/replace semester #"-" "")))
