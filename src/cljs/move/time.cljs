(ns move.time)
;; I want to implement time protocol here for incapsulation some inner state...

(defprotocol Time
  "Protocol for time model"
  (pause [this])
  (play [this])
  (get-time [this]))

;;-----------------------------------
;; This is atom-state based implementation of Time protocol
(deftype TimeOne [t0 tp paused?]

  Time

  (pause [_]
    (if (not @paused?)
      (do
        (reset! tp (.now js/Date))
        (reset! paused? true))))

  (play [_]
    (if @paused?
      (do
        (reset! paused? false)
        (swap! t0 (fn [v delta] (+ v delta)) (- (.now js/Date) @tp)))))

  (get-time [_]
    (if @paused?
      (- @tp @t0)
      (- (.now js/Date) @t0))))

(defn create-time-model-one [paused?]
  "factory function for TimeOne"
  (if paused?
    (TimeOne. (atom (.now js/Date)) (atom (.now js/Date)) (atom true))
    (TimeOne. (atom (.now js/Date)) (atom 0) (atom false))))

;;---------------------------------------------------------------
;; Рассмотреть реализацию - без стайта - атома - тоесть чисто иммутабельную....


(def ttt 235)
