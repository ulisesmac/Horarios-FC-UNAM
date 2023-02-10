(ns horarios-fc.screens.presentation.views
  (:require
   [react-native :as rn]
   [horarios-fc.screens.presentation.subs :as subs]
   [reagent.core :as r]
   [clojure.walk :as walk]
   [re-frame.core :as rf]
   [horarios-fc.colors :as colors]))

(defn h2 [text]
  [rn/text {:style {:font-size     21
                    :margin-top    5.75
                    :margin-bottom 5.75
                    :font-weight   "600"}}
   text])

(defn h3 [text]
  [rn/text {:style {:font-size     16
                    :margin-top    7
                    :margin-bottom 7
                    :font-weight   "600"}}
   text])

(defn h4 [text]
  [rn/text {:style {:font-size     15
                    :margin-top    9.31
                    :margin-bottom 9.31
                    :font-weight   "600"}}
   text])

(defn h5 [text]
  [rn/text {:style {:font-size     14
                    :margin-horizontal    11.67
                    :font-weight   "600"}}
   text])
(defn div [children]
  [rn/view children])
(defn p [children]
  [rn/text {:style {:margin-vertical 7
                    :font-size 14
                    :column-gap 7}}
   children])
(defn span [children]
  children)

(defn strong [children]
  [rn/text {:style {:font-weight "600"}}
   " "
   children
   " "])

(defn a [[text _kw v-attributes :as p]]
  (let [{:attr/keys [href] :as x} (into {} (mapv vec (partition 2 v-attributes)))]
    (prn x v-attributes)
    (prn p)
    [rn/touchable-highlight {:on-press (fn [] (prn href))}
     [rn/view {:style {:padding-vertical   6
                       :padding-horizontal 18

                       :background-color   (colors/theme-color :primary-600)
                       :border-radius 6}}
      [rn/text {:style {:color (colors/theme-color :basic-100)}}
       text]]]))
(defn html->hiccup [node]
  (cond
    (vector? node)
    (cond
      (= (first node) :div) [div (second node)]
      (= (first node) :h2) [h2 (second node)]
      (= (first node) :h3) [h3 (second node)]
      (= (first node) :h4) [h4 (second node)]
      (= (first node) :h5) [h5 (second node)]
      (= (first node) :p) [p (second node)]
      (= (first node) :a) [a (rest node)]
      (= (first node) :span) [span (second node)]
      (= (first node) :strong) [strong (second node)]
      (= (first node) :text) [rn/text (second node)]
      (= (first node) :attr/target) node
      (vector? (first node)) (vec (concat [:<>] node))
      :else [rn/text (str node)])

    (string? node) node

    :else node))

(defn screen* []
  (let [presentation (rf/subscribe [::subs/presentation])
        _ (def -p presentation)]
    (fn []
      [rn/scroll-view
       (let [_ (def x
                 (walk/postwalk html->hiccup @presentation))]
         x)
       ])))

(defn screen [] (r/as-element [:f> screen*]))
