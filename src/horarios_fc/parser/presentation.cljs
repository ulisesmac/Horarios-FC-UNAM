(ns horarios-fc.parser.presentation
  (:require
   [horarios-fc.parser.utils :refer [content-path parse-xml] :as p]
   [horarios-fc.networking :as n]
   [clojure.string :as string]))


(def test-url "/docencia/horarios/presentacion/342743")

#_(n/http-request! {:method     :get
                    :url        (str n/domain test-url)
                    :on-success (fn [raw-response]
                                  (prn raw-response))
                    :on-failure #(println "error")})

#_ (let [coll (-> (.parse (p/xml-parser {:preserveOrder true}) --rr)
                  (js/JSON.stringify)
                  (clojure.string/replace #":@" "attributes")
                  (js/JSON.parse)
                  (js->clj :keywordize-keys true)
                  ;;
                  (get-in [0 :html 1 :body 1 :div 1 :div 2 :div 0 :div])
                  ;;
                  )
         html-presentation (vec (filter (fn [{:keys [attributes]}]
                                          (some->> attributes :attr/title (re-find #"Page \d+")))
                                        coll))]
     (->> html-presentation
          (mapcat (fn [html-page]
                    (get-in html-page [:div 0 :div 0 :div])))
          (clojure.walk/postwalk #(if (and (map? %) (:attr/text %))
                                    {:text (:attr/text %)}
                                    %))))

