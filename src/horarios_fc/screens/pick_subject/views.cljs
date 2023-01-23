(ns horarios-fc.screens.pick-subject.views
  (:require
   [clojure.string :as string]
   [horarios-fc.colors :refer [alpha theme]]
   [horarios-fc.screens.pick-subject.subs :as subs]
   [horarios-fc.screens.pick-subject.events :as events]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn subject [{:keys [semester-num subject url]}]
  [rn/touchable-highlight
   {:style          {:border-radius 18, :margin-horizontal 4}
    :underlay-color (alpha (theme :primary-100) 80)
    :on-press       #(rf/dispatch [::events/get-group-details semester-num subject url])}
   [rn/view {:style {:border-radius      18
                     :padding-vertical   14
                     :padding-horizontal 18}}
    [rn/text {:style {:font-size 16
                      :color     (theme :primary-800)}}
     (str subject)]]])

(defn semester-w-subjects [{:keys [semester-num subjects]}]
  (let [opened? (r/atom false)]
    (fn []
      [rn/view
       [rn/touchable-highlight {:style    {:border-radius 16}
                                :on-press #(swap! opened? not)}
        [rn/view {:style {:background-color   (theme :secondary-600)
                          :border-radius      16
                          :padding-vertical   12
                          :padding-horizontal 18}}
         [rn/view {:style {:flex-direction  :row
                           :justify-content :space-between
                           :align-items     :center}}
          [rn/text {:style {:font-size   18
                            :font-weight "500"
                            :color       (theme :basic-100)}}
           (str semester-num)]

          [rn/text {:style {:font-size   20
                            :font-weight "800"
                            :color       (theme :basic-100)}}
           (if @opened? "-" "+")]]]]
       (when @opened?
         [rn/view
          (map (fn [{:keys [url] :as props}]
                 ^{:key url} [subject (assoc props :semester-num semester-num)])
               subjects)])])))

(def hour->emoji-clock
  {"12"    "🕛", "00" "🕛", "12:30" "🕧", "00:30" "🕧", "1" "🕐", "13" "🕐", "1:30" "🕜",
   "13:30" "🕜", "2" "🕑", "14" "🕑", "2:30" "🕝", "14:30" "🕝", "3" "🕒", "15" "🕒",
   "3:30"  "🕞", "15:30" "🕞", "4" "🕓", "16" "🕓", "4:30" "🕟", "16:30" "🕟", "5" "🕔",
   "17"    "🕔", "5:30" "🕠", "17:30" "🕠", "6" "🕕", "18" "🕕", "6:30" "🕡", "18:30" "🕡",
   "7"     "🕖", "19" "🕖", "7:30" "🕢", "19:30" "🕢", "8" "🕗", "20" "🕗", "8:30" "🕣",
   "20:30" "🕣", "9" "🕘", "21" "🕘", "9:30" "🕤", "21:30" "🕤", "10" "🕙", "22" "🕙",
   "10:30" "🕥", "22:30" "🕥", "11" "🕚", "23" "🕚", "11:30" "🕦", "23:30" "🕦"})

(defn group-details [{:keys [group-id places presentation-url description] :as group-data}]
  [rn/view ;{:style {:border-width 0.5}}
   [rn/view {:style {:flex-direction          :row
                     :padding-vertical        8
                     :padding-horizontal      14
                     :background-color        (theme :primary-700)
                     :align-items             :center
                     :justify-content         :space-between
                     ;:border-radius      8
                     :border-top-left-radius  14
                     :border-top-right-radius 14
                     }}

    [rn/text {:style {:color       (theme :basic-100)
                      :font-weight "700"}}
     (str "Grupo " group-id)]
    [rn/text {:style {:color       (theme :basic-100)
                      :font-weight "700"}}
     (str places " lugares")]]

   (when description
     [rn/view ;; TODO: stylize
      [rn/text {:style {:color (theme :basic-1000)}}
       description]])
   ;;
   [rn/view {:style {:flex                       1
                     :row-gap                    18
                     :padding-top                6
                     :padding-bottom             28
                     :padding-horizontal         8
                     :border-bottom-right-radius 14
                     :border-bottom-left-radius  14
                     :background-color           (alpha (theme :primary-100) 50)
                     ;;
                     :border-width               0.5
                     :border-top-width           0
                     :border-color               (theme :primary-500)
                     }}
    (map (fn [[role {:keys [person-name days hours classroom extra] :as _details}]]
           ^{:key (str role)}
           [rn/view {:style {:row-gap 2}}
            ;; Person name & role
            [rn/view {:style {:justify-content    :center
                              :padding-vertical   6
                              :padding-horizontal 12
                              :border-radius      12
                              :background-color   (alpha (theme :primary-300) 40)}}
             [rn/text
              (if person-name
                [rn/text {:style {:color       (theme :basic-1000)
                                  :font-weight "600"}}
                 (str person-name ", ")]
                [rn/text {:style {:color      (theme :basic-600)
                                  :font-style :italic}}
                 "Sin asignar, "])
              [rn/text {:style {:color       (theme :secondary-600)
                                :font-weight "600"}}
               role]]]
            ;; Days & hours
            (when (or days hours)
              [rn/view {:style {:flex-direction   :row
                                :justify-content  :space-between
                                :align-items      :center
                                :padding-left     12
                                :border-radius    12
                                :background-color (alpha (theme :primary-100) 80)}
                        }
               (when days
                 [rn/view
                  [rn/text {:style {:color       (theme :basic-800)
                                    :font-weight "500"}}
                   (string/capitalize
                    (if (some #(= "a" %) days)
                      (apply str (interpose " " days))
                      (as-> days $
                        (interpose ", " $)
                        (apply str $)
                        (string/reverse $)
                        (string/replace-first $ #" ," " y ")
                        (string/reverse $))))]])
               (when hours
                 [rn/view {:style {:flex-direction     :row
                                   :justify-content    :center
                                   :align-items        :center
                                   :padding-vertical   4
                                   :padding-horizontal 8
                                   :border-radius      16
                                   :border-width       1
                                   :border-color       (theme :primary-900)}}
                  [rn/text {:style {:font-size 18}}
                   (str (hour->emoji-clock (first hours)) " ")]
                  [rn/text {:style {:color       (theme :primary-900)
                                    :text-align  :center
                                    :font-weight "600"}}
                   (->> hours
                        (map #(if (re-find #":" %)
                                %
                                (str % ":00")))
                        (interpose " - ")
                        (apply str))]])])

            ;; Room
            (when-let [room (:room classroom)]
              [rn/view {:style {:justify-content  :center
                                :align-items      :flex-start
                                :border-radius    12
                                :background-color (alpha (theme :primary-100) 60)}}
               [rn/view {:style {:align-self       :flex-start
                                 :border-width     1
                                 :padding-vertical 4
                                 :padding          12
                                 :border-radius    12
                                 :border-color     (theme :primary-900)}}
                [rn/text {:style {:color       (theme :primary-900)
                                  :font-weight "600"}}
                 (str "🏫 " room)]]])


            ;; Extra days, hours & room
            (when extra
              (map (fn [{:keys [days hours classroom]}]
                     ^{:key (str days hours group-id person-name)}
                     [:<> ;; Days & hours
                      (when (or days hours)
                        [rn/view {:style {:flex-direction   :row
                                          :justify-content  :space-between
                                          :align-items      :center
                                          :padding-left     12
                                          :border-radius    12
                                          :background-color (alpha (theme :primary-100) 80)}}
                         (when days
                           [rn/view
                            [rn/text {:style {:color       (theme :basic-800)
                                              :font-weight "500"}}
                             (string/capitalize
                              (if (some #(= "a" %) days)
                                (apply str (interpose " " days))
                                (as-> days $
                                  (interpose ", " $)
                                  (apply str $)
                                  (string/reverse $)
                                  (string/replace-first $ #" ," " y ")
                                  (string/reverse $))))]])
                         (when hours
                           [rn/view {:style {:flex-direction     :row
                                             :justify-content    :center
                                             :align-items        :center
                                             :padding-vertical   4
                                             :padding-horizontal 8
                                             :border-radius      14
                                             :border-width       1
                                             :border-color       (theme :primary-900)}}
                            [rn/text {:style {:font-size 16}}
                             (str (hour->emoji-clock (first hours)) " ")]
                            [rn/text {:style {:color       (theme :primary-900)
                                              :text-align  :center
                                              :font-weight "600"}}
                             (->> hours
                                  (map #(if (re-find #":" %)
                                          %
                                          (str % ":00")))
                                  (interpose " - ")
                                  (apply str))]])])
                      ;; Room
                      (when-let [room (:room classroom)]
                        [rn/view {:style {:justify-content  :center
                                          :align-items      :flex-start
                                          :border-radius    12
                                          :background-color (alpha (theme :primary-100) 60)}}
                         [rn/view {:style {:align-self         :flex-start
                                           :padding-vertical   6
                                           :padding-horizontal 12
                                           :border-radius      12
                                           :border-width       1
                                           :border-color       (theme :primary-900)}}
                          [rn/text {:style {:color       (theme :primary-900)
                                            :text-align  :center
                                            :font-weight "600"}}
                           (str "🏫 " room)]]])])
                   extra))])
         (dissoc group-data :presentation-url :places :group-id :description))]
   ;;
   (when presentation-url
     [rn/view {:style {:margin-top         -18
                       :flex-direction     :row
                       :justify-content    :flex-end
                       :padding-horizontal 12}}
      [rn/touchable-highlight {:style          {:border-radius 16}
                               :active-opacity 0.6
                               :on-press       #(prn presentation-url)}
       [rn/view {:style {:padding-horizontal 18
                         :padding-vertical   8
                         :border-radius      16
                         :background-color   (theme :primary-500)}}
        [rn/text {:style {:font-weight "500"
                          :color       (theme :basic-100)}}
         "📃 Presentación"]]]])
   ])

(defn top-panel []
  (let [selected-subject      (rf/subscribe [:subject-selected])
        selected-semester-num (rf/subscribe [:semester-num-selected])
        groups-list           (rf/subscribe [::subs/groups-by-subject-list])]
    (fn []
      [rn/view {:style {:flex            1
                        :justify-content :center}}
       (if-not @selected-subject
         [rn/text {:style {:text-align  :center
                           :font-size   26
                           :font-weight "600"
                           :color       (theme :secondary-600)}}
          (str "Elige un semestre y materia")]
         ;;
         [rn/view {:style {:flex    1
                           :row-gap 4}}
          ;; subject title
          [rn/view {:style {:position           :relative
                            :height             58
                            :padding-vertical   6
                            :padding-horizontal 28
                            :justify-content    :center}}
           [rn/text {:style {:font-size  18
                             :text-align :center}}
            [rn/text {:style {:color       (theme :secondary-700)
                              :font-weight "500"}}
             (str @selected-semester-num ", ")]
            [rn/text {:style {:color       (theme :primary-700)
                              :font-weight "600"}}
             (str @selected-subject)]]]

          [rn/view {:style {:flex 1}}
           [rn/scroll-view {:content-container-style {:row-gap            18
                                                      :padding-bottom     12
                                                      :padding-horizontal 4}}
            (map (fn [{:keys [group-id] :as group-data}]
                   ^{:key group-id} [group-details group-data])
                 @groups-list)]]]

         )])))


(defn bottom-panel []
  (let [semesters (rf/subscribe [::subs/semesters-w-subjects-list])]
    (fn []
      [rn/view {:style {:flex 1}}
       [rn/scroll-view {:content-container-style {:row-gap            20
                                                  :padding-bottom     32
                                                  :padding-horizontal 8
                                                  :justify-content    :center}}
        (map (fn [{:keys [semester-num] :as props}]
               ^{:key semester-num} [semester-w-subjects props])
             @semesters)]])))

(defn screen* []
  [rn/view {:style {:flex               1
                    :padding-horizontal 16
                    :justify-content    :center
                    :row-gap            12
                    :background-color   (theme :basic-100)}}
   [top-panel]
   [rn/view {:style {:background-color (theme :basic-300)
                     :height           1}}]
   [bottom-panel]])

(defn screen []
  (r/as-element [screen*]))
