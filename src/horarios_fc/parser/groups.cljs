(ns horarios-fc.parser.groups
  (:require
   [clojure.string :as string]
   [horarios-fc.networking :as n]
   [horarios-fc.parser.utils :refer [content-path parse-xml]]))

(def ->long-day-str
  {"lu" "lunes"
   "ma" "martes"
   "mi" "miércoles"
   "ju" "jueves"
   "vi" "viernes"
   "sá" "sábado"})

(defn ->days-vec [s]
  (some-> s
    (string/replace #"lu|ma|mi|ju|vi|sá" ->long-day-str)
    (string/split #" ")))

(defn ->vec-hours [str-hours]
  (some-> str-hours
    (string/replace #" " "")
    (string/split #"a")))

(defn- ->schedule-map [html-schedule]
  (reduce (fn [m {[title & _ :as table-data] :td}]
            (if (nil? (:attr/text title))
              (let [[_ days hours classroom] table-data
                    [last-key _last-val] (last m) ;; Belongs to the previous title and person
                    extra-days      (->days-vec (:attr/text days))
                    extra-hours     (->vec-hours (:attr/text hours))
                    extra-classroom (when-let [room (:a classroom)]
                                      {:url  (:attr/href room)
                                       :room (as-> (:attr/text room) $
                                               (string/split $ "(")
                                               (update $ 1 #(apply str (drop-last %)))
                                               (interpose " " $)
                                               (reverse $)
                                               (apply str $)
                                               (string/trim $))})]
                (update-in m [last-key :extra] conj {:days      extra-days
                                                     :hours     extra-hours
                                                     :classroom extra-classroom}))
              (let [[_ person days hours classroom] table-data]
                (assoc m (:attr/text title)
                         {:person-name (:attr/text (:a person))
                          :url         (:attr/href (:a person))
                          :days        (->days-vec (:attr/text days))
                          :hours       (->vec-hours (:attr/text hours))
                          :classroom   (when-let [room (:a classroom)]
                                         {:url  (:attr/href room)
                                          :room (as-> (:attr/text room) $
                                                  (string/split $ "(")
                                                  (update $ 1 #(apply str (drop-last %)))
                                                  (interpose " " $)
                                                  (reverse $)
                                                  (apply str $)
                                                  (string/trim $))})}))))
          {}
          html-schedule))

(defn- group-html-classes [html-response]
  (-> html-response
      (string/replace #"</p>\s*</form>\s*<div" "</p></form><div><div")
      (string/replace #"<div style=\"padding-top: 10px" "</div><div><div style=\"padding-top: 10px")
      (string/replace #"</table>\s*</div>\s*<p>" "</table></div></div><p>")))

(defn parse-classes-details [raw-response]
  (let [fixed-html  (group-html-classes raw-response)
        parsed-html (parse-xml fixed-html)
        groups      (->> (get-in parsed-html content-path) :div (drop 2))]
    (->> groups
         (map-indexed #(vector %1 %2))
         (reduce (fn [m [idx group]]
                   (let [group-data   (as-> (:div group) $
                                        (if (vector? $)
                                          [(first $) (if (:i (get $ 1))
                                                       (if (:ul (get $ 2))
                                                         (merge (get $ 1) (get $ 2))
                                                         (get $ 1))
                                                       (get $ 1))]
                                          [$ nil]))
                         group-id     (some->> group-data first :strong (re-find #"\d+"))
                         description  (->> group-data second :i)
                         num-places   (some->> group-data first :attr/text (re-find #"\d+") int)
                         presentation (some->> group-data second :ul :li :a :attr/href)
                         schedule     (-> group :table :tr ->schedule-map)]
                     (assoc m group-id {:idx              idx
                                        :description      description
                                        :places           num-places
                                        :schedule         schedule
                                        :presentation-url presentation})))
                 {}))))

(defn create-url [group-url]
  (str n/domain group-url))
