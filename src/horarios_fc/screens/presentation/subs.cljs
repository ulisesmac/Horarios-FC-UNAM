(ns horarios-fc.screens.presentation.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::presentation
 (fn [db]
   (:shown-presentation db)))
