(ns horarios-fc.parser.utils
  (:require
   ["fast-xml-parser" :refer [XMLParser]]))

(def ^:private xml-parser
  (XMLParser. #js{:ignoreAttributes    false
                  :attributeNamePrefix "attr/"
                  :textNodeName        "attr/text"}))

(def content-path [:html :body :div 1 :div 1 :div 2 :div])

(defn parse-xml [s]
  (js->clj (.parse xml-parser s) :keywordize-keys true))
