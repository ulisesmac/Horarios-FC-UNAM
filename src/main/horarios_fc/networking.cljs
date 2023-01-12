(ns horarios-fc.networking)

(def main-url "https://www.fciencias.unam.mx/docencia/horarios/indice")

(defn http-request! [{:keys [method url on-success on-failure]}]
  (-> (js/fetch url #js{:method (name method)})
      (.then (fn [response]
               (.text response)))
      (.then (fn [text]
               (on-success text)
               text))
      (.catch (fn [error]
                (on-failure error)))))

