(ns horarios-fc.components.major-icons)

(defonce biología-icon (js/require "../resources/icons/majors/Biología.png"))
(defonce ciencias-de-la-computación-icon (js/require "../resources/icons/majors/Ciencias-de-la-Computación.png"))
(defonce ciencias-de-la-tierra-icon (js/require "../resources/icons/majors/Ciencias-de-la-Tierra.png"))
(defonce física-icon (js/require "../resources/icons/majors/Física.png"))
(defonce física-biomédica-icon (js/require "../resources/icons/majors/Física-biomédica.png"))
(defonce matemáticas-icon (js/require "../resources/icons/majors/Matemáticas.png"))

(defonce major-icon
  {;"Actuaría"                             ["\uD83D\uDCB9" "📊"]
   "Biología"                   biología-icon
   "Ciencias de la Computación" ciencias-de-la-computación-icon
   "Ciencias de la Tierra"      ciencias-de-la-tierra-icon
   "Física"                     física-icon
   "Física Biomédica"           física-biomédica-icon
   ;"Manejo Sustentable de Zonas Costeras" ["\uD83C\uDF0A " "\uD83C\uDFDD"]
   ;"Matemáticas Aplicadas"                ["❔" "🧮"]
   "Matemáticas"                matemáticas-icon})
