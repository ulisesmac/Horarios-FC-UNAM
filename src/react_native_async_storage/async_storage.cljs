(ns react-native-async-storage.async-storage
  (:require
   ["@react-native-async-storage/async-storage" :refer [AsyncStorage]]
   [cljs.core.async :refer [go take!]]
   [cljs.core.async.interop :refer [<p!]]
   [cljs.reader :refer [read-string]]
   [re-frame.core :as rf]))

(def set-item (.-setItem AsyncStorage))
(def get-item (.-getItem AsyncStorage))

(defn store! [k data]
  (let [key-str  (prn-str k)
        data-str (prn-str data)]
    (go
     (try
       (<p! (set-item key-str data-str))
       (catch :default e
         (js/console.error "\nError writing data\n"
                           "\nKey: " key-str "\nValue: " data-str
                           "\nError:\n" e))))))
(rf/reg-fx :store-data #(apply store! %))

(defn read! [k callback-fn]
  (-> (let [key-str (prn-str k)]
        (go
         (try
           (some-> (<p! (get-item key-str))
             (read-string))
           (catch :default e
             (js/console.error "\nError reading key:\n " key-str "\nError:\n" e)))))
      (take! callback-fn)))
(rf/reg-fx :read-data #(apply read! %))
