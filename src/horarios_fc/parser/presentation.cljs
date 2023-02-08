(ns horarios-fc.parser.presentation
  (:require
   [horarios-fc.parser.utils :refer [parse-xml] :as p]
   [horarios-fc.networking :as n]
   [clojure.walk :as walk]
   [clojure.string :as string]))


(def test-url "/docencia/horarios/presentacion/342743")

(defn parse-presentation [raw-response]
  (let [_            (def --rr raw-response)
        parsed-html  (-> (p/xml-parser {:preserveOrder true})
                         (.parse raw-response)
                         (js/JSON.stringify)
                         (clojure.string/replace #":@" "attributes")
                         (js/JSON.parse)
                         (js->clj :keywordize-keys true))
        content-path [0 :html 1 :body 1 :div 1 :div 2 :div 0 :div]
        content      (get-in parsed-html content-path)]
    (->> content
         (drop 6)
         (walk/postwalk #(if (and (map? %) (:attr/text %))
                           {:text (:attr/text %)}
                           %)))))

#_(vec
   (filter (fn [{:keys [attributes]}]
             (some->> attributes :attr/title (re-find #"Page \d+")))
           content))
;(mapcat #(get-in % [:div 0 :div 0 :div]))

(defn create-url [url]
  (str n/domain url))


