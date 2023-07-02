(ns horarios-fc.testing-cloud-firestore
  "ref: https://rnfirebase.io/firestore/usage"
  (:require
   ["@react-native-firebase/firestore" :default firestore]))

;; One time read
(comment
 (-> (firestore)
     (.collection "restaurant")
     (.doc "wFOIUFU2XbsUbjSfSAI3")
     (.get)
     (.then (fn [a]
              (prn (js->clj (.-_data a)
                            :keywordize-keys true))))
     (.catch (fn [el-error]
               (prn el-error)))))

;; Realtime changes
(comment
 (defn on-result
   [document-snapshot]
   (prn (.data document-snapshot)))

 (defn on-error
   [error]
   (js/console.error error))

 (def unsuscribe!
   (-> (firestore)
       (.collection "restaurant")
       (.doc "wFOIUFU2XbsUbjSfSAI3")
       (.onSnapshot on-result on-error)))
 )

;; Add data to document
(comment
 (-> (firestore)
     (.collection "restaurant")
     (.doc "wFOIUFU2XbsUbjSfSAI3")
     (.update #js {:name "La tía Aly!!"
                   ;:rating 3
                   })
     (.then (fn []
              (prn "Restaurante agregado")))
     (.catch (fn []
               (prn "Error al agregar al restaurante"))))

 )
