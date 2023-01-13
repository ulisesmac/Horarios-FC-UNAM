(ns horarios-fc.parser.subjects
  (:require
   [clojure.string :as string]
   [horarios-fc.networking :as n]
   [horarios-fc.parser.utils :refer [content-path parse-xml]]))

(defn- get-subject-name [raw-subject-name]
  (->> raw-subject-name
       (reverse)
       (drop-while #(not= "," %))
       (rest)
       (reverse)
       (apply str)))

(defn- ->subjects-map [html-subjects]
  (let [subjects      (:div html-subjects)
        coll-subjects (cond-> subjects
                        (map? subjects) (vector))]
    (reduce (fn [m {{:attr/keys [text href] :as _subjects} :a}]
              (assoc m (get-subject-name text) href))
            {}
            coll-subjects)))

(defn get-subjects-by-groups [raw-response]
  (let [content           (get-in raw-response content-path)
        groups            (:h2 content)
        subjects-by-group (map ->subjects-map (next (:div content)))]
    (map #(vector %1 %2) groups subjects-by-group)))

(defn- group-html-subjects [html-response]
  (-> html-response
      (string/replace #"</h2>\s*<div>" "</h2><div><div>")
      (string/replace #"</div>\s*<h2>" "</div></div><h2>")
      (string/replace #"</div>\s*</div>\s*<p>" "</div></div></div><p>")))

(defn subjects-by-plan!
  [{:keys [plan-resource-url on-success on-failure]}]
  (n/http-request! {:url        (str n/domain plan-resource-url)
                    :method     :GET
                    :on-success #(-> %
                                     (group-html-subjects)
                                     (parse-xml)
                                     (get-subjects-by-groups)
                                     (on-success))
                    :on-failure (fn [error]
                                  (js/console.error error)
                                  (on-failure error))}))
