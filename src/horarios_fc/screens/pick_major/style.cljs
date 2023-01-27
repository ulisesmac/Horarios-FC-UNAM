(ns horarios-fc.screens.pick-major.style
  (:require
   [horarios-fc.colors :refer [alpha theme theme-color]]))

(defn container []
  {:position           :relative
   :flex               1
   :background-color   (theme-color :basic-100 :basic-1000)
   :padding-horizontal 15
   :row-gap            2})

;; Header
(defn header []
  {:height              54
   :position            :relative
   :justify-content     :center
   :align-items         :center
   :border-bottom-width 1
   :margin-horizontal   -15
   :border-bottom-color (theme-color :basic-400 :basic-700)})

(def header-text-1 {:font-size   24
                    :font-weight "600"
                    :color       (theme :primary-600)})

(def header-text-2 {:color (theme :secondary-600)})

;; Body
(def body-container {:row-gap 6, :flex 1})

;; Semester picker
(def semester-picker {:padding-vertical 2})
(defn subtitle-text []
  {:font-size   18
   :font-weight "500"
   :color       (theme-color :primary-800 :primary-700)})

(def semester-container {:flex-direction     :row
                         :align-items        :center
                         :padding-vertical   6
                         :padding-horizontal 2
                         :column-gap         12})

(def semester-button-radius {:border-radius 12})

(defn semester-button [active?]
  {:justify-content    :center
   :align-items        :center
   :padding-horizontal 14
   :padding-vertical   6
   :border-width       1
   :border-radius      12
   :border-color       (if active?
                         (theme :secondary-700)
                         (theme-color :primary-700 :primary-600))
   :background-color   (if active?
                         (theme-color :secondary-100 :secondary-900)
                         (theme-color :primary-100 :primary-1100))})

(def semester-button-text-container {:height          18
                                     :justify-content :center})

(defn semester-button-text [active?]
  {:font-weight "500"
   :color       (if active?
                  (theme-color :secondary-700 :secondary-600)
                  (theme-color :primary-700 :primary-500))})

;; Majors
(def majors-container {:flex              1
                       :padding-vertical  2
                       :margin-horizontal -11})
(def major-title-container {:margin-horizontal 11})
(def majors-listing-container {:flex-direction  :row
                               :justify-content :center
                               :flex-wrap       :wrap
                               :column-gap      12
                               :row-gap         16
                               :padding-top     12
                               :padding-bottom  16})

(defn major []
  {:flex-direction     :row
   :background-color   (theme-color
                        (alpha (theme :primary-100) 30)
                        :primary-1200)
   :border-radius      16
   :border-width       1
   :border-color       (theme-color :primary-800 :primary-600)
   :width              185
   :height             108
   :padding-vertical   12
   :padding-horizontal 6
   :column-gap         4})

(def major-image-container {:justify-content :center})
(def major-image {:width  58
                  :height 58})
(def major-anon-text
  {:font-size 42
   :color     (theme :basic-1000)})

(def major-text-container {:flex            1
                           :justify-content :center})
(defn major-text []
  {:font-size   17
   :font-weight "600"
   :color       (theme-color :primary-800 :primary-600)})
