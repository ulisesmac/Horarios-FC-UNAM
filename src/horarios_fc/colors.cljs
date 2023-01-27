(ns horarios-fc.colors
  (:require
   [re-frame.core :as rf]
   [react-native :as rn]))

(def theme
  {:primary-100   "#D4F0FE"
   :primary-200   "#ABDDFE"
   :primary-300   "#80C7FE"
   :primary-400   "#61B1FD"
   :primary-500   "#2D8EFD"
   :primary-600   "#206ED9"
   :primary-700   "#1652B6"
   :primary-800   "#0E3992"
   :primary-900   "#082879"
   :primary-1000  "#07205d"
   :primary-1100  "#041436"
   :primary-1200  "#031026"
   :secondary-100 "#bcf6cd"
   :secondary-200 "#8ff0ac"
   :secondary-300 "#62ea8b"
   :secondary-400 "#35e369"
   :secondary-500 "#1cca50"
   :secondary-600 "#159d3e"
   :secondary-700 "#0f702c"
   :secondary-800 "#09431b"
   :secondary-900 "#031609"
   :success-100   "#E9FCD9"
   :success-200   "#CFFAB4"
   :success-300   "#AAF18C"
   :success-400   "#87E46C"
   :success-500   "#55D33F"
   :success-600   "#37B52E"
   :success-700   "#1F9720"
   :success-800   "#147A1C"
   :success-900   "#0C651A"
   :info-100      "#D7FBFE"
   :info-200      "#B0F2FE"
   :info-300      "#89E3FD"
   :info-400      "#6BD2FB"
   :info-500      "#3BB7F9"
   :info-600      "#2B8FD6"
   :info-700      "#1D6CB3"
   :info-800      "#124C90"
   :info-900      "#0B3677"
   :warning-100   "#FEFAD2"
   :warning-200   "#FEF5A5"
   :warning-300   "#FCED78"
   :warning-400   "#FAE456"
   :warning-500   "#F7D720"
   :warning-600   "#D4B517"
   :warning-700   "#B19410"
   :warning-800   "#8F750A"
   :warning-900   "#765E06"
   :danger-100    "#FFE6D9"
   :danger-200    "#FFC8B3"
   :danger-300    "#FFA28D"
   :danger-400    "#FF7F71"
   :danger-500    "#FF4542"
   :danger-600    "#DB303C"
   :danger-700    "#B72138"
   :danger-800    "#931533"
   :danger-900    "#7A0C30"
   :basic-100     "#fdfdfd"
   :basic-200     "#eeeeee"
   :basic-300     "#d7d7d7"
   :basic-400     "#aaaaaa"
   :basic-500     "#969696"
   :basic-600     "#828282"
   :basic-700     "#6e6e6e"
   :basic-800     "#141414"
   :basic-900     "#0a0a0a"
   :basic-1000    "#050505"
   :basic-1100    "#000000"})

(defn alpha [color percentage]
  (str color percentage))

(rf/reg-event-db
 :set-theme
 (fn [db [_ theme-variant]]
   (assoc db :theme-variant theme-variant)))

(rf/reg-sub
 :theme-raw
 (fn [db _]
   (get db :theme-variant :auto)))

(rf/reg-sub
 :theme
 :<- [:theme-raw]
 (fn [theme-raw]
   (if (= theme-raw :auto)
     (rn/get-color-scheme)
     theme-raw)))

(defn theme-color
  ([light-color]
   (theme-color light-color light-color))
  ([light-color dark-color]
   (let [theme-selected @(rf/subscribe [:theme])
         actual-color   (if (= theme-selected :dark)
                          dark-color
                          light-color)]
     (if (keyword? actual-color)
       (theme actual-color)
       actual-color))))
