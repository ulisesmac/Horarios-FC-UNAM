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

(defn group-details [{:keys [group-id places presentation-url] :as group-data}]
  [rn/view {:style {:border-width 0.5}}
   [rn/view {:style {:flex-direction     :row
                     :padding-vertical   6
                     :padding-horizontal 14
                     :background-color   (theme :primary-500)
                     :align-items        :center
                     :justify-content    :space-between}}

    [rn/text {:style {:color       (theme :basic-100)
                      :font-weight "600"}}
     (str "Grupo " group-id)]
    [rn/text {:style {:color       (theme :basic-100)
                      :font-weight "600"}}
     (str places " lugares")]]
   ;;
   [rn/view {:style {:flex        1
                     :padding     8
                     :row-gap     8
                     :padding-top 6}}
    (map (fn [[role {:keys [person-name days hours classroom extra] :as _details}]]
           ^{:key (str role)}
           [rn/view {:style {:row-gap 1}}
            ;; Person name & role
            [rn/view {:style {:flex-direction  :row
                              :justify-content :space-between}}
             (if person-name
               [rn/text {:style {:color       (theme :basic-800)
                                 :font-weight "500"}}
                person-name]
               [rn/text {:style {:color      (theme :basic-500)
                                 :font-style :italic}}
                "Sin asignar"])
             [rn/text {:style {:color       (theme :secondary-800)
                               :font-weight "500"}}
              (str role)]]

            ;; Days & hours
            [rn/view {:style {:flex-direction :row, :justify-content :space-between}}
             [rn/text {:style {:color (theme :basic-800)}}
              (string/capitalize
               (if (some #(= "a" %) days)
                 (apply str (interpose " " days))
                 (as-> days $
                   (interpose ", " $)
                   (apply str $)
                   (string/reverse $)
                   (string/replace-first $ #" ," " y ")
                   (string/reverse $))))]
             [rn/text {:style {:color (theme :basic-800)}}
              (->> hours
                   (map #(str % "h"))
                   (interpose " a ")
                   (apply str))]]

            ;; Room
            [rn/view {:style {:flex-direction  :row
                              :justify-content :space-between}}
             (when-let [room (:room classroom)]
               [rn/text {:style {:color (theme :basic-800)}}
                (str "En " room)])]

            ;; Extra days, hours & room
            (when extra
              (map (fn [{:keys [days hours classroom]}]
                     ^{:key (str days hours group-id person-name)}
                     [:<> ;; Days & hours
                      [rn/view {:style {:flex-direction :row, :justify-content :space-between}}
                       [rn/text {:style {:color (theme :basic-800)}}
                        (string/capitalize
                         (if (some #(= "a" %) days)
                           (apply str (interpose " " days))
                           (as-> days $
                             (interpose ", " $)
                             (apply str $)
                             (string/reverse $)
                             (string/replace-first $ #" ," " y ")
                             (string/reverse $))))]
                       [rn/text {:style {:color (theme :basic-800)}}
                        (->> hours
                             (map #(str % "h"))
                             (interpose " a ")
                             (apply str))]]
                      ;; Room
                      [rn/view {:style {:flex-direction  :row
                                        :justify-content :space-between}}
                       (when-let [room (:room classroom)]
                         [rn/text {:style {:color (theme :basic-800)}}
                          (str "En " room)])]])
                   extra))])
         (dissoc group-data :presentation-url :places :group-id))]
   ;;
   (when presentation-url
     [rn/view
      [rn/text "Presentación" ;(str presentation-url)
       ]])
   ])

(defn top-panel []
  (let [selected-subject (rf/subscribe [:subject-selected])
        groups-list      (rf/subscribe [::subs/groups-by-subject-list])]
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
          [rn/view {:style {:height             58
                            :padding-vertical   6
                            :padding-horizontal 28
                            :justify-content    :center}}
           [rn/text {:style {:font-size   18
                             :text-align  :center
                             :color       (theme :primary-700)
                             :font-weight "500"}}
            (str @selected-subject)]]

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
                     :height           0.6}}]
   [bottom-panel]])

(defn screen []
  (r/as-element [screen*]))
