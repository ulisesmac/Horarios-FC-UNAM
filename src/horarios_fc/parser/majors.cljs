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
    (reduce (fn [m {:attr/keys [text href] :as _html-plan}]
              (assoc m (get-plan-name text) href))
            {}
            plan-coll)))

(defn- parse-majors-w-plans [raw-response]
  (let [content   (get-in raw-response content-path)
        majors (:h2 content)
        plans     (map #(->plans-map (:a %))
                       (next (:p content)))]
    (mapv #(vector %1 %2) majors plans)))

(defn majors-w-plans!
  "2023-1, 2023-2, ..."
  [{:keys [semester on-success on-failure]}]
  (n/http-request! {:method     :GET
                    :url        (str base-url (string/replace semester #"-" ""))
                    :on-success #(-> %
                                     (parse-xml)
                                     (parse-majors-w-plans)
                                     (on-success))
                    :on-failure (fn [error]
                                  (js/console.error error)
                                  (on-failure error))}))