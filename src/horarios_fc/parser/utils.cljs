(ns horarios-fc.parser.utils
  (:require
   [clojure.string :as string]
   ["fast-xml-parser" :refer [XMLParser]]))

(defn xml-parser [& [opts]]
  (let [js-params (-> {:ignoreAttributes    false
                       :attributeNamePrefix "attr/"
                       :textNodeName        "attr/text"}
                      (merge opts)
                      (clj->js))]
    (prn js-params)
    (XMLParser. js-params)))

(def content-path [:html :body :div 1 :div 1 :div 2 :div])

(defn parse-xml [s & {:as opts}]
  (-> (xml-parser opts)
      (.parse s)
      #_(js->clj :keywordize-keys true)))

(defn url-resource? [s]
  (string/starts-with? s "/"))
