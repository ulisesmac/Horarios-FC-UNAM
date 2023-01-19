(ns horarios-fc.parser.groups
  (:require
   [clojure.string :as string]
   [horarios-fc.networking :as n]
   [horarios-fc.parser.utils :refer [content-path parse-xml]]))

(defn- ->schedule-map [html-schedule]
  (reduce (fn [m {[title person days hours] :td}]
            (assoc m (:attr/text title)
                     {:person-name         (:attr/text (:a person))
                      :person-resource-url (:attr/href (:a person))
                      :days                (:attr/text days)
                      :hours               (:attr/text hours)}))
          {}
          html-schedule))

(defn- get-classes-details [raw-response]
  (let [groups (->> (get-in raw-response content-path) :div (drop 2))]
    (vec
     (reduce (fn [m group]
               (let [group-data   (as-> (:div group) $
                                    (if (vector? $)
                                      [(first $) (second $)]
                                      [$ nil]))
                     group-id     (->> group-data first :strong (re-find #"\d+"))
                     num-places   (->> group-data first :attr/text (re-find #"\d+") int)
                     presentation (some->> group-data second :ul :li :a :attr/href)
                     schedule     (-> group :table :tr ->schedule-map)]
                 (assoc m group-id {:places                   num-places
                                    :schedule                 schedule
                                    :presentation-resorce-url presentation})))
             {}
             groups))))

(defn- group-html-classes [html-response]
  (-> html-response
      (string/replace #"</p>\s*</form>\s*<div" "</p></form><div><div")
      (string/replace #"<div style=\"padding-top: 10px" "</div><div><div style=\"padding-top: 10px")
      (string/replace #"</table>\s*</div>\s*<p>" "</table></div></div><p>")))

(defn groups-by-subject!
  [{:keys [group-resource-url on-success on-failure]}]
  (n/http-request! {:method     :GET
                    :url        (str n/domain group-resource-url)
                    :on-success #(-> %
                                     (group-html-classes)
                                     (parse-xml)
                                     (get-classes-details)
                                     (on-success))
                    :on-failure (fn [error]
                                  (js/console.error error)
                                  (on-failure error))}))
