(ns horarios-fc.parser.presentation
  (:require
   [horarios-fc.parser.utils :refer [content-path parse-xml] :as p]
   [horarios-fc.networking :as n]
   [clojure.walk :as walk]
   [clojure.string :as string]))


(def test-url "/docencia/horarios/presentacion/342743")

#_(n/http-request! {:method     :get
                    :url        (str n/domain test-url)
                    :on-success (fn [raw-response]
                                  (prn raw-response))
                    :on-failure #(println "error")})

(defn parse-presentation [raw-response]
  (let [parsed-html  (-> (p/xml-parser {:preserveOrder true})
                         (.parse raw-response)
                         (js/JSON.stringify)
                         (clojure.string/replace #":@" "attributes")
                         (js/JSON.parse)
                         (js->clj :keywordize-keys true))
        content-path [0 :html 1 :body 1 :div 1 :div 2 :div 0 :div]
        content      (get-in parsed-html content-path)
        html-pages   (vec
                      (filter (fn [{:keys [attributes]}]
                                (some->> attributes :attr/title (re-find #"Page \d+")))
                              content))]
    (->> html-pages
         (mapcat #(get-in % [:div 0 :div 0 :div]))
         (walk/postwalk #(if (and (map? %) (:attr/text %))
                           ;; TODO: check if we only care about :text key
                           (update-keys % name)
                           %)))))

(defn create-url [url]
  (str n/domain url))


