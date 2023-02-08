(ns horarios-fc.screens.presentation.views
  (:require
   [react-native :as rn]
   [horarios-fc.screens.presentation.subs :as subs]
   [reagent.core :as r]
   [clojure.walk :as walk]
   [re-frame.core :as rf]))

(defn h3 [text]
  [rn/text {:style {:font-size     16
                    :margin-top    14
                    :margin-bottom 14
                    :font-weight   "600"}}
   text])

(defn screen* []
  (let [presentation (rf/subscribe [::subs/presentation])]
    (fn []
      [rn/view

       #_(walk/prewalk (fn [])
                     @presentation)

       #_(loop [html-node (concat [:div] @presentation)
              acc       [:<>]]
         (cond
           (vector? html-node)
           (recur (conj acc :H# ))
               )
         )])))

(defn screen [] (r/as-element [:f> screen*]))
