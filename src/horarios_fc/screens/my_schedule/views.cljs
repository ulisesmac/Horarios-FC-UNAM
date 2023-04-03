(ns horarios-fc.screens.my-schedule.views
  (:require
   [horarios-fc.colors :refer [theme-color]]
   [react-native :as rn]
   [reagent.core :as r]))

(def subject->color
  {"ALGEBRA"     :green
   "BIOLOGÍA"    :blue
   "CÁLCULO III" :purple
   "CÁLCULO IV"  :cyan})

;; TODO: get screen width

(defn what-to-paint [hour subject m-subjects]
  (let [[start end] (mapv (fn [hour-str]
                            (let [hour-part    (subs hour-str 0 2)
                                  minutes-part (subs hour-str 3 5)]
                              (+ (parse-long hour-part)
                                 (case minutes-part ;; TODO use >=
                                   "00" 0
                                   "15" 0.25
                                   "30" 0.5
                                   "45" 0.75))))
                          (:hours (get m-subjects subject)))]
    (cond
      (= start hour) :starts
      (and (< start hour end)) :in
      (= end hour) :ends
      :else nil)))

(defn schedule-per-day-per-hour [hour day]
  [rn/view {:style {:height             48
                    :width              50
                    :border-left-width  1
                    :border-right-width 1
                    :padding-horizontal 6
                    :border-color       :red}}
   (map (fn [subject]
          (let [what-00 (what-to-paint hour subject day)
                what-15 (what-to-paint (+ hour 0.25) subject day)
                what-30 (what-to-paint (+ hour 0.5) subject day)
                what-45 (what-to-paint (+ hour 0.75) subject day)
                what-60 (what-to-paint (inc hour) subject day)]
            (cond
              (and (= what-00 :starts) (= what-60 :ends))
              [rn/view {:style {:border-radius    25
                                :background-color (subject->color subject)
                                :height           48}}]

              (= what-00 :starts) [rn/view {:style {:border-top-left-radius  25
                                                    :border-top-right-radius 25
                                                    :background-color        (subject->color subject)
                                                    :height                  48}}]

              (= what-60 :ends) [rn/view {:style {:background-color           (subject->color subject)
                                                  :height                     48
                                                  :border-bottom-left-radius  25
                                                  :border-bottom-right-radius 25}}]
              (= what-15 :starts) [rn/view {:style {:border-top-left-radius  25
                                                    :border-top-right-radius 25
                                                    :margin-top              12
                                                    :background-color        (subject->color subject)
                                                    :height                  36}}]
              (= what-15 :ends) [rn/view {:style {:background-color           (subject->color subject)
                                                  :height                     12
                                                  :border-bottom-left-radius  25
                                                  :border-bottom-right-radius 25}}]

              (= what-30 :starts) [rn/view {:style {:border-top-left-radius  25
                                                    :border-top-right-radius 25
                                                    :margin-top              24
                                                    :background-color        (subject->color subject)
                                                    :height                  24}}]
              (= what-30 :ends) [rn/view {:style {:background-color           (subject->color subject)
                                                  :height                     24
                                                  :border-bottom-left-radius  25
                                                  :border-bottom-right-radius 25}}]

              (= what-45 :starts) [rn/view {:style {:border-top-left-radius  25
                                                    :border-top-right-radius 25
                                                    :margin-top              36
                                                    :background-color        (subject->color subject)
                                                    :height                  12}}]
              (= what-45 :ends) [rn/view {:style {:margin-top                 0
                                                  :background-color           (subject->color subject)
                                                  :height                     36
                                                  :border-bottom-left-radius  25
                                                  :border-bottom-right-radius 25}}]
              (= what-00 :in) [rn/view {:style {:background-color (subject->color subject)
                                                :height           48}}]
              )))
        (keys day))
   ])


(defn schedule-per-hour
  [{hour                                                        :hour
    {:keys [monday tuesday wednesday thursday friday saturday]} :schedule}]
  [rn/view {:style {:flex-direction :row
                    :align-items    :center
                    :border-width   1
                    :border-color   :red}}
   [rn/text {:style {:width 50}} hour]

   [schedule-per-day-per-hour hour monday]

   [schedule-per-day-per-hour hour tuesday]
   [schedule-per-day-per-hour hour wednesday]
   [schedule-per-day-per-hour hour thursday]
   [schedule-per-day-per-hour hour friday]
   [schedule-per-day-per-hour hour saturday]
   ]
  )

(defn table-header []
  [rn/view {:style {:flex-direction :row}}
   [rn/text {:style {:width      50
                     :text-align :center}}
    ""]
   [rn/text {:style {:width      50
                     :text-align :center}}
    "L"]
   [rn/text {:style {:width      50
                     :text-align :center}}
    "M"]
   [rn/text {:style {:width      50
                     :text-align :center}}
    "M"]
   [rn/text {:style {:width      50
                     :text-align :center}}
    "J"]
   [rn/text {:style {:width      50
                     :text-align :center}}
    "V"]
   [rn/text {:style {:width      50
                     :text-align :center}}
    "S"]])

(defn screen* []
  (let [schedule      {:monday    {"ALGEBRA"     {:hours ["10:30" "12:45"]}
                                   "BIOLOGÍA"    {:hours ["14:00" "16:00"]}
                                   "CÁLCULO III" {:hours ["17:00" "18:00"]}
                                   "CÁLCULO IV"  {:hours ["19:30" "21:30"]}}
                       :wednesday {"ALGEBRA" {:hours ["08:00" "09:00"]}}
                       :thursday  {"BIOLOGÍA" {:hours ["13:00" "14:30"]}}
                       :saturday  {"CÁLCULO IV" {:hours ["20:00" "21:00"]}}}
        ;;
        all-intervals (for [[_ day] schedule
                            [_ subjects-per-day] day
                            [_ hour] subjects-per-day]
                        hour)
        min-hour      (parse-long (subs (first (sort < (map first all-intervals))) 0 2))
        max-hour      (parse-long (subs (first (sort > (map first all-intervals))) 0 2))
        hour-interval (range min-hour (inc max-hour))]

    [rn/view {:style {:margin-horizontal 15}}
     [table-header]

     (map (fn [hour]
            ^{:key (str hour)}
            [schedule-per-hour {:hour     hour
                                :schedule schedule}])
          hour-interval)
     ])
  )

(defn screen []
  (r/as-element [screen*]))
