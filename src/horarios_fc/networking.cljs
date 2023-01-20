(ns horarios-fc.networking
  (:require [re-frame.core :as rf]))

(def domain "https://archive.fciencias.unam.mx")

(defn http-request! [{:keys [method url on-success on-failure]}]
  (-> (js/fetch url #js{:method (name method)})
      (.then (fn [response]
               (.text response)))
      (.then (fn [text]
               (on-success text)
               text))
      (.catch (fn [error]
                (on-failure error)))))

(rf/reg-fx :http-request http-request!)
