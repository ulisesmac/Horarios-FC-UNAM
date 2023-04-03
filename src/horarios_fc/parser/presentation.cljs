(ns horarios-fc.parser.presentation
  (:require
    [clojure.string :as string]
    [clojure.walk :as walk]
    [horarios-fc.networking :as n]
    [horarios-fc.parser.utils :refer [parse-xml] :as p]))

(def test-url "/docencia/horarios/presentacion/342743")

(def html-entities {"&acute;"  "´"
                    "&bull;"   "•"
                    "&hellip;" "…"
                    "&middot;" "·"
                    "&ndash;"  "—"
                    "&ordm;"   "º"
                    "&rarr;"   "→"
                    ":@"       "attributes"})

(def html-entities-regex
  (->> html-entities
       (keys)
       (interpose "|")
       (apply str)
       (re-pattern)))

(defn replace-entities [html]
  (string/replace html html-entities-regex html-entities))

(defn parse-presentation [raw-response]
  (let [parsed-html  (-> (p/xml-parser {:preserveOrder true})
                         (.parse raw-response)
                         (js/JSON.stringify)
                         (replace-entities)
                         (js/JSON.parse)
                         (js->clj :keywordize-keys true))
        content-path [0 :html 1 :body 1 :div 1 :div 2 :div 0 :div]
        content      (get-in parsed-html content-path)]
    (->> content
         (drop 6)
         (walk/postwalk #(if (and (map? %) (:attr/text %))
                           {:text (:attr/text %)}
                           %))
         (walk/postwalk #(if (map? %)
                           (into [] cat %)
                           %))
         (walk/postwalk #(if (and
                               (vector? %)
                               (< (count %) 2))
                           (vec (mapcat identity %))
                           %)))))

(defn create-url [url]
  (str n/domain url))
