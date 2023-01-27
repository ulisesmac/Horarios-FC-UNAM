(ns horarios-fc.screens.settings.views
  (:require
   [horarios-fc.colors :refer [theme-color]]
   [re-frame.core :as rf]
   [react-native :as rn]
   [reagent.core :as r]))

(defn theme-button [{:keys [text on-press disabled]}]
  [rn/touchable-highlight (when-not disabled {:style    {:border-radius 12}
                                              :on-press on-press})
   [rn/view {:style {:background-color   (if disabled
                                           (theme-color :basic-400 :basic-700)
                                           (theme-color :primary-600 :primary-800))
                     :padding-vertical   12
                     :padding-horizontal 24
                     :border-radius      12}}
    [rn/text {:style {:color       (if disabled
                                     (theme-color :basic-700 :basic-300)
                                     (theme-color :basic-100 :basic-200))
                      :font-weight "500"}}
     text]]])

(defn screen* []
  (let [current-theme (rf/subscribe [:theme-raw])]
    (fn []
      [rn/view {:style {:flex               1
                        :justify-content    :center
                        :align-items        :center
                        :row-gap            12
                        :padding-horizontal 15
                        :background-color   (theme-color :basic-100 :basic-1000)}}
       [rn/text {:style {:font-size  18
                         :color      (theme-color :basic-1000 :basic-200)
                         :text-align :center}}
        "Tema"]
       [rn/view {:style {:row-gap 12}}
        [rn/view {:style {:align-items     :center
                          :justify-content :center}}
         [theme-button {:text     "🤖 Automático"
                        :disabled (= @current-theme :auto)
                        :on-press #(rf/dispatch [:set-theme :auto])}]]

        [rn/view {:style {:flex-direction :row
                          :column-gap     18}}
         [theme-button {:text     "☀ Claro"
                        :disabled (= @current-theme :light)
                        :on-press #(rf/dispatch [:set-theme :light])}]
         [theme-button {:text     "🌙 Obscuro"
                        :disabled (= @current-theme :dark)
                        :on-press #(rf/dispatch [:set-theme :dark])}]]]])))

(defn screen []
  (r/as-element [screen*]))


