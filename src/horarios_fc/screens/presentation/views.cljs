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
                    :font-weight   "600"
                    :color         (colors/theme-color :basic-800)}}
   text])

(defn h3 [text]
  [rn/text {:style {:font-size     16
                    :margin-top    7
                    :margin-bottom 7
                    :font-weight   "600"
                    :color         (colors/theme-color :basic-800)}}
   text])

(defn h4 [text]
  [rn/text {:style {:font-size     15
                    :margin-top    9.31
                    :margin-bottom 9.31
                    :font-weight   "600"
                    :color         (colors/theme-color :basic-800)}}
   text])

(defn h5 [text]
  [rn/text {:style {:font-size     14
                    :margin-horizontal    11.67
                    :font-weight   "600"
                    :color         (colors/theme-color :basic-800)}}
   text])

(defn div [children]
  [rn/view children])

(defn p [children]
  [rn/text {:style {:margin-vertical 7
                    :font-size       14
                    :line-height     22
                    :column-gap      7
                    :color           (colors/theme-color :basic-800)}}
   children])
(defn span [children]
  [rn/text
   " " children " "])

(defn strong [children]
  [rn/text {:style {:font-weight "600"
                    :color       (colors/theme-color :primary-700)}}
   " "
   children
   " "])

(defn a [[text _kw v-attributes :as p]]
  (let [{:attr/keys [href] :as x} (into {} (mapv vec (partition 2 v-attributes)))]
    #_(prn x v-attributes)
    (prn p)
    [rn/touchable-highlight {:on-press (fn [] (prn href))}
     [rn/view {:style {:padding-vertical   6
                       :padding-horizontal 18
                       :background-color   (colors/theme-color :primary-600)
                       :border-radius 6}}
      [rn/text {:style {:color (colors/theme-color :basic-100)}}
       text]]]))

(defn em [children]
  [rn/text {:style {:font-style :italic}}
   children])

(defn li
  ([idx [_fragment-kw child-text & children]]
   (let [li-parent [rn/view
                    [rn/text {:style {:font-size   14
                                      :line-height 20
                                      :color       (colors/theme-color :basic-800)}}
                     (str idx ". ")
                     child-text]]]
     (into li-parent children)))
  ([[_fragment-kw child-text & children]]
   (let [li-parent [rn/view
                    [rn/view {:style {:flex-direction :row
                                      :column-gap     6}}
                     [rn/view {:style {:margin-top       9
                                       :height           5
                                       :width            5
                                       :border-radius    4
                                       :background-color (colors/theme-color :primary-700)}}]
                     [rn/text {:style {:font-size   14
                                       :line-height 20
                                       :color       (colors/theme-color :basic-800)}}
                      child-text]]]]
     (into li-parent children))))

(defn ol [[_kw & children]]
  [rn/view {:style {:padding-left   12
                    :padding-top    4
                    :padding-bottom 6}}
   (map (fn [idx [_kw-li child]]
          ^{:key (str idx child)}
          [li idx child])
        (rest (range))
        children)])

(defn ul [[_kw & children]]
  [rn/view {:style {:padding-left   12
                    :padding-top    4
                    :padding-bottom 6}}
   (map (fn [idx [_kw-li child]]
          ^{:key (str idx child)}
          [li child])
        (range)
        children)])

(defn hr []
  [rn/view {:style {:flex             1
                    :height           0.7
                    :background-color (colors/theme-color :basic-700)}}])

(defn table [body]
  [rn/view {:style {:margin-vertical   8
                    :margin-horizontal 4
                    :border-width      1
                    :border-color      (colors/theme-color :primary-800)}}
   body])

(defn tbody [body]
  [rn/view
   body])

(defn tr [body]
  [rn/view {:style {:flex-direction      :row
                    :flex                1
                    :align-items         :center
                    :border-bottom-width 1
                    :border-bottom-color (colors/theme-color :primary-800)}}
   body])

(defn td [body]
  [rn/view {:style {:flex               1
                    :justify-content    :center
                    :align-items        :center
                    :padding-horizontal 12
                    :padding-vertical   4
                    :border-left-width  0.5
                    :border-right-width 0.5
                    :border-left-color  (colors/theme-color :primary-800)
                    :border-right-color (colors/theme-color :primary-800)}}
   [rn/text {:style {:font-size 14
                     :color     (colors/theme-color :basic-800)}}
    body]])

(defn top-bar []
  (let [subject (rf/subscribe [:subject-selected])]
    [rn/view {:style {:position            :relative
                      :height              58
                      :padding-vertical    6
                      :padding-horizontal  43
                      :margin-horizontal   -15
                      :justify-content     :center
                      :border-bottom-width 1
                      :border-bottom-color (colors/theme-color :basic-300 :basic-700)}}
     [rn/text {:style {:font-size  18
                       :text-align :center}}
      [rn/text {:style {:color       (colors/theme-color :secondary-700 :secondary-600)
                        :font-weight "500"}}
       (str @subject ", ")]
      [rn/text {:style {:color       (colors/theme-color :primary-700 :primary-600)
                        :font-weight "600"}}
       (str "Grupo")]]]))

(defn html->hiccup [node]
  (cond
    (vector? node)
    (with-meta
     (cond
       (= (first node) :a) [a (rest node)]
       (= (first node) :attr/target) node
       (= (first node) :br) [rn/text "\n"]
       (= (first node) :div) [div (second node)]
       (= (first node) :em) [em (second node)]
       (= (first node) :h2) [h2 (second node)]
       (= (first node) :h3) [h3 (second node)]
       (= (first node) :h4) [h4 (second node)]
       (= (first node) :h5) [h5 (second node)]
       (= (first node) :hr) [hr]

       (= (first node) :ul) [ul (second node)]
       (= (first node) :ol) [ol (second node)]
       (= (first node) :li) [:li (second node)]

       (= (first node) :p) [p (second node)]
       (= (first node) :span) [span (second node)]
       (= (first node) :strong) [strong (second node)]

       (= (first node) :table) [table (second node)]
       (= (first node) :tbody) [tbody (second node)]
       (= (first node) :tr) [tr (second node)]
       (= (first node) :td) [td (second node)]

       (= (first node) :text) [rn/text (second node)]
       (vector? (first node)) (vec (concat [:<>] node))
       :else [rn/text (str node)])
     {:key (str (random-uuid))})

    (string? node) node

    :else node))

(defn screen* []
  (let [presentation (rf/subscribe [::subs/presentation])
        _            (def -p presentation)]
    (fn []
      [rn/view {:style {:flex             1
                        :padding-bottom   24
                        :background-color (colors/theme-color :basic-100)}
                }
       [top-bar]
       [rn/scroll-view {:style {:padding-horizontal 14}}
        (let [_ (def x
                  (walk/postwalk html->hiccup @presentation))]
          x)
        ]])))

(defn screen [] (r/as-element [:f> screen*]))