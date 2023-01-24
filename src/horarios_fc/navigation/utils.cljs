(ns horarios-fc.navigation.utils
  (:require
   [re-frame.core :as rf]
   [react-navigation.native :refer [create-navigation-container-ref]]))

(defn create-stack-navigator [{:keys [native-stack-navigator screens]}]
  (let [{stack-screen    :stack/screen
         stack-navigator :stack/navigator} native-stack-navigator]
    [stack-navigator
     (map (fn [{:keys [props component] :as _screen}]
            ^{:key (str (:name props))}
            [stack-screen props component])
          screens)]))

(defonce navigation-ref (create-navigation-container-ref))

(defn navigate [{:keys [route-name params]}]
  (when ^js/Boolean (.isReady ^js/Object navigation-ref)
    (.navigate navigation-ref route-name params)))

(rf/reg-fx :navigate navigate)

(rf/reg-event-fx
 :store-navigation-&-state
 (fn [{db :db} [_ navigation-state]]
   (let [new-db (assoc db :navigation-state navigation-state)]
     {:db         new-db
      :store-data [:db-state new-db]})))

(rf/reg-sub
 :navigation-state
 (fn [db]
   (:navigation-state db)))
