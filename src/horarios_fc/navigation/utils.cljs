(ns horarios-fc.navigation.utils
  (:require
   [react-navigation.native :refer [create-navigation-container-ref]]
   [re-frame.core :as rf]))

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

(rf/reg-fx ::navigate! navigate)

(rf/reg-event-fx
 :navigate
 (fn [{db :db} [_ route-name params]]
   {:db         (assoc db :current-route route-name)
    ::navigate! {:route-name route-name
                 :params     params}}))
