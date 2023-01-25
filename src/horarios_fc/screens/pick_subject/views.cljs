(ns horarios-fc.screens.pick-subject.views
  (:require
   [clojure.string :as string]
   [horarios-fc.colors :refer [alpha theme]]
   [horarios-fc.screens.pick-subject.events :as events]
   [horarios-fc.screens.pick-subject.style :as style]
   [horarios-fc.components.requesting-data :refer [requesting-data]]
   [horarios-fc.screens.pick-subject.subs :as subs]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn subject [{:keys [semester-num subject url]}]
  [rn/touchable-highlight
   {:style          {:border-radius 16}
    :underlay-color (alpha (theme :primary-100) 80)
    :on-press       #(rf/dispatch [::events/get-group-details semester-num subject url])}
   [rn/view {:style {:border-radius      16
                     :padding-vertical   6
                     :padding-horizontal 12}}
    [rn/text {:style {:font-size 16
                      :color     (theme :primary-800)}}
     (str subject)]]])

(defn semester-w-subjects [{:keys [semester-num subjects]}]
  (let [opened? (r/atom false)]
    (fn []
      [rn/view {:style (when @opened?
                         {:border-width     0.6
                          :border-top-width 0
                          :border-radius    22
                          :border-color     (theme :primary-500)})}
       [rn/touchable-highlight {:style    {:border-radius 16}
                                :on-press #(swap! opened? not)}
        [rn/view {:style (merge style/semester-num
                                (when @opened? style/semester-num-open))}
         [rn/view {:style style/semester-num-content}
          [rn/text {:style style/semester-num-text}
           (str semester-num)]
          [rn/text {:style (assoc style/semester-num-text :font-size 22)}
           (if @opened? "-" "+")]]]]
       (when @opened?
         [rn/view {:style {:row-gap 2, :padding 6}}
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

(defn group-header [{:keys [group-id places description]}]
  [rn/view
   [rn/view {:style style/group-header}
    [rn/text {:style style/group-header-text}
     (str "Grupo " group-id)]
    [rn/text {:style style/group-header-text}
     (str places " lugares")]]
   (when description
     [rn/view {:style style/group-header-description}
      [rn/text {:style style/group-header-description-text}
       (str "📖 " (string/capitalize description))]])])

(defn- person-info [{:keys [person-name role]}]
  [rn/view {:style style/person-data}
   [rn/text
    (if person-name
      [rn/text {:style style/person-name}
       (str person-name ", ")]
      [rn/text {:style style/unassigned-person-name}
       "Sin asignar, "])
    [rn/text {:style style/person-role}
     role]]])

(defn- schedule-info [{:keys [days hours]}]
  [rn/view {:style style/schedule}
   (when days
     (let [days-str (string/capitalize (if (some #(= "a" %) days)
                                         (apply str (interpose " " days))
                                         (as-> days $
                                           (interpose ", " $)
                                           (apply str $)
                                           (string/reverse $)
                                           (string/replace-first $ #" ," " y ")
                                           (string/reverse $))))]
       [rn/view
        [rn/text {:style style/schedule-days}
         days-str]]))
   (when hours
     (let [emoji-hour-str (str (hour->emoji-clock (first hours)) " ")
           hour-range-str (->> hours
                               (map #(if (re-find #":" %)
                                       %
                                       (str % ":00")))
                               (interpose " - ")
                               (apply str))]
       [rn/view {:style style/schedule-hours-container}
        [rn/text {:style style/schedule-hours-text-size}
         emoji-hour-str]
        [rn/text {:style style/schedule-hours-text}
         hour-range-str]]))])

(defn- room-info [{:keys [room]}]
  [rn/view {:style style/room-container}
   [rn/view {:style style/room}
    [rn/text {:style style/room-text}
     (str "🏫 " room)]]])

(defn extra-schedule-info [{:keys [days hours classroom]}]
  [:<> ;; Days & hours
   (when (or days hours)
     [schedule-info {:days days, :hours hours}])
   ;; Room
   (when-let [room (:room classroom)]
     [room-info {:room room}])])

(defn group-info [{:keys [group-id person-name days hours classroom extra role] :as _details}]
  [rn/view {:style {:row-gap 2}}
   ;; Person name & role
   [person-info {:person-name person-name, :role role}]
   ;; Days & hours
   (when (or days hours)
     [schedule-info {:days days, :hours hours}])
   ;; Room
   (when-let [room (:room classroom)]
     [room-info {:room room}])
   ;; Extra days, hours & room
   (when extra
     (map (fn [{:keys [days hours classroom]}]
            ^{:key (str days hours group-id person-name)}
            [extra-schedule-info {:days      days
                                  :hours     hours
                                  :classroom classroom}])
          extra))])

(defn presentation-button [{:keys [presentation-url]}]
  [rn/view {:style style/presentation}
   [rn/touchable-highlight {:style          style/presentation-button-border
                            :active-opacity 0.6
                            :on-press       #(prn presentation-url)}
    [rn/view {:style style/presentation-button}
     [rn/text {:style style/presentation-button-text}
      "📃 Presentación"]]]])

(defn group-details [{:keys [group-id places presentation-url description] :as group-data}]
  [rn/view
   [group-header {:group-id    group-id
                  :places      places
                  :description description}]
   [rn/view {:style style/group-body}
    (map (fn [[role {:keys [person-name days hours classroom extra] :as _details}]]
           ^{:key (str group-id person-name role days hours)}
           [group-info {:group-id    group-id
                        :role        role
                        :person-name person-name
                        :days        days
                        :hours       hours
                        :classroom   classroom
                        :extra       extra}])
         (dissoc group-data :presentation-url :places :group-id :description))]
   ;;
   (when presentation-url
     [presentation-button {:presentation-url presentation-url}])])

(defn subject-title [{:keys [semester-num subject]}]
  [rn/view {:style style/subject-container}
   [rn/text {:style style/subject-text}
    [rn/text {:style style/subject-semester-text}
     (str semester-num ", ")]
    [rn/text {:style style/subject-text-bold}
     (str subject)]]])

(defn top-panel [{:keys [flex-size]}]
  (let [selected-subject      (rf/subscribe [:subject-selected])
        selected-semester-num (rf/subscribe [:semester-num-selected])
        groups-list           (rf/subscribe [::subs/groups-by-subject-list])]
    (fn [{:keys [flex-size]}]
      [rn/view {:style {:flex            flex-size
                        :justify-content :center}}
       (if-not @selected-subject
         ;; Instructions
         [rn/text {:style (style/instructions)}
          (str "Elige un semestre y materia")]
         [rn/view {:style {:flex    1
                           :row-gap 4}}
          ;; Subject title
          [subject-title {:semester-num @selected-semester-num
                          :subject      @selected-subject}]
          ;; List of groups
          [rn/view {:style {:flex 1}}
           [rn/scroll-view {:content-container-style style/groups-list}
            (map (fn [{:keys [group-id] :as group-data}]
                   ^{:key group-id} [group-details group-data])
                 @groups-list)]]])])))


(defn bottom-panel []
  (let [semesters (rf/subscribe [::subs/semesters-w-subjects-list])]
    (fn []
      [rn/view {:style {:flex 1}}
       [rn/scroll-view {:content-container-style style/bottom-container}
        (map (fn [{:keys [semester-num] :as props}]
               ^{:key semester-num} [semester-w-subjects props])
             @semesters)]])))

(defn divider [{:keys [on-press-move move-text]}]
  [rn/view {:style style/divider-container}
   [rn/view {:style style/gray-bar}]
   [rn/touchable-highlight {:style    style/move-button-border
                            :on-press on-press-move}
    [rn/view {:style style/move-button}
     [rn/text {:style style/move-button-text}
      move-text]]]])

(defn screen* []
  (let [bigger-top? (r/atom false)]
    (fn []
      [rn/view {:style style/container}
       [requesting-data]
       [top-panel {:flex-size (if @bigger-top? 4 1)}]
       [divider {:on-press-move #(swap! bigger-top? not)
                 :move-text     (str "Mover " (if @bigger-top? "⬆" "⬇"))}]
       [bottom-panel]])))

(defn screen [] (r/as-element [screen*]))
