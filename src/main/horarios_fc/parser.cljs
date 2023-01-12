(ns horarios-fc.parser
  (:require
   ["fast-xml-parser" :refer [XMLParser]]))

(defn parse-xml [s]
  (-> #js{:ignoreAttributes    false
          :attributeNamePrefix "ATTR/"}
      (XMLParser.)
      (.parse s)
      (js->clj :keywordize-keys true)))
