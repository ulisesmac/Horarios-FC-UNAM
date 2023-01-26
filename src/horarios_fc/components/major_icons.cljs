(ns horarios-fc.components.major-icons)

(defonce actuaria-icon (js/require "../resources/icons/majors/actuaria.png"))
(defonce biología-icon (js/require "../resources/icons/majors/biologia.png"))
(defonce ciencias-de-la-computación-icon (js/require "../resources/icons/majors/computacion.png"))
(defonce ciencias-de-la-tierra-icon (js/require "../resources/icons/majors/tierra.png"))
(defonce física-icon (js/require "../resources/icons/majors/fisica.png"))
(defonce física-biomédica-icon (js/require "../resources/icons/majors/biomedica.png"))
(defonce costas-icon (js/require "../resources/icons/majors/costas.png"))
(defonce matemáticas-aplicadas-icon (js/require "../resources/icons/majors/aplicadas.png"))
(defonce matemáticas-icon (js/require "../resources/icons/majors/matematicas.png"))

(defonce major-icon
  {"Actuaría"                             actuaria-icon
   "Biología"                             biología-icon
   "Ciencias de la Computación"           ciencias-de-la-computación-icon
   "Ciencias de la Tierra"                ciencias-de-la-tierra-icon
   "Física"                               física-icon
   "Física Biomédica"                     física-biomédica-icon
   "Manejo Sustentable de Zonas Costeras" costas-icon
   "Matemáticas Aplicadas"                matemáticas-aplicadas-icon
   "Matemáticas"                          matemáticas-icon})
