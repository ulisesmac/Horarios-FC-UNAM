(ns horarios-fc.screens.pick-subject.style
  (:require
   [horarios-fc.colors :refer [alpha theme]]))

(def container
  {:flex               1
   :padding-horizontal 16
   :justify-content    :center
   :row-gap            12
   :background-color   (theme :basic-100)})

;; Divider
(def divider-container {:position           :relative
                        :align-items        :flex-end
                        :padding-horizontal 8})

(def gray-bar {:position         :absolute
               :top              "50%"
               :left             0
               :right            0
               :background-color (theme :basic-300)
               :height           1})

(def move-button-border {:border-radius 12})
(def move-button (merge move-button-border
                        {:background-color   (theme :secondary-700)
                         :justify-content    :center
                         :align-items        :center
                         :padding-vertical   8
                         :padding-horizontal 18}))
(def move-button-text {:color       (theme :basic-100)
                       :font-weight "600"
                       :z-index     10})

;; TOP
(defn instructions []
  {:text-align  :center
   :font-size   26
   :font-weight "600"
   :color       (theme :secondary-600)})

(def subject-container {:position           :relative
                        :height             58
                        :padding-vertical   6
                        :padding-horizontal 28
                        :justify-content    :center})
(def subject-text {:font-size  18
                   :text-align :center})
(def subject-semester-text {:color       (theme :secondary-700)
                            :font-weight "500"})
(def subject-text-bold {:color       (theme :primary-700)
                        :font-weight "600"})

(def groups-list {:row-gap            18
                  :padding-bottom     12
                  :padding-horizontal 4})

;; TOP -> group details
(def group-header {:flex-direction          :row
                   :padding-vertical        8
                   :padding-horizontal      14
                   :background-color        (theme :primary-700)
                   :align-items             :center
                   :justify-content         :space-between
                   :border-top-left-radius  14
                   :border-top-right-radius 14})

(def group-header-text {:color       (theme :basic-100)
                        :font-weight "700"})

(def group-header-description {:padding-vertical   8
                               :padding-horizontal 14
                               :background-color   (theme :primary-300)})

(def group-header-description-text {:color      (theme :basic-1000)
                                    :font-style :italic})

(def group-body {:flex                       1
                 :row-gap                    18
                 :padding-top                6
                 :padding-bottom             28
                 :padding-horizontal         8
                 :border-bottom-right-radius 14
                 :border-bottom-left-radius  14
                 :background-color           (alpha (theme :primary-100) 50)
                 :border-width               0.5
                 :border-top-width           0
                 :border-color               (theme :primary-500)})
;;
(def presentation {:margin-top         -18
                   :flex-direction     :row
                   :justify-content    :flex-end
                   :padding-horizontal 12})
(def presentation-button-border {:border-radius 16})
(def presentation-button
  (merge presentation-button-border
         {:padding-horizontal 18
          :padding-vertical   8
          :border-radius      16
          :background-color   (theme :primary-500)}))
(def presentation-button-text {:font-weight "500"
                               :color       (theme :basic-100)})
;;
(def person-data {:justify-content    :center
                  :padding-vertical   6
                  :padding-horizontal 12
                  :border-radius      12
                  :background-color   (alpha (theme :primary-300) 40)})

(def person-name {:color       (theme :basic-1000)
                  :font-weight "600"})
(def unassigned-person-name {:color       (theme :basic-1000)
                             :font-weight "600"})
(def person-role {:color       (theme :secondary-600)
                  :font-weight "600"})
;;
(def schedule {:flex-direction   :row
               :justify-content  :space-between
               :align-items      :center
               :padding-left     12
               :border-radius    12
               :background-color (alpha (theme :primary-100) 80)})

(def schedule-days {:color       (theme :basic-800)
                    :font-weight "500"})

(def schedule-hours-container {:flex-direction     :row
                               :justify-content    :center
                               :align-items        :center
                               :padding-vertical   4
                               :padding-horizontal 8
                               :border-radius      16
                               :border-width       1
                               :border-color       (theme :primary-900)})

(def schedule-hours-text-size {:font-size 18})

(def schedule-hours-text {:color       (theme :primary-900)
                          :text-align  :center
                          :font-weight "600"})
;;
(def room-container {:justify-content  :center
                     :align-items      :flex-start
                     :border-radius    12
                     :background-color (alpha (theme :primary-100) 60)})
(def room {:align-self       :flex-start
           :border-width     1
           :padding-vertical 4
           :padding          12
           :border-radius    12
           :border-color     (theme :primary-900)})

(def room-text {:color       (theme :primary-900)
                :font-weight "600"})

;; Bottom
(def bottom-container {:row-gap            20
                       :padding-bottom     32
                       :padding-horizontal 8
                       :justify-content    :center})

(def semester-num {:background-color   (theme :primary-600)
                   :border-radius      16
                   :padding-vertical   6
                   :padding-horizontal 16})

(def semester-num-open {:border-bottom-right-radius 0
                        :border-bottom-left-radius  0})

(def semester-num-content {:flex-direction  :row
                           :justify-content :space-between
                           :align-items     :center})

(def semester-num-text {:font-size   16
                        :font-weight "600"
                        :color       (theme :basic-100)})
