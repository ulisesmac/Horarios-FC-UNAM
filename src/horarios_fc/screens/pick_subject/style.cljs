(ns horarios-fc.screens.pick-subject.style
  (:require
   [horarios-fc.colors :refer [alpha theme theme-color]]))

(defn container []
  {:position           :relative
   :flex               1
   :padding-horizontal 16
   :justify-content    :center
   :row-gap            6
   :background-color   (theme-color :basic-100 :basic-1000)})

;; Divider
(def divider-container {:position           :relative
                        :align-items        :flex-end
                        :padding-horizontal 8})

(defn green-bar []
  {:position         :absolute
   :top              "50%"
   :left             0
   :right            0
   :background-color (theme-color :secondary-800 :secondary-600)
   :height           1})

(def move-button-border {:border-radius 12})
(defn move-button []
  (merge move-button-border
         {:background-color   (theme-color :secondary-700 :secondary-800)
          :justify-content    :center
          :align-items        :center
          :padding-vertical   8
          :padding-horizontal 18}))
(defn move-button-text []
  {:color       (theme-color :basic-100 :basic-200)
   :font-weight "600"
   :z-index     10})

;; TOP
(defn instructions []
  {:text-align  :center
   :font-size   26
   :font-weight "600"
   :color       (theme-color :secondary-700 :secondary-500)})

(defn subject-container []
  {:position            :relative
   :height              58
   :padding-vertical    6
   :padding-horizontal  43
   :margin-horizontal   -15
   :justify-content     :center
   :border-bottom-width 1
   :border-bottom-color (theme-color :basic-300 :basic-700)})

(def subject-text {:font-size 18, :text-align :center})

(defn subject-semester-text []
  {:color       (theme-color :secondary-700 :secondary-600)
   :font-weight "500"})
(defn subject-text-bold []
  {:color       (theme-color :primary-700 :primary-600)
   :font-weight "600"})

(def groups-list {:row-gap            24
                  :padding-bottom     12
                  :padding-horizontal 4})

;; TOP -> group details
(defn group-header []
  {:flex-direction          :row
   :padding-vertical        8
   :padding-horizontal      14
   :background-color        (theme-color :primary-700 :primary-1100)
   :align-items             :center
   :justify-content         :space-between
   :border-top-left-radius  14
   :border-top-right-radius 14})

(defn group-header-text []
  {:color       (theme-color :basic-100 :basic-200)
   :font-weight "700"})

(defn group-header-description []
  {:padding-vertical   8
   :padding-horizontal 14
   :background-color   (theme-color :primary-300 :primary-1200)})

(defn group-header-description-text []
  {:color      (theme-color :basic-1000 :primary-400)
   :font-style :italic})

(defn group-body []
  {:flex                       1
   :row-gap                    18
   :padding-top                6
   :padding-bottom             28
   :padding-horizontal         8
   :border-bottom-right-radius 14
   :border-bottom-left-radius  14
   :background-color           (alpha (theme-color :primary-100 :primary-1200) 50)
   :border-width               0.5
   :border-top-width           0
   :border-color               (theme-color :primary-500 :primary-1000)})
;;
(def presentation {:margin-top         -18
                   :flex-direction     :row
                   :justify-content    :flex-end
                   :padding-horizontal 12})
(def presentation-button-border {:border-radius 16})
(defn presentation-button []
  (merge presentation-button-border
         {:padding-horizontal 18
          :padding-vertical   8
          :border-radius      16
          :background-color   (theme-color :primary-500 :primary-800)}))
(defn presentation-button-text []
  {:font-weight "500"
   :color       (theme-color :basic-100 :basic-200)})
;;
(defn person-data []
  {:justify-content    :center
   :padding-vertical   6
   :padding-horizontal 12
   :border-radius      12
   :background-color   (alpha (theme-color :primary-300 :primary-1000) 40)})

(defn person-name []
  {:color       (theme-color :basic-1000 :primary-100)
   :font-weight "600"})
(defn unassigned-person-name []
  {:color       (theme-color :basic-1000 :basic-300)
   :font-weight "600"})
(defn person-role []
  {:color       (theme-color :secondary-600 :secondary-500)
   :font-weight "600"})
;;
(defn schedule []
  {:flex-direction   :row
   :justify-content  :space-between
   :align-items      :center
   :padding-left     12
   :border-radius    12
   :background-color (alpha (theme-color :primary-100 :primary-1100) 80)})

(defn schedule-days []
  {:color       (theme-color :basic-800 :basic-200)
   :font-weight "500"})

(defn schedule-hours-container []
  {:flex-direction     :row
   :justify-content    :center
   :align-items        :center
   :padding-vertical   4
   :padding-horizontal 8
   :border-radius      16
   :border-width       1
   :border-color       (theme-color :primary-900 :primary-500)})

(def schedule-hours-text-size {:font-size 18})

(defn schedule-hours-text []
  {:color       (theme-color :primary-900 :basic-200)
   :text-align  :center
   :font-weight "600"})
;;
(defn room-container []
  {:justify-content  :center
   :align-items      :flex-start
   :border-radius    12
   :background-color (alpha (theme-color :primary-100 :primary-1200) 60)})
(defn room []
  {:align-self       :flex-start
   :border-width     1
   :padding-vertical 4
   :padding          12
   :border-radius    12
   :border-color     (theme-color :primary-900 :primary-500)})

(defn room-text []
  {:color       (theme-color :primary-900 :basic-200)
   :font-weight "600"})

;; Bottom
(def bottom-container {:row-gap            20
                       :padding-bottom     32
                       :padding-horizontal 8
                       :justify-content    :center})

(defn semester-num []
  {:background-color   (theme-color :primary-600 :primary-1000)
   :border-radius      16
   :padding-vertical   8
   :padding-horizontal 16})

(def semester-num-open {:border-bottom-right-radius 0
                        :border-bottom-left-radius  0})

(def semester-num-content {:flex-direction  :row
                           :justify-content :space-between
                           :align-items     :center})

(defn semester-num-text []
  {:font-size   16
   :font-weight "600"
   :color       (theme-color :basic-100 :basic-200)})

(defn subject-listing-container []
  {:border-width     0.6
   :border-top-width 0
   :border-radius    22
   :border-color     (theme-color :primary-500 :primary-900)})

(def subject-name-radius {:border-radius 16})

(defn subject-name-underlay-color []
  (alpha (theme-color :primary-100 :primary-1000) 80))

(def subject-name-container {:border-radius      16
                             :padding-vertical   8
                             :padding-horizontal 12})
(defn subject-name-text []
  {:font-size 16
   :color     (theme-color :primary-800 :basic-300)})
