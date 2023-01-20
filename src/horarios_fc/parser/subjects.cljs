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
    (->> coll-subjects
         (map-indexed #(vector %1 %2))
         (reduce (fn [m [idx {{:attr/keys [text href] :as _subjects} :a}]]
                   (assoc m (get-subject-name text) {:url  href
                                                     :idx  idx
                                                     :data nil}))
                 {}))))

(defn- group-html-subjects [html-response]
  (-> html-response
      (string/replace #"</h2>\s*<div>" "</h2><div><div>")
      (string/replace #"</div>\s*<h2>" "</div></div><h2>")
      (string/replace #"</div>\s*</div>\s*<p>" "</div></div></div><p>")))

(defn- parse-subjects [raw-response]
  (let [fixed-html           (group-html-subjects raw-response)
        parsed-response      (parse-xml fixed-html)
        content              (get-in parsed-response content-path)
        num-semesters        (:h2 content)
        subjects-by-semester (map ->subjects-map (next (:div content)))]

    (reduce (fn [acc [idx num-semester subjects]]
              (assoc acc num-semester {:idx  idx
                                       :data subjects}))
            {}
            (partition 3 (interleave (range) num-semesters subjects-by-semester)))))

(defn create-url [plan-url]
  (str n/domain plan-url))
